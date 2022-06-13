plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Libs.Kotlin.version
}

android {
    compileSdk = App.compileSdkVersion

    defaultConfig {
        minSdk = App.minSdkVersion
        targetSdk = App.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))

    implementation(KotlinX.coroutines.core)

    // Retrofit
    implementation(Square.retrofit2.retrofit)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Libs.OkHttp.profiler)

    // Kotlin Serialization
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.json)

    // Room
    implementation(AndroidX.room.ktx)
    implementation(AndroidX.room.runtime)
    annotationProcessor(AndroidX.room.compiler)
    kapt(AndroidX.room.compiler)

    // Koin
    implementation(Koin.core)
    implementation(Koin.android)

    testImplementation(Libs.Android.archCoreTesting)
    testImplementation(AndroidX.room.testing)
    testImplementation(Testing.junit4)
    testImplementation(Testing.Mockito.kotlin)
    testImplementation(Testing.Mockito.inline)
    testImplementation(Kotlin.test.testng)
    testImplementation(KotlinX.coroutines.test)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(Kotlin.test.testng)
    androidTestImplementation(AndroidX.test.espresso.core)
    androidTestImplementation(AndroidX.test.rules)
    androidTestImplementation(AndroidX.test.runner)
    androidTestImplementation(AndroidX.test.coreKtx)
}