package com.api.kotlin01.controller

import com.api.kotlin01.security.FirebaseUserDetails
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {

    @GetMapping("/public/hello")
    fun publicHello(): String {
        return "Hello from public endpoint!"
    }

    @GetMapping("/secure/hello")
    fun secureHello(@AuthenticationPrincipal currentUser: FirebaseUserDetails): String {
        // @AuthenticationPrincipal を使って認証済みユーザーの詳細情報を取得
        return "Hello from secure endpoint, ${currentUser.username}!"
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')") // メソッドレベルでのロールベースの認可
    fun adminDashboard(): String {
        // SecurityContextHolder から認証情報を取得する例
        val authentication = SecurityContextHolder.getContext().authentication
        return "Welcome to the admin dashboard, ${authentication.name}!"
    }
}
