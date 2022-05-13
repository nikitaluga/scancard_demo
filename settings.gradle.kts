dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://pay.cards/maven")
    }
}
rootProject.name = "Scan card demo rv"
include(":app")
