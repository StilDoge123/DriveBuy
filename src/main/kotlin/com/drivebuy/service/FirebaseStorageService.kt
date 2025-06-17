package com.drivebuy.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import com.google.firebase.cloud.StorageClient
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.core.io.ClassPathResource
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.UUID


@ConfigurationProperties(prefix = "firebase.storage")
data class FirebaseStorageProperties(
    val bucket: String
)

@ConfigurationProperties(prefix = "firebase")
data class FirebaseProperties(
    val serviceAccountPath: String,
    val storage: FirebaseStorageProperties
)

@Service
@EnableConfigurationProperties(FirebaseProperties::class)
class FirebaseStorageService(
    private val firebaseProperties: FirebaseProperties
) {
    @PostConstruct
    fun initialize() {
        val serviceAccount = ClassPathResource(firebaseProperties.serviceAccountPath).inputStream

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket(firebaseProperties.storage.bucket)
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun uploadAdImage(file: MultipartFile): String {
        val bucket = StorageClient.getInstance().bucket(firebaseProperties.storage.bucket)
        val filename = "ads/${UUID.randomUUID()}_${file.originalFilename}"
        val blob = bucket.create(filename, file.bytes, file.contentType)

        return getPublicUrl(filename)
    }

    fun deleteImage(url: String) {
        val filename = url.substringAfter("o/").substringBefore("?alt=media")
        StorageClient.getInstance().bucket(firebaseProperties.storage.bucket)
            .get(URLDecoder.decode(filename, "UTF-8"))
            .delete()
    }

    private fun getPublicUrl(filename: String): String {
        return "https://firebasestorage.googleapis.com/v0/b/${firebaseProperties.storage.bucket}/o/" +
                URLEncoder.encode(filename, "UTF-8") + "?alt=media"
    }
}
