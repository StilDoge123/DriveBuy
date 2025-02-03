plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.liquibase.gradle") version "2.2.0"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	sourceSets.main.get().resources.srcDir("src/main/resources")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("org.springframework.boot:spring-boot-starter-liquibase")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.liquibase:liquibase-core")

	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.postgresql", "postgresql")
	liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:4.23.1")
	liquibaseRuntime("org.springframework.boot:spring-boot:3.0.2")
	liquibaseRuntime("jakarta.persistence:jakarta.persistence-api:3.0.0")
	liquibaseRuntime("org.springframework:spring-orm")
	liquibaseRuntime("org.jetbrains.kotlin:kotlin-reflect")
	liquibaseRuntime("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	liquibaseRuntime("info.picocli:picocli:4.7.4")
	liquibaseRuntime(sourceSets.main.get().output)

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

liquibase {
	tasks.processResources {
		duplicatesStrategy = DuplicatesStrategy.INCLUDE // or INCLUDE if you want to allow duplicates
	}
	activities {
		register("main") {
			arguments = mapOf(
				"logLevel" to "info",
				"changelogFile" to "src/main/resources/db/changelog/db.changelog-master.yaml", // Updated here
				"url" to "jdbc:postgresql://localhost:5454/drivebuydb",
				"username" to "postgres",
				"password" to "123456",
				"driver" to "org.postgresql.Driver",
				"referenceDriver" to "liquibase.ext.hibernate.database.connection.HibernateDriver",
				"referenceUrl" to "hibernate:spring:com.appolica.**?dialect=org.hibernate.dialect.PostgreSQL9Dialect&hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
			)
		}
	}
	runList = "main"
}


allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
