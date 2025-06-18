package com.api.kotlin01.controller

import com.api.kotlin01.model.User
import com.api.kotlin01.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun createUser(
        @RequestBody user: User,
    ): ResponseEntity<User> = ResponseEntity.ok(userService.createUser(user))

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody user: User,
    ): ResponseEntity<User> {
        val updated = userService.updateUser(id, user)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getUser(
        @PathVariable id: Long,
    ): ResponseEntity<User> {
        val user = userService.getUser(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @GetMapping("/public/get_all_users")
    fun getAllUsers(): List<User> = userService.getAllUsers()
}
