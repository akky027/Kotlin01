package com.api.kotlin01.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*

@Component
class FirebaseAuthenticationFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(FirebaseAuthenticationFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val idToken = authorizationHeader.substring(7) // "Bearer " の7文字をスキップ

        try {
            val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)

            val uid = decodedToken.uid
            val email = decodedToken.email

            // 認証情報を作成
            // 実際には、Firebaseのカスタムクレームやデータベースからユーザーのロールを取得することが多い
            val authorities = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
            // カスタムクレームからADMINロールを付与する例
            if (decodedToken.claims.containsKey("admin") && decodedToken.claims["admin"] == true) {
                authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }

            val userDetails = FirebaseUserDetails(uid, email, authorities)

            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
//            logger.info("Successfully authenticated user: {}", uid)

        } catch (e: FirebaseAuthException) {
//            logger.warn("Firebase ID Token verification failed", e)
            SecurityContextHolder.clearContext() // 認証情報をクリア
            response.status = HttpServletResponse.SC_UNAUTHORIZED // 401 Unauthorized
            response.writer.write("Invalid or expired Firebase ID Token.")
            return // 認証失敗なのでここで処理を中断
        }

        filterChain.doFilter(request, response)
    }
}
