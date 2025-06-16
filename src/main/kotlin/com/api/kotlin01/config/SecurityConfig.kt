//package com.api.kotlin01.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .authorizeHttpRequests { authorize ->
//                authorize
//                    .requestMatchers("/public/**", "/images/**").permitAll()// /public/ と /images/ は認証なしでアクセス許可
//                    .requestMatchers("/admin/**").hasRole("ADMIN")// /admin/ はADMINロールのみアクセス許可
//                    .anyRequest().authenticated()// その他の全てのリクエストは認証を要求
//            }
//            .formLogin { form ->
//                form
//                    .loginPage("/login") // カスタムログインページのURL (デフォルトは/login)
//                    .permitAll()// ログインページは誰でもアクセス可能
//            }
//            .logout { logout ->
//                logout.permitAll() // ログアウトは誰でもアクセス可能
//            }
//        // .csrf { csrf -> csrf.disable() } // CSRFを無効にする場合 (推奨されないが、API専用の場合など)
//        return http.build()
//    }
//
//    @Bean
//    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
//        val user = User.withUsername("user")
//            .password(passwordEncoder.encode("password")) // パスワードはエンコードする必要がある
//            .roles("USER")
//            .build()
//
//        val admin = User.withUsername("admin")
//            .password(passwordEncoder.encode("adminpass"))
//            .roles("ADMIN", "USER") // 複数のロールも可能
//            .build()
//
//        return InMemoryUserDetailsManager(user, admin)
//    }
//
//    // 3. パスワードエンコーダーの定義
//    // パスワードを安全に保存・比較するために必要
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//}
