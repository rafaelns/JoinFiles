plugins {
    id("java")
    application
}

group = "com.joinfiles"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.joinfiles.JoinFilesApplication")
}

dependencies {
    implementation("org.apache.commons:commons-configuration2:2.8.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("org.junit-pioneer:junit-pioneer:1.9.1")
}

tasks.test {
    useJUnitPlatform()
}