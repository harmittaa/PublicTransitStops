import org.jetbrains.kotlin.konan.properties.loadProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.apollographql.apollo3)
}

android {
    namespace = "com.harmittaa.publictransitstops"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.harmittaa.publictransitstops"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Fetches the digitransit API key from endpoint.properties file,
        // located in the project root
        val projectProperties = loadProperties("endpoint.properties")
        val digitransitKey = projectProperties.getProperty("DIGITRANSIT_KEY")
        val digitransitUrl = projectProperties.getProperty("DIGITRANSIT_URL")

        buildConfigField("String", "DIGITRANSIT_KEY", digitransitKey)
        buildConfigField("String", "DIGITRANSIT_URL", digitransitUrl
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    apollo {
        service("service") {
            packageName.set("com.harmittaa.publictransitstops")
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.coroutines.support)
    implementation(libs.koin.android.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}