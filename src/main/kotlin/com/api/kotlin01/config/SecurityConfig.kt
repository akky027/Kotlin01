package com.api.kotlin01.config

import com.api.kotlin01.security.FirebaseAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val firebaseAuthenticationFilter: FirebaseAuthenticationFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // CSRF対策を無効化 (REST APIでは通常不要)
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) } // CORSを有効化
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/public/**").permitAll() // パブリックエンドポイント
                    .requestMatchers("/admin/**").hasRole("ADMIN") // ADMINロールが必要なエンドポイント
                    .anyRequest().authenticated() // その他のすべてのリクエストは認証が必要
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // セッションを使用しない (JWT認証の基本)
            }

        // FirebaseAuthenticationFilterをUsernamePasswordAuthenticationFilterの前に配置
        http.addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080") // フロントエンドのURL
        configuration.allowedMethods = listOf("*") // GET, POST, PUTなど
        configuration.allowedHeaders = listOf("*") // Authorization, Content-Typeなど
        configuration.allowCredentials = true // クッキーなどの資格情報を許可する場合
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
