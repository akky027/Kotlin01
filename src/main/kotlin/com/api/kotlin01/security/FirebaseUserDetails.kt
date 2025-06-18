package com.api.kotlin01.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class FirebaseUserDetails(
    private val uid: String,
    private val email: String?,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String? = null // Firebase認証ではパスワードは使用しない

    override fun getUsername(): String = uid // UIDをSpring Securityのユーザー名として使用

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
