package org.example.zad03_2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App {

    @Autowired
    lateinit var appService: AppServiceLazy

    fun authService(): AppServiceLazy {
        return appService
    }
}

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
