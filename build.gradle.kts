plugins {
    id("java")
    application
}

group = "br.com.rns"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("br.com.rns.joinfiles.JoinFilesApplication")
}

dependencies {
    implementation("org.apache.commons:commons-configuration2:2.8.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}