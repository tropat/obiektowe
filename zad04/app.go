package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"net/http"

	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

type OpenWeatherMapResponse struct {
	Main struct {
		Temperature float64 `json:"temp"`
	} `json:"main"`
	Weather []struct {
		Description string `json:"description"`
	} `json:"weather"`
}

type WeatherModel struct {
	gorm.Model
	Location    string  `json:"location"`
	Temperature float64 `json:"temperature"`
	Condition   string  `json:"condition"`
}

type Proxy struct {
	db *gorm.DB
}

func (proxy *Proxy) GetWeather(location string) (*WeatherModel, error) {
	response, err := http.Get(fmt.Sprintf("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=f3f614a9f6b25fc1ced87eb7cf4f114a&units=metric", location))
	if err != nil {
		return nil, err
	}
	defer response.Body.Close()

	if response.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("unexpected status code: %d", response.StatusCode)
	}

	var weatherResponse OpenWeatherMapResponse
	err = json.NewDecoder(response.Body).Decode(&weatherResponse)
	if err != nil {
		return nil, err
	}

	if len(weatherResponse.Weather) == 0 {
		return nil, errors.New("no weather data found")
	}

	weather := &WeatherModel{
		Location:    location,
		Temperature: weatherResponse.Main.Temperature,
		Condition:   weatherResponse.Weather[0].Description,
	}

	return weather, nil
}

func (proxy *Proxy) SaveInDatabase(weather *WeatherModel) error {
	result := proxy.db.Create(&weather)
	if result.Error != nil {
		return result.Error
	}
	return nil
}

func main() {
	db, err := gorm.Open(sqlite.Open("weather.db"), &gorm.Config{})
	if err != nil {
		panic("Failed to connect to database")
	}
	db.AutoMigrate(&WeatherModel{})
	proxy := &Proxy{db: db}

	e := echo.New()

	e.GET("/weather", func(c echo.Context) error {
		location := c.QueryParam("location")
		if location == "" {
			return c.JSON(http.StatusBadRequest, map[string]string{"error": "Location not given"})
		}

		weather, err := proxy.GetWeather(location)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]string{"error": "Failed with external api"})
		}

		err = proxy.SaveInDatabase(weather)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, map[string]string{"error": "Failed to save in database"})
		}

		return c.JSON(http.StatusOK, weather)
	})

	e.Logger.Fatal(e.Start(":8080"))
}
