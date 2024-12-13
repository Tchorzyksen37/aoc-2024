plugins {
    kotlin("jvm") version "1.9.10"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

sourceSets {
    main {
        kotlin.srcDir("src/main/kotlin")
    }

    test {
        kotlin.srcDir("src/test/kotlin")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }

    test {
        useJUnitPlatform()
    }
}
