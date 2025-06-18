package com.api.kotlin01.security

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority

class FirebaseUserDetailsTest {
    @Test
    fun `FirebaseUserDetails properties should return correct values`() {
        // Arrange
        val uid = "testUid"
        val email = "test@example.com"
        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        val userDetails = FirebaseUserDetails(uid, email, authorities)

        // Act & Assert
//        assertEquals(uid, userDetails.getUid())
        assertEquals(uid, userDetails.username)
        assertEquals(authorities, userDetails.authorities)
        assertNull(userDetails.password) // パスワードはnullを返す
        assertTrue(userDetails.isAccountNonExpired)
        assertTrue(userDetails.isAccountNonLocked)
        assertTrue(userDetails.isCredentialsNonExpired)
        assertTrue(userDetails.isEnabled)
    }
}
