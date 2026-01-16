package com.drivebuy.db_initialiser

import com.drivebuy.persistance.entity.car_info.*
import com.drivebuy.persistance.enums.*
import com.drivebuy.repository.car_info.*
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class CarInfoInitializer(
    private val bodyTypeRepository: BodyTypeRepository,
    private val carConditionRepository: CarConditionRepository,
    private val featuresRepository: CarFeaturesRepository,
    private val colorRepository: ColorRepository,
    private val cylinderCountRepository: CylinderCountRepository,
    private val doorCountRepository: DoorCountRepository,
    private val driveTypeRepository: DriveTypeRepository,
    private val fuelTypeRepository: FuelTypeRepository,
    private val steeringPositionRepository: SteeringPositionRepository,
    private val transmissionTypeRepository: TransmissionTypeRepository
) {
    // Disabled - using Flyway migrations instead
    // @PostConstruct
    @Transactional
    fun init() {
        // Body Types
        val bodyTypeEnumValues = BodyTypeEnum.entries.map { it.typeName }.toSet()
        val bodyTypeRepoEntries = bodyTypeRepository.findAll()

        bodyTypeRepoEntries.forEach {
            if (!bodyTypeEnumValues.contains(it.bodyTypeName)) {
                bodyTypeRepository.delete(it)
            }
        }

        BodyTypeEnum.entries.forEach {
            if (bodyTypeRepository.findByBodyTypeName(it.typeName) == null) {
                bodyTypeRepository.save(BodyTypeEntity(bodyTypeName = it.typeName))
            }
        }

        // Car Conditions
        val carConditionEnumValues = CarConditionEnum.entries.map { it.conditionName }.toSet()
        val carConditionRepoEntries = carConditionRepository.findAll()

        carConditionRepoEntries.forEach {
            if (!carConditionEnumValues.contains(it.conditionName)) {
                carConditionRepository.delete(it)
            }
        }

        CarConditionEnum.entries.forEach {
            if (carConditionRepository.findByConditionName(it.conditionName) == null) {
                carConditionRepository.save(CarConditionEntity(conditionName = it.conditionName))
            }
        }

        // Car Features
        val featuresEnumValues = CarFeaturesEnum.entries.map { it.featureName }.toSet()
        val featuresRepoEntries = featuresRepository.findAll()

        featuresRepoEntries.forEach {
            if (!featuresEnumValues.contains(it.featureName)) {
                featuresRepository.delete(it)
            }
        }

        CarFeaturesEnum.entries.forEach {
            if (featuresRepository.findByFeatureName(it.featureName) == null) {
                featuresRepository.save(CarFeaturesEntity(featureName = it.featureName))
            }
        }

        // Colors
        val colorEnumValues = ColorEnum.entries.map { it.colorName }.toSet()
        val colorRepoEntries = colorRepository.findAll()

        colorRepoEntries.forEach {
            if (!colorEnumValues.contains(it.colorName)) {
                colorRepository.delete(it)
            }
        }

        ColorEnum.entries.forEach {
            if (colorRepository.findByColorName(it.colorName) == null) {
                colorRepository.save(ColorEntity(colorName = it.colorName))
            }
        }

        // Cylinder Counts
        val cylinderCountEnumValues = CylinderCountEnum.entries.map { it.count }.toSet()
        val cylinderCountRepoEntries = cylinderCountRepository.findAll()

        cylinderCountRepoEntries.forEach {
            if (!cylinderCountEnumValues.contains(it.cylinderCount)) {
                cylinderCountRepository.delete(it)
            }
        }

        CylinderCountEnum.entries.forEach {
            if (cylinderCountRepository.findByCylinderCount(it.count) == null) {
                cylinderCountRepository.save(CylinderCountEntity(cylinderCount = it.count))
            }
        }

        // Door Counts
        val doorCountEnumValues = DoorCountEnum.entries.map { it.count }.toSet()
        val doorCountRepoEntries = doorCountRepository.findAll()

        doorCountRepoEntries.forEach {
            if (!doorCountEnumValues.contains(it.doorCount)) {
                doorCountRepository.delete(it)
            }
        }

        DoorCountEnum.entries.forEach {
            if (doorCountRepository.findByDoorCount(it.count) == null) {
                doorCountRepository.save(DoorCountEntity(doorCount = it.count))
            }
        }

        // Drive Types
        val driveTypeEnumValues = DriveTypeEnum.entries.map { it.typeName }.toSet()
        val driveTypeRepoEntries = driveTypeRepository.findAll()

        driveTypeRepoEntries.forEach {
            if (!driveTypeEnumValues.contains(it.driveTypeName)) {
                driveTypeRepository.delete(it)
            }
        }

        DriveTypeEnum.entries.forEach {
            if (driveTypeRepository.findByDriveTypeName(it.typeName) == null) {
                driveTypeRepository.save(DriveTypeEntity(driveTypeName = it.typeName))
            }
        }

        // Fuel Types
        val fuelTypeEnumValues = FuelTypeEnum.entries.map { it.typeName }.toSet()
        val fuelTypeRepoEntries = fuelTypeRepository.findAll()

        fuelTypeRepoEntries.forEach {
            if (!fuelTypeEnumValues.contains(it.fuelTypeName)) {
                fuelTypeRepository.delete(it)
            }
        }

        FuelTypeEnum.entries.forEach {
            if (fuelTypeRepository.findByFuelTypeName(it.typeName) == null) {
                fuelTypeRepository.save(FuelTypeEntity(fuelTypeName = it.typeName))
            }
        }

        // Steering Positions
        val steeringEnumValues = SteeringPositionEnum.entries.map { it.positionName }.toSet()
        val steeringRepoEntries = steeringPositionRepository.findAll()

        steeringRepoEntries.forEach {
            if (!steeringEnumValues.contains(it.steeringPositionName)) {
                steeringPositionRepository.delete(it)
            }
        }

        SteeringPositionEnum.entries.forEach {
            if (steeringPositionRepository.findBySteeringPositionName(it.positionName) == null) {
                steeringPositionRepository.save(SteeringPositionEntity(steeringPositionName = it.positionName))
            }
        }

        // Transmission Types
        val transmissionEnumValues = TransmissionTypeEnum.entries.map { it.typeName }.toSet()
        val transmissionRepoEntries = transmissionTypeRepository.findAll()

        transmissionRepoEntries.forEach {
            if (!transmissionEnumValues.contains(it.transmissionTypeName)) {
                transmissionTypeRepository.delete(it)
            }
        }

        TransmissionTypeEnum.entries.forEach {
            if (transmissionTypeRepository.findByTransmissionTypeName(it.typeName) == null) {
                transmissionTypeRepository.save(TransmissionTypeEntity(transmissionTypeName = it.typeName))
            }
        }
    }
}