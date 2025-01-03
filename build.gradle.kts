// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.androidTest).apply(false)
    alias(libs.plugins.kapt).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.apollo).apply(false)
    alias(libs.plugins.daggerHiltAndroidGradle).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
}