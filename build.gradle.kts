plugins {
    id("java")
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.8.0"
}

group = "com.viana.poc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    // Spring Boot Web for REST endpoint
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Avro
    implementation("org.apache.avro:avro")

    // Confluent Avro Serializer
    implementation("io.confluent:kafka-avro-serializer:7.6.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

avro {
    stringType.set("String")
}

springBoot {
    mainClass.set("com.viana.poc.KafkaAvroPocApplication")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
