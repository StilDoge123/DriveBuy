-- Migration: Seed Car Information Tables
-- This migration populates body types, colors, fuel types, and other car-related reference data

-- Body Types
INSERT INTO body_types (body_type_name) VALUES
('Седан'), ('Комби'), ('Хечбек'), ('Купе'), ('Кабрио'), ('Джип'), ('Ван'), ('Пикап')
ON CONFLICT (body_type_name) DO NOTHING;

-- Car Conditions
INSERT INTO car_conditions (condition_name) VALUES
('Нов'), ('Използван'), ('Катастрофирал'), ('За части')
ON CONFLICT (condition_name) DO NOTHING;

-- Car Features
INSERT INTO car_features (feature_name) VALUES
('Климатик'), ('Климатроник'), ('Затъмнени стъкла'), ('Ел. стъкла'), ('Ел. огледала'),
('Ел. седалки'), ('Подгрев на седалки'), ('Вентилирани седалки'), ('Подгрев на задни седалки'),
('Вентилирани задни седалки'), ('Масажиращи седалки'), ('Спортни седалки'),
('Подгрев на волана'), ('Подгрев на огледалата'), ('Мултифункционален волан'),
('Парктроник'), ('Задна камера'), ('Предна и задна камера'), ('360° камера'),
('Блутут'), ('Кабелен Apple Carplay'), ('Кабелен Android Auto'),
('Безжичен Apple Carplay'), ('Безжичен Android Auto'), ('Навигация'),
('Панорамен покрив'), ('Шибедах'), ('Круиз контрол'), ('Адаптивен круиз контрол'),
('ABS'), ('ESC'), ('Тракшън контрол'), ('Аларма'), ('Имобилайзер'),
('Централно заключване'), ('Въздушни възглавници'), ('Серво усилване на волана'),
('Безключов достъп'), ('Алуминиеви джанти'), ('Теглич'), ('Такси'),
('Резервна гума'), ('Крик'), ('7 Места'), ('Кожен салон')
ON CONFLICT (feature_name) DO NOTHING;

-- Colors
INSERT INTO colors (color_name) VALUES
('Червен'), ('Син'), ('Зелен'), ('Жълт'), ('Черен'), ('Бял'), ('Сив'),
('Сребрист'), ('Златист'), ('Оранжев'), ('Лилав'), ('Розов'), ('Кафяв'),
('Бежов'), ('Бронзов'), ('Бордо'), ('Хамелеон'), ('Многоцветен'), ('Друг')
ON CONFLICT (color_name) DO NOTHING;

-- Cylinder Counts
INSERT INTO cylinder_counts (cylinder_count) VALUES
('1'), ('2'), ('3'), ('4'), ('5'), ('6'), ('8'), ('10'), ('12'), ('16')
ON CONFLICT (cylinder_count) DO NOTHING;

-- Door Counts
INSERT INTO door_counts (door_count) VALUES
('2/3'), ('4/5')
ON CONFLICT (door_count) DO NOTHING;

-- Drive Types
INSERT INTO drive_types (drive_type_name) VALUES
('Предно'), ('Задно'), ('4x4')
ON CONFLICT (drive_type_name) DO NOTHING;

-- Fuel Types
INSERT INTO fuel_types (fuel_type_name) VALUES
('Бензин'), ('Дизел'), ('Газ/Бензин'), ('Метан/Бензин'),
('Водород'), ('Електричество'), ('Хибрид')
ON CONFLICT (fuel_type_name) DO NOTHING;

-- Steering Positions
INSERT INTO steering_positions (steering_position_name) VALUES
('Ляво'), ('Дясно')
ON CONFLICT (steering_position_name) DO NOTHING;

-- Transmission Types
INSERT INTO transmission_types (transmission_type_name) VALUES
('Автоматична'), ('Ръчна')
ON CONFLICT (transmission_type_name) DO NOTHING;
