package com.api.kotlin01.service

import com.api.kotlin01.model.User
import com.api.kotlin01.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class UserServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        userService = UserService(userRepository)
    }

    @Test
    fun `createUser should save and return user`() {
        val user = User(name = "test", email = "test@example.com", age = 20, gender = "M")
        every { userRepository.save(user) } returns user

        val result = userService.createUser(user)

        assertEquals(user, result)
        verify { userRepository.save(user) }
    }

    @Test
    fun `updateUser should update and return user when found`() {
        val id = 1L
        val oldUser = User(id = id, name = "old", email = "old@example.com", age = 30, gender = "F")
        val updated = User(id = id, name = "new", email = "new@example.com", age = 25, gender = "M")
        every { userRepository.findById(id) } returns Optional.of(oldUser)
        every { userRepository.save(any()) } answers { firstArg() }

        val result = userService.updateUser(id, updated)

        assertNotNull(result)
        assertEquals("new", result?.name)
        verify { userRepository.findById(id) }
        verify { userRepository.save(any()) }
    }

    @Test
    fun `updateUser should return null when user not found`() {
        val id = 2L
        val updated = User(id = id, name = "new", email = "new@example.com", age = 25, gender = "M")
        every { userRepository.findById(id) } returns Optional.empty()

        val result = userService.updateUser(id, updated)

        assertNull(result)
        verify { userRepository.findById(id) }
        verify(exactly = 0) { userRepository.save(any()) }
    }

    @Test
    fun `deleteUser should call repository deleteById`() {
        val id = 3L
        every { userRepository.deleteById(id) } just Runs

        userService.deleteUser(id)

        verify { userRepository.deleteById(id) }
    }

    @Test
    fun `getUser should return user when found`() {
        val id = 4L
        val user = User(id = id, name = "test", email = "test@example.com", age = 22, gender = "F")
        every { userRepository.findById(id) } returns Optional.of(user)

        val result = userService.getUser(id)

        assertEquals(user, result)
        verify { userRepository.findById(id) }
    }

    @Test
    fun `getUser should return null when not found`() {
        val id = 5L
        every { userRepository.findById(id) } returns Optional.empty()

        val result = userService.getUser(id)

        assertNull(result)
        verify { userRepository.findById(id) }
    }

    @Test
    fun `getAllUsers should return user list`() {
        val users =
            listOf(
                User(id = 1, name = "A", email = "a@example.com", age = 20, gender = "M"),
                User(id = 2, name = "B", email = "b@example.com", age = 21, gender = "F"),
            )
        every { userRepository.findAll() } returns users

        val result = userService.getAllUsers()

        assertEquals(users, result)
        verify { userRepository.findAll() }
    }
}
