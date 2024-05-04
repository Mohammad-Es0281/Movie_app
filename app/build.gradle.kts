import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

val envFile: File = rootProject.file(".env")
val env = Properties().apply {
    load(envFile.inputStream())
}

android {
    namespace = "com.sample.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.movie"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"
        setProperty("archivesBaseName", "${rootProject.name}-${versionName}")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations.addAll(listOf("fa", "en"))
    }

    signingConfigs {

        register("release") {
            storeFile = file(env.getProperty("RELEASE_STORE_FILE"))
            storePassword = env.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = env.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = env.getProperty("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            manifestPlaceholders["environment"] = "debug"
        }
        applicationVariants.all {
            val variant = this
            variant.buildConfigField("String", "BASE_URL", env.getProperty("BASE_URL"))
            variant.buildConfigField(
                "String",
                "BASE_STORAGE_URL",
                env.getProperty("BASE_STORAGE_URL")
            )

            variant.buildConfigField("String", "ACCESS_TOKEN", env.getProperty("ACCESS_TOKEN"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    allprojects {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
            }
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        )
    }

}

dependencies {
    // Support Library
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.splashscreen)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui.util)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.compose.animation)

    // Accompanist
    implementation(libs.accompanist.permissions)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.destination.core)
    ksp(libs.destination.ksp)
    implementation(libs.destination.animation)

    // Moshi
    implementation(libs.converter.moshi)
    ksp(libs.moshi.codegen)

    // Network
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.moshi.kotlin)

    // Timber
    implementation(libs.timber)

    // Room
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.paging)

    // Data store
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)

    // Work manager
    implementation(libs.android.work)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Hilt
    implementation(libs.hilt)
    implementation(libs.hilt.commons)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.android.hilt.compiler)
    ksp(libs.hilt.compiler)

    // Lottie
    implementation(libs.lottie)

    testImplementation(libs.junit)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
}
