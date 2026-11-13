package com.drivebuy.exception

open class NotFoundException(message: String) : RuntimeException(message)

class UserNotFoundException(message: String = "User not found") : NotFoundException(message)

class AdNotFoundException(message: String = "Ad not found") : NotFoundException(message)

class ChatNotFoundException(message: String = "Chat not found") : NotFoundException(message)

class MessageNotFoundException(message: String = "Message not found") : NotFoundException(message)

class UserRegistrationException(message: String) : RuntimeException(message)

class UnauthorizedException(message: String) : RuntimeException(message)

class AuthenticationException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class EmailAlreadyExistsException(message: String) : RuntimeException(message)

class InvalidEmailException(message: String) : RuntimeException(message)

class WeakPasswordException(message: String) : RuntimeException(message)

class ConfigurationException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
