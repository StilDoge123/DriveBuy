package com.drivebuy.persistance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chats")
data class ChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val adId: Long,

    @Column(nullable = false)
    val buyerId: String,

    @Column(nullable = false)
    val sellerId: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var lastMessageAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "chatId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val messages: MutableList<MessageEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adId", insertable = false, updatable = false)
    val ad: CarAdEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId", referencedColumnName = "firebaseId", insertable = false, updatable = false)
    val buyer: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId", referencedColumnName = "firebaseId", insertable = false, updatable = false)
    val seller: UserEntity? = null
) {
    @PreUpdate
    fun preUpdate() {
        lastMessageAt = LocalDateTime.now()
    }
}
