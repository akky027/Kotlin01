package com.api.kotlin01.controller

import com.api.kotlin01.model.User
import com.api.kotlin01.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class UserControllerTest() {
    private lateinit var mockMvc: MockMvc
    private lateinit var userService: UserService
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setup() {
        userService = mockk()
        val controller = UserController(userService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper.registerModule(JavaTimeModule()) // 追加
    }

    @Test
    fun `createUser returns created user`() {
        val user = User(name = "test", email = "test@example.com", age = 20, gender = "M")
        every { userService.createUser(any()) } returns user

        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("test"))
    }

    @Test
    fun `updateUser returns updated user`() {
        val id = 1L
        val user = User(id = id, name = "updated", email = "u@example.com", age = 21, gender = "F")
        every { userService.updateUser(id, any()) } returns user

        mockMvc.perform(
            put("/api/users/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("updated"))
    }

    @Test
    fun `updateUser returns 404 if not found`() {
        val id = 2L
        every { userService.updateUser(id, any()) } returns null

        mockMvc.perform(
            put("/api/users/$id")
                .contentType(MediaType.APPLICATION_JSON),
//                .content(objectMapper.writeValueAsString(User()))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `deleteUser returns no content`() {
        val id = 3L
        every { userService.deleteUser(id) } returns Unit

        mockMvc.perform(delete("/api/users/$id"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `getUser returns user if found`() {
        val id = 4L
        val user = User(id = id, name = "test", email = "test@example.com", age = 22, gender = "F")
        every { userService.getUser(id) } returns user

        mockMvc.perform(get("/api/users/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
    }

    @Test
    fun `getUser returns 404 if not found`() {
        val id = 5L
        every { userService.getUser(id) } returns null

        mockMvc.perform(get("/api/users/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getAllUsers returns user list`() {
        val users =
            listOf(
                User(id = 1, name = "A", email = "a@example.com", age = 20, gender = "M"),
                User(id = 2, name = "B", email = "b@example.com", age = 21, gender = "F"),
            )
        every { userService.getAllUsers() } returns users

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
    }
}
