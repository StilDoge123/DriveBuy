package com.drivebuy.db_initialiser

import com.drivebuy.service.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class FirebaseUserSync(
    private val userService: UserService
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        userService.syncFirebaseUsersWithDatabase()
    }
}