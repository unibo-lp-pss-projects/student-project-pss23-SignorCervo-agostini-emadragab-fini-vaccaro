plugins {
    application
    java
}

repositories {
    mavenCentral()
}

val javaFXModules = listOf(
    "base",
    "controls",
    "media",
    "fxml",
    "swing",
    "graphics"
)

val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    val junitVersion = "4.13.2" // Aggiornato alla versione più recente di JUnit 4
    val javaFxVersion = "22"
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }
    testImplementation("junit:junit:$junitVersion") // Usa JUnit 4
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("org.json:json:20230227")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    //decodificare stringhe
    implementation ("org.apache.commons:commons-text:1.11.0")

}

tasks.test {
    useJUnit()
    testLogging { events ("PASSED", "FAILED", "SKIPPED")
    showStandardStreams = true
    }
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.io.Main")
}
