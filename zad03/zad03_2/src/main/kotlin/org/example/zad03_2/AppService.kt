package org.example.zad03_2

import org.springframework.stereotype.Service

@Service
object AppService {
    private val users = mapOf(
        "u_1" to "p_1",
        "u_2" to "p_2",
        "u_3" to "p_3",
        "u_4" to "p_4")

    fun authenticate(name: String, pass: String): Boolean {
        return users[name] == pass
    }

    fun usersGet(): Map<String, String> {
        return users
    }
}

@Service
class AppServiceLazy private constructor() {

    companion object {
        val instance:AppServiceLazy by lazy {
            AppServiceLazy()
        }
    }

    private val users = mapOf(
        "u_1" to "p_1",
        "u_2" to "p_2",
        "u_3" to "p_3",
        "u_4" to "p_4")

    fun authenticate(name: String, pass: String): Boolean {
        return users[name] == pass
    }

    fun usersGet(): Map<String, String> {
        return users
    }
}