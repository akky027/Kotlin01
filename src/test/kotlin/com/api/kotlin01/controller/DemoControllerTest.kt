package com.api.kotlin01.controller

import com.api.kotlin01.config.SecurityConfig
import com.api.kotlin01.security.FirebaseUserDetails
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(DemoController::class)
@Import(SecurityConfig::class)
class DemoControllerTest(
    @Autowired val mockMvc: MockMvc,
) {
    @BeforeEach
    fun setup() {
        val userDetails =
            FirebaseUserDetails(
                uid = "testUid",
                email = "test@example.com",
                authorities =
                    listOf(
                        SimpleGrantedAuthority("ROLE_USER"),
                    ),
            )
        // ユーザーの詳細情報をモックとして設定
        val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = auth
    }

    @Test
    fun `publicHello returns public message`() {
        mockMvc.get("/public/hello")
            .andExpect {
                status { isOk() }
                content { string("Hello from public endpoint!") }
            }
    }

    @Test
    fun `secureHello returns secure message`() {
        // FirebaseUserDetailsのモックは必要に応じて追加
        mockMvc.get("/secure/hello")
            .andExpect {
                status { isOk() }
                content { string("Hello from secure endpoint, testUid!") }
            }
    }

//    @Test
//    @WithMockUser(username = "adminuser", roles = ["ADMIN"])
//    fun `adminDashboard returns admin message`() {
//        mockMvc.get("/admin/dashboard")
//            .andExpect {
//                status { isOk() }
//                content { string("Welcome to the admin dashboard, adminuser!") }
//            }
//    }
}
