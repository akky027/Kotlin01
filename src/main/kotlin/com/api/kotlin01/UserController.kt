package com.api.kotlin01

import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/users") // このコントローラのベースパスを設定
class UserController {

    private val users = mutableListOf<User>()
    private val counter = AtomicLong()

    init {
        // 初期データ
        users.add(User(counter.incrementAndGet(), "Alice", "alice@example.com"))
        users.add(User(counter.incrementAndGet(), "Bob", "bob@example.com"))
    }

    @GetMapping // 全ユーザーを取得
    fun getAllUsers(): List<User> {
        return users
    }

    @GetMapping("/{id}") // 特定のユーザーをIDで取得
    fun getUserById(@PathVariable id: Long): User? {
        return users.find { it.id == id }
    }

    @PostMapping // 新しいユーザーを作成
    fun createUser(@RequestBody user: User): User {
        val newUser = user.copy(id = counter.incrementAndGet())
        users.add(newUser)
        return newUser
    }

    @PutMapping("/{id}") // ユーザーを更新
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: User): User? {
        val index = users.indexOfFirst { it.id == id }
        return if (index != -1) {
            val userToUpdate = updatedUser.copy(id = id)
            users[index] = userToUpdate
            userToUpdate
        } else {
            null // 更新対象が見つからない場合
        }
    }

    @DeleteMapping("/{id}") // ユーザーを削除
    fun deleteUser(@PathVariable id: Long): Boolean {
        return users.removeIf { it.id == id }
    }
}
