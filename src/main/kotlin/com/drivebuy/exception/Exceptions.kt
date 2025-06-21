package com.drivebuy.exception

class NotFoundException(message: String) : RuntimeException(message)

class UserRegistrationException(message: String) : RuntimeException(message)

class UnauthorizedException(message: String) : RuntimeException(message)

class EmailAlreadyExistsException(message: String) : RuntimeException(message)

class InvalidEmailException(message: String) : RuntimeException(message)

class WeakPasswordException(message: String) : RuntimeException(message)
