package com.drivebuy.service

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.persistance.request.UpdateUserRequest
import com.drivebuy.repository.AdRepository
import com.drivebuy.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.util.*

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var adRepository: AdRepository
    private lateinit var userService: UserService
    
    @BeforeEach
    fun setup() {
        userRepository = mock()
        adRepository = mock()
        userService = UserService(userRepository, adRepository)
    }

    @Test
    fun `getUserById should return user when found`() {
        val userId = "firebase123"
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")

        whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))

        val result = userService.getUserById(userId)

        assertNotNull(result)
        assertEquals("John", result.name)
        assertEquals("john@test.com", result.email)
    }

    @Test
    fun `getAllUsers should return all users`() {
        val users = listOf(
            UserEntity(firebaseId = "user1", name = "John", email = "john@test.com", phone = "123"),
            UserEntity(firebaseId = "user2", name = "Jane", email = "jane@test.com", phone = "456")
        )

        whenever(userRepository.findAll()).thenReturn(users)

        val result = userService.getAllUsers()

        assertEquals(2, result.size)
    }

    @Test
    fun `updateUser should update user successfully`() {
        val userId = "firebase123"
        val existingUser = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val updateRequest = UpdateUserRequest(
            name = "John Updated",
            email = "newemail@test.com",
            phone = "999"
        )

        whenever(userRepository.findById(userId)).thenReturn(Optional.of(existingUser))
        doAnswer { it.arguments[0] }.whenever(userRepository).save(any())

        val result = userService.updateUser(userId, updateRequest)

        assertEquals("John Updated", result.name)
        assertEquals("newemail@test.com", result.email)
        assertEquals("999", result.phone)
        verify(userRepository).save(any())
    }

    @Test
    fun `updateUser should throw exception when user not found`() {
        val userId = "nonexistent"
        val updateRequest = UpdateUserRequest(name = "Test", email = null, phone = null)

        whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            userService.updateUser(userId, updateRequest)
        }

        assertEquals("User not found", exception.message)
    }

    @Test
    fun `saveAd should add ad to user's saved ads`() {
        val userId = "firebase123"
        val adId = 1L
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val ad = CarAdEntity(
            id = adId,
            userId = "seller123",
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )

        whenever(userRepository.existsById(userId)).thenReturn(true)
        whenever(adRepository.existsById(adId)).thenReturn(true)
        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(adRepository.findEntityById(adId)).thenReturn(ad)
        doAnswer { it.arguments[0] }.whenever(userRepository).save(any())

        val result = userService.saveAd(userId, adId)

        assertTrue(result.contains(ad))
        verify(userRepository).save(user)
    }

    @Test
    fun `saveAd should throw exception when user not found`() {
        val userId = "nonexistent"
        val adId = 1L

        whenever(userRepository.existsById(userId)).thenReturn(false)

        val exception = assertThrows<RuntimeException> {
            userService.saveAd(userId, adId)
        }

        assertEquals("User not found", exception.message)
    }

    @Test
    fun `saveAd should throw exception when ad not found`() {
        val userId = "firebase123"
        val adId = 999L

        whenever(userRepository.existsById(userId)).thenReturn(true)
        whenever(adRepository.existsById(adId)).thenReturn(false)

        val exception = assertThrows<RuntimeException> {
            userService.saveAd(userId, adId)
        }

        assertEquals("Ad not found", exception.message)
    }

    @Test
    fun `removeSavedAd should remove ad from user's saved ads`() {
        val userId = "firebase123"
        val adId = 1L
        val ad = CarAdEntity(
            id = adId,
            userId = "seller123",
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )
        val user = UserEntity(
            firebaseId = userId, 
            name = "John", 
            email = "john@test.com", 
            phone = "123",
            savedAds = mutableListOf(ad)
        )

        whenever(userRepository.existsById(userId)).thenReturn(true)
        whenever(adRepository.existsById(adId)).thenReturn(true)
        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(adRepository.findEntityById(adId)).thenReturn(ad)
        doAnswer { it.arguments[0] }.whenever(userRepository).save(any())

        val result = userService.removeSavedAd(userId, adId)

        assertFalse(result.contains(ad))
        verify(userRepository).save(user)
    }

    @Test
    fun `getSavedAds should return user's saved ads`() {
        val userId = "firebase123"
        val ad = CarAdEntity(
            id = 1L,
            userId = "seller123",
            make = "Toyota",
            model = "Camry",
            title = "Title",
            description = "Desc",
            year = 2020,
            color = "Red",
            bodyType = "Sedan",
            condition = "Used",
            hp = 200,
            displacement = 2500,
            mileage = 50000,
            price = 20000,
            doorCount = "4",
            cylinderCount = "4",
            transmissionType = "Automatic",
            fuelType = "Gasoline",
            steeringPosition = "Left",
            driveType = "FWD",
            ownerCount = 1,
            phone = "123",
            region = "Region",
            city = "City"
        )
        val user = UserEntity(
            firebaseId = userId,
            name = "John",
            email = "john@test.com",
            phone = "123",
            savedAds = mutableListOf(ad)
        )

        whenever(userRepository.existsById(userId)).thenReturn(true)
        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)

        val result = userService.getSavedAds(userId)

        assertEquals(1, result.size)
        assertEquals(ad, result[0])
    }

    @Test
    fun `getSavedAds should throw exception when user not found`() {
        val userId = "nonexistent"

        whenever(userRepository.existsById(userId)).thenReturn(false)

        val exception = assertThrows<RuntimeException> {
            userService.getSavedAds(userId)
        }

        assertEquals("User not found", exception.message)
    }
}
