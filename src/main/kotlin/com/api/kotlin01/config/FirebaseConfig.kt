package com.api.kotlin01.config
// Kotlin 例
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import java.io.IOException
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean

@Configuration
class FirebaseConfig(
    private val resourceLoader: ResourceLoader
) {

    @Value("\${firebase.service-account.path}")
    private lateinit var serviceAccountPath: String // @Valueでパスが注入される

    @Bean
    fun firebaseApp(): FirebaseApp {
        // resourceLoader.getResource() は classpath:, file:, url: などのプレフィックスを自動的に解釈します
        val resource = resourceLoader.getResource(serviceAccountPath)
        if (!resource.exists()) {
            throw IOException("Firebase service account file not found at: $serviceAccountPath")
        }

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(resource.inputStream)) // JSONファイルをInputStreamとして渡す
            // .setDatabaseUrl("https://your-project-id.firebaseio.com") // 必要に応じて設定
            .build()

        return if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options) // FirebaseAppがまだ初期化されていなければ初期化
        } else {
            FirebaseApp.getInstance() // 既に初期化されていれば既存のインスタンスを返す
        }
    }
}
