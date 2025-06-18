package com.api.kotlin01.service

import com.api.kotlin01.model.User
import com.api.kotlin01.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(user: User): User = userRepository.save(user)

    fun updateUser(
        id: Long,
        updated: User,
    ): User? {
        return userRepository.findById(id).orElse(null)?.let {
            val userToUpdate =
                it.copy(
                    name = updated.name,
                    email = updated.email,
                    age = updated.age,
                    gender = updated.gender,
                    updatedAt = java.time.LocalDateTime.now(),
                )
            userRepository.save(userToUpdate)
        }
    }

    fun deleteUser(id: Long) = userRepository.deleteById(id)

    fun getUser(id: Long): User? = userRepository.findById(id).orElse(null)

    fun getAllUsers(): List<User> = userRepository.findAll()
}
