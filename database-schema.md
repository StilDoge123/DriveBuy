%%{init: {'theme': 'base', 'themeVariables': {'primaryColor': '#6c757d', 'primaryTextColor': '#ffffff', 'lineColor': '#495057', 'background': '#ffffff', 'fontFamily': 'Arial, sans-serif'}}}%%
erDiagram
    UserEntity {
        String firebaseId PK
        String name
        String email
        String phone
    }

    CarAdEntity {
        Long id PK
        String userId FK
        String make
        String model
        String title
        String description
        Int year
        String color
        Int hp
        Int displacement
        Int mileage
        Int price
        String bodyType
        String condition
        String doorCount
        String cylinderCount
        String transmissionType
        String fuelType
        String steeringPosition
        String driveType
        Int ownerCount
        String phone
        String region
        String city
        List_String_ imageUrls
        List_String_ features
        LocalDateTime createdAt
    }

    CarBrandEntity {
        Long id PK
        String brandName
    }

    CarModelEntity {
        Long id PK
        Long brand_id FK
        String modelName
    }

    ChatEntity {
        Long id PK
        Long adId FK
        String buyerId FK
        String sellerId FK
        LocalDateTime createdAt
        LocalDateTime lastMessageAt
    }

    MessageEntity {
        Long id PK
        Long chatId FK
        String senderId FK
        String content
        LocalDateTime timestamp
        Boolean isRead
    }


    Region {
        Long id PK
        String regionName
    }

    City {
        Long id PK
        Long region_id FK
        String cityName
    }
    
    BodyTypeEntity {
        Long id PK
        String bodyTypeName
    }
    
    CarConditionEntity {
        Long id PK
        String conditionName
    }
    
    CarFeaturesEntity {
        Long id PK
        String featureName
    }
    
    ColorEntity {
        Long id PK
        String colorName
    }
    
    CylinderCountEntity {
        Long id PK
        String cylinderCount
    }
    
    DoorCountEntity {
        Long id PK
        String doorCount
    }
    
    DriveTypeEntity {
        Long id PK
        String driveTypeName
    }
    
    FuelTypeEntity {
        Long id PK
        String fuelTypeName
    }
    
    SteeringPositionEntity {
        Long id PK
        String steeringPositionName
    }
    
    TransmissionTypeEntity {
        Long id PK
        String transmissionTypeName
    }


    UserEntity ||--o{ CarAdEntity : "saves"
    CarModelEntity ||--|{ CarBrandEntity : "belongs to"
    ChatEntity ||--|{ CarAdEntity : "is for"
    ChatEntity }o--|| UserEntity : "buyer"
    ChatEntity }o--|| UserEntity : "seller"
    MessageEntity ||--|{ ChatEntity : "belongs to"
    MessageEntity ||--|{ UserEntity : "sent by"
    ChatEntity o{--|| MessageEntity : "has"
    City ||--|{ Region : "is in"
    
    %% Car Info Relationships to Ads
    CarAdEntity }o--|| CarBrandEntity : "has brand"
    CarAdEntity }o--|| CarModelEntity : "has model"
    CarAdEntity }o--|| Region : "located in region"
    CarAdEntity }o--|| City : "located in city"
    CarAdEntity }o--|| BodyTypeEntity : "has body type"
    CarAdEntity }o--|| CarConditionEntity : "has condition"
    CarAdEntity }o--|| CarFeaturesEntity : "has features"
    CarAdEntity }o--|| ColorEntity : "has color"
    CarAdEntity }o--|| CylinderCountEntity : "has cylinder count"
    CarAdEntity }o--|| DoorCountEntity : "has door count"
    CarAdEntity }o--|| DriveTypeEntity : "has drive type"
    CarAdEntity }o--|| FuelTypeEntity : "has fuel type"
    CarAdEntity }o--|| SteeringPositionEntity : "has steering position"
    CarAdEntity }o--|| TransmissionTypeEntity : "has transmission"

    %% Original simple styling
    classDef userEntity fill:#e9ecef,stroke:#6c757d,stroke-width:2px,color:#495057
    classDef adEntity fill:#d1ecf1,stroke:#0c5460,stroke-width:2px,color:#495057
    classDef chatEntity fill:#f8d7da,stroke:#721c24,stroke-width:2px,color:#495057
    classDef carInfoEntity fill:#fff3cd,stroke:#856404,stroke-width:1px,color:#495057
    classDef locationEntity fill:#d4edda,stroke:#155724,stroke-width:1px,color:#495057
    classDef brandEntity fill:#e2e3e5,stroke:#383d41,stroke-width:1px,color:#495057

    %% Apply styles to entities
    class UserEntity userEntity
    class CarAdEntity adEntity
    class ChatEntity,MessageEntity chatEntity
    class BodyTypeEntity,CarConditionEntity,CarFeaturesEntity,ColorEntity,CylinderCountEntity,DoorCountEntity,DriveTypeEntity,FuelTypeEntity,SteeringPositionEntity,TransmissionTypeEntity carInfoEntity
    class Region,City locationEntity
    class CarBrandEntity,CarModelEntity brandEntity
