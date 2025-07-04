package com.drivebuy.service

import com.google.firebase.cloud.StorageClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*
import com.google.cloud.storage.BlobInfo

@Service
class FirebaseStorageService(
    @Value("\${firebase.storage.bucket}") private val bucketName: String
) {
    fun uploadAdImage(file: MultipartFile): String {
        val bucket = StorageClient.getInstance().bucket(bucketName)
        val filename = "ads/${UUID.randomUUID()}_${file.originalFilename}"
        val token = UUID.randomUUID().toString()
        val blobInfo = BlobInfo.newBuilder(bucket.name, filename)
            .setContentType(file.contentType)
            .setMetadata(mapOf("firebaseStorageDownloadTokens" to token))
            .build()
        val blob = bucket.storage.create(blobInfo, file.bytes)
        return getPublicUrl(filename, token)
    }

    fun deleteImage(url: String) {
        val filename = url.substringAfter("o/").substringBefore("?alt=media")
        StorageClient.getInstance().bucket(bucketName)
            .get(URLDecoder.decode(filename, "UTF-8"))
            .delete()
    }

    private fun getPublicUrl(filename: String, token: String?): String {
        val baseUrl = "https://firebasestorage.googleapis.com/v0/b/${bucketName}/o/" +
                URLEncoder.encode(filename, "UTF-8") + "?alt=media"
        
        return if (token != null) {
            "$baseUrl&token=$token"
        } else {
            baseUrl
        }
    }
}
