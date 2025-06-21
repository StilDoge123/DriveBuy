package com.drivebuy.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import com.google.firebase.cloud.StorageClient
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.UUID

@Service
class FirebaseStorageService {
    
    private val bucketName: String = System.getenv("FIREBASE_STORAGE_BUCKET") 
        ?: throw RuntimeException("FIREBASE_STORAGE_BUCKET environment variable is not set")

    fun uploadAdImage(file: MultipartFile): String {
        val bucket = StorageClient.getInstance().bucket(bucketName)
        val filename = "ads/${UUID.randomUUID()}_${file.originalFilename}"
        val blob = bucket.create(filename, file.bytes, file.contentType)

        return getPublicUrl(filename)
    }

    fun deleteImage(url: String) {
        val filename = url.substringAfter("o/").substringBefore("?alt=media")
        StorageClient.getInstance().bucket(bucketName)
            .get(URLDecoder.decode(filename, "UTF-8"))
            .delete()
    }

    private fun getPublicUrl(filename: String): String {
        return "https://firebasestorage.googleapis.com/v0/b/${bucketName}/o/" +
                URLEncoder.encode(filename, "UTF-8") + "?alt=media"
    }
}
