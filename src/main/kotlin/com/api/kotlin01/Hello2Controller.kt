package com.example.demoapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController // このクラスがRESTコントローラであることを示す
class Hello2Controller {

    @GetMapping("/hello") // GETリクエストを/helloパスにマッピング
    fun hello(): String {
        return "Hello, Spring Boot with Kotlin!"
    }
}
