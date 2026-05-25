plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.archive"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.fasterxml.jackson.core:jackson-databind")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("org.springframework.restdocs.outputDir", layout.buildDirectory.dir("generated-snippets").get().asFile.absolutePath)
}

// snippets → markdown 변환 태스크
tasks.register<Exec>("convertSnippetsToMarkdown") {
    dependsOn("test")
    val scriptFile = file("scripts/convert-snippets.py")
    val snippetsDir = layout.buildDirectory.dir("generated-snippets").get().asFile
    val outputDir = file("docs/api")
    commandLine("python3", scriptFile.absolutePath, snippetsDir.absolutePath, outputDir.absolutePath)
}