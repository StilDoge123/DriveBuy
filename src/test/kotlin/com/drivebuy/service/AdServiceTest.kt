package com.drivebuy.service

import com.drivebuy.persistance.entity.CarAdEntity
import com.drivebuy.persistance.entity.UserEntity
import com.drivebuy.persistance.request.CreateAdRequest
import com.drivebuy.persistance.request.UpdateAdRequest
import com.drivebuy.repository.AdRepository
import com.drivebuy.repository.ChatRepository
import com.drivebuy.repository.MessageRepository
import com.drivebuy.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

class AdServiceTest {

    private lateinit var adRepository: AdRepository
    private lateinit var userRepository: UserRepository
    private lateinit var storageService: FirebaseStorageService
    private lateinit var chatRepository: ChatRepository
    private lateinit var messageRepository: MessageRepository
    private lateinit var adService: AdService
    
    @BeforeEach
    fun setup() {
        adRepository = mock()
        userRepository = mock()
        storageService = mock()
        chatRepository = mock()
        messageRepository = mock()
        
        adService = AdService(
            adRepository,
            userRepository,
            storageService,
            chatRepository,
            messageRepository
        )
    }

    @Test
    fun `createAd should create ad successfully when user exists`() {
        val userId = "firebase123"
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val imageFile = mock<MultipartFile>()
        val request = CreateAdRequest(
            userId = userId,
            make = "Toyota",
            model = "Camry",
            title = "2020 Toyota Camry",
            description = "Great car",
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
            phone = "123456789",
            region = "Region1",
            city = "City1",
            images = listOf(imageFile),
            features = listOf("GPS", "Bluetooth")
        )

        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(storageService.uploadAdImage(imageFile)).thenReturn("http://image.url")
        doAnswer { it.arguments[0] }.whenever(adRepository).save(any())

        val result = adService.createAd(request)

        assertNotNull(result)
        assertEquals("Toyota", result.make)
        assertEquals("Camry", result.model)
        assertEquals(2020, result.year)
        verify(userRepository).findByFirebaseId(userId)
        verify(storageService).uploadAdImage(imageFile)
        verify(adRepository).save(any())
    }

    @Test
    fun `createAd should throw exception when user not found`() {
        val userId = "nonexistent"
        val imageFile = mock<MultipartFile>()
        val request = CreateAdRequest(
            userId = userId,
            make = "Toyota",
            model = "Camry",
            title = "2020 Toyota Camry",
            description = "Great car",
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
            phone = "123456789",
            region = "Region1",
            city = "City1",
            images = listOf(imageFile),
            features = listOf()
        )

        whenever(userRepository.findByFirebaseId(userId)).thenThrow(RuntimeException("User not found"))

        val exception = assertThrows<RuntimeException> {
            adService.createAd(request)
        }

        assertEquals("User not found. Register or login to create an ad.", exception.message)
    }

    @Test
    fun `updateAd should update ad successfully when authorized`() {
        val userId = "firebase123"
        val adId = 1L
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val existingAd = CarAdEntity(
            id = adId,
            userId = userId,
            make = "Toyota",
            model = "Camry",
            title = "Old Title",
            description = "Old desc",
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

        val updateRequest = UpdateAdRequest(
            userId = userId,
            title = "New Title",
            price = 25000,
            imagesToDelete = emptyList(),
            newImages = emptyList()
        )

        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(adRepository.findById(adId)).thenReturn(Optional.of(existingAd))
        doAnswer { it.arguments[0] }.whenever(adRepository).save(any())

        val result = adService.updateAd(adId, updateRequest)

        assertNotNull(result)
        assertEquals("New Title", result.title)
        assertEquals(25000, result.price)
        verify(adRepository).save(any())
    }

    @Test
    fun `updateAd should throw exception when user not authorized`() {
        val userId = "firebase123"
        val otherUserId = "other456"
        val adId = 1L
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val existingAd = CarAdEntity(
            id = adId,
            userId = otherUserId,
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

        val updateRequest = UpdateAdRequest(
            userId = userId,
            title = "New Title",
            imagesToDelete = emptyList(),
            newImages = emptyList()
        )

        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(adRepository.findById(adId)).thenReturn(Optional.of(existingAd))

        val exception = assertThrows<RuntimeException> {
            adService.updateAd(adId, updateRequest)
        }

        assertEquals("You are not authorized to modify this ad", exception.message)
    }

    @Test
    fun `deleteAd should delete ad successfully when authorized`() {
        val userId = "firebase123"
        val adId = 1L
        val user = UserEntity(firebaseId = userId, name = "John", email = "john@test.com", phone = "123")
        val ad = CarAdEntity(
            id = adId,
            userId = userId,
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
            city = "City",
            imageUrls = mutableListOf("http://image1.url")
        )

        whenever(userRepository.findByFirebaseId(userId)).thenReturn(user)
        whenever(adRepository.findById(adId)).thenReturn(Optional.of(ad))
        whenever(chatRepository.findByAdId(adId)).thenReturn(emptyList())
        whenever(adRepository.countOtherAdsReferencingImage(any<String>(), eq(adId))).thenReturn(0L)

        adService.deleteAd(adId, userId)

        verify(userRepository).deleteSavedAdLinksByAdId(adId)
        verify(chatRepository).deleteAllByAdId(adId)
        verify(storageService).deleteImage("http://image1.url")
        verify(adRepository).delete(ad)
    }

    @Test
    fun `getAdById should return ad when found`() {
        val adId = 1L
        val ad = CarAdEntity(
            id = adId,
            userId = "user123",
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

        whenever(adRepository.findById(adId)).thenReturn(Optional.of(ad))

        val result = adService.getAdById(adId)

        assertNotNull(result)
        assertEquals("Toyota", result?.make)
    }

    @Test
    fun `getAdById should return null when not found`() {
        val adId = 999L

        whenever(adRepository.findById(adId)).thenReturn(Optional.empty())

        val result = adService.getAdById(adId)

        assertNull(result)
    }

    @Test
    fun `getAdsByUserId should return user ads`() {
        val userId = "user123"
        val ads = listOf(
            CarAdEntity(
                id = 1L,
                userId = userId,
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
        )

        whenever(adRepository.findByUserId(userId)).thenReturn(ads)

        val result = adService.getAdsByUserId(userId)

        assertEquals(1, result.size)
        assertEquals(userId, result[0].userId)
    }
}
