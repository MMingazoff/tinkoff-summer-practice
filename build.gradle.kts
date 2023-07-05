plugins {
    id("com.android.application") version "8.1.0-beta01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

detekt {
    source = files(projectDir)
    parallel = true
    config = files("${project.rootDir}/config/detekt/detekt.yml")
}
