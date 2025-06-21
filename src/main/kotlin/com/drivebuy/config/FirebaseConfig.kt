package com.drivebuy.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.StorageClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.IOException

@Configuration
class FirebaseConfig {

    @Bean
    fun firebaseApp(): FirebaseApp {
        // This is the environment variable you will set on Render.
        val serviceAccountJson = System.getenv("FIREBASE_SERVICE_ACCOUNT_KEY_JSON")
        val storageBucket = System.getenv("FIREBASE_STORAGE_BUCKET")
            ?: throw RuntimeException("FIREBASE_STORAGE_BUCKET environment variable is not set")

        val options: FirebaseOptions
        try {
            if (serviceAccountJson != null && serviceAccountJson.isNotEmpty()) {
                // --- FOR RENDER (PRODUCTION) ---
                // Initialize from the environment variable content.
                val credentials = GoogleCredentials.fromStream(ByteArrayInputStream(serviceAccountJson.toByteArray()))
                options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setStorageBucket(storageBucket)
                    .build()
            } else {
                // --- FOR LOCAL DEVELOPMENT ---
                // Fallback to a local file path.
                // Make sure you have the GOOGLE_APPLICATION_CREDENTIALS env var set locally,
                // or change this path to your local JSON key file.
                val serviceAccountStream = FileInputStream("src/main/resources/drivebuy-firebase-adminsdk.json") // <-- Or your local path
                options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .setStorageBucket(storageBucket)
                    .build()
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to initialize Firebase", e)
        }

        // Avoid re-initializing the app if it's already been set up
        return FirebaseApp.getApps().firstOrNull() ?: FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseAuth(firebaseApp: FirebaseApp): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }

    @Bean
    fun storageClient(firebaseApp: FirebaseApp): StorageClient {
        return StorageClient.getInstance(firebaseApp)
    }
}