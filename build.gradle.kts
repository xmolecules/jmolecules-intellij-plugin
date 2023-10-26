import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nexos.intellij.ddd"

buildscript {
  repositories {
    mavenCentral()
  }
}

repositories {
  mavenCentral()
  maven (url= "https://www.jetbrains.com/intellij-repository/releases")
  maven ( url= "https://jetbrains.bintray.com/intellij-third-party-dependencies" )
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
  testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.10.0")
  testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.10.0")
  runtimeOnly("org.jetbrains.kotlin","kotlin-reflect", "1.9.10")
}

 plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.10"
  id("org.jetbrains.intellij") version "1.16.0"
}

val pluginVersion: String by project

tasks.withType<JavaCompile> {
  sourceCompatibility = "17"
  targetCompatibility = "17"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

intellij {
  updateSinceUntilBuild.set(false)
  downloadSources.set(true)
  plugins.set(listOf("java", "org.jetbrains.plugins.gradle", "com.intellij.java"))
  version.set("2023.2.4")
}

tasks.publishPlugin {
  token.set(System.getenv("PUBLISH_TOKEN"))
  channels.set(listOf(version.toString().split('-').getOrElse(1) { "default" }.split('.').first()))
}
