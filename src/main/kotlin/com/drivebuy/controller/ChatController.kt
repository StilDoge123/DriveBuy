package com.drivebuy.controller

import com.drivebuy.persistance.entity.ChatEntity
import com.drivebuy.persistance.entity.MessageEntity
import com.drivebuy.persistance.request.SendMessageRequest
import com.drivebuy.service.ChatService
import com.google.firebase.auth.FirebaseAuth
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/create")
    fun createOrGetChat(
        @RequestParam adId: Long,
        @RequestParam sellerId: String,
        request: HttpServletRequest
    ): ChatEntity {
        val buyerId = getUidFromRequest(request)
        return chatService.createOrGetChat(adId, buyerId, sellerId)
    }

    @GetMapping
    fun getUserChats(request: HttpServletRequest): List<ChatEntity> {
        val userId = getUidFromRequest(request)
        return chatService.getUserChats(userId)
    }

    @GetMapping("/{chatId}")
    fun getChat(
        @PathVariable chatId: Long,
        request: HttpServletRequest
    ): ChatEntity {
        val userId = getUidFromRequest(request)
        return chatService.getChatById(chatId, userId)
    }

    @GetMapping("/{chatId}/messages")
    fun getChatMessages(
        @PathVariable chatId: Long,
        request: HttpServletRequest
    ): List<MessageEntity> {
        val userId = getUidFromRequest(request)
        return chatService.getChatMessages(chatId, userId)
    }

    @PostMapping("/{chatId}/messages")
    fun sendMessage(
        @PathVariable chatId: Long,
        @RequestBody sendMessageRequest: SendMessageRequest,
        request: HttpServletRequest
    ): MessageEntity {
        val senderId = getUidFromRequest(request)
        return chatService.sendMessage(chatId, senderId, sendMessageRequest.content)
    }

    @PostMapping("/{chatId}/mark-read")
    fun markMessagesAsRead(
        @PathVariable chatId: Long,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val userId = getUidFromRequest(request)
        chatService.markMessagesAsRead(chatId, userId)
        return ResponseEntity.ok("Messages marked as read")
    }

    @GetMapping("/unread-count")
    fun getUnreadMessageCount(request: HttpServletRequest): ResponseEntity<Map<String, Long>> {
        val userId = getUidFromRequest(request)
        val count = chatService.getUnreadMessageCount(userId)
        return ResponseEntity.ok(mapOf("unreadCount" to count))
    }

    @GetMapping("/{chatId}/unread-count")
    fun getUnreadMessageCountForChat(
        @PathVariable chatId: Long,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, Long>> {
        val userId = getUidFromRequest(request)
        val count = chatService.getUnreadMessageCountForChat(chatId, userId)
        return ResponseEntity.ok(mapOf("unreadCount" to count))
    }

    @GetMapping("/ad/{adId}")
    fun getChatsByAdId(
        @PathVariable adId: Long,
        request: HttpServletRequest
    ): List<ChatEntity> {
        val userId = getUidFromRequest(request)
        return chatService.getChatByAdId(adId, userId)
    }

    fun getUidFromRequest(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
            ?: throw RuntimeException("Missing Authorization header")
        if (!authHeader.startsWith("Bearer ")) {
            throw RuntimeException("Invalid Authorization header")
        }
        val idToken = authHeader.removePrefix("Bearer ").trim()
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        return decodedToken.uid
    }
}