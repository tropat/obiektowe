package org.example.zad03_2

import org.example.zad03_2.AppService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class Request(val username: String, val password: String)

@RestController
class Controller @Autowired constructor(private val appService: AppService) {

    @GetMapping("/")
    fun hello(): String {
        return "Application to check your login and password"
    }

    @GetMapping("/users")
    fun users(): String {
        val result = appService.usersGet()
        val usersList = result.keys.joinToString(", ")
        return "Użytkownicy: $usersList"
    }

    @PostMapping("/login")
    fun login(@RequestBody request: Request): String {
        val result = appService.authenticate(request.username, request.password)
        return if (result) "Success" else "Failure"
    }
}