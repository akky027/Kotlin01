package com.api.kotlin01.repository

import com.api.kotlin01.model.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest
    @Autowired
    constructor(
        val userRepository: UserRepository,
    ) {
        @Test
        fun `save and find user by id`() {
            val user = User(name = "repo", email = "repo@example.com", age = 30, gender = "M")
            val saved = userRepository.save(user)
            val found = userRepository.findById(saved.id).orElse(null)
            assertNotNull(found)
            assertEquals("repo", found?.name)
        }

        @Test
        fun `findByEmail returns user`() {
            val user = User(name = "mail", email = "mail@example.com", age = 25, gender = "F")
            userRepository.save(user)
            val found = userRepository.findByEmail("mail@example.com")
            assertNotNull(found)
            assertEquals("mail", found?.name)
        }

        @Test
        fun `deleteById removes user`() {
            val user = User(name = "del", email = "del@example.com", age = 40, gender = "M")
            val saved = userRepository.save(user)
            userRepository.deleteById(saved.id)
            val found = userRepository.findById(saved.id)
            assertTrue(found.isEmpty)
        }

        @Test
        fun `findAll returns all users`() {
            userRepository.save(User(name = "A", email = "a@a.com", age = 10, gender = "M"))
            userRepository.save(User(name = "B", email = "b@b.com", age = 20, gender = "F"))
            val all = userRepository.findAll()
            assertTrue(all.size >= 2)
        }
    }
