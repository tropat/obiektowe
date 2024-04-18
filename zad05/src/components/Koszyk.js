import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Koszyk = () => {
    const [koszyk, setKoszyk] = useState([]);

    useEffect(() => {
        const fetchKoszyk = async () => {
            try {
                const response = await axios.get('http://localhost:8080/koszyk/1');
                setKoszyk(response.data);
            } catch (error) {
                console.error('Błąd podczas pobierania danych koszyka:', error);
            }
        };

        fetchKoszyk();
    }, []);

    return (
        <div>
            <h2>Koszyk</h2>
            <ul>
                {koszyk.Produkty && koszyk.Produkty.map(produkt => (
                    <li key={produkt.ID}>Produkt {produkt.Nazwa} Kategorii {produkt.KategoriaID}: {produkt.Cena} zl</li>
                ))}
            </ul>
        </div>
    );
};

export default Koszyk;