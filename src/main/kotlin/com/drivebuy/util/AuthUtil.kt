package com.drivebuy.util

import com.google.firebase.auth.FirebaseAuth
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.simp.SimpMessageHeaderAccessor

object AuthUtil {
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

    fun getUidFromHeaderAccessor(headerAccessor: SimpMessageHeaderAccessor): String {
        val authHeader = headerAccessor.getFirstNativeHeader("Authorization")
            ?: throw RuntimeException("Missing Authorization header")
        if (!authHeader.startsWith("Bearer ")) {
            throw RuntimeException("Invalid Authorization header")
        }
        val idToken = authHeader.removePrefix("Bearer ").trim()
        return getUidFromToken(idToken)
    }

    fun getUidFromToken(idToken: String): String {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        return decodedToken.uid
    }
}
