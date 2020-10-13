import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nexos.intellij.jddd"

buildscript {
  repositories {
    mavenCentral()
    jcenter()
  }
  dependencies { classpath(kotlin("gradle-plugin", "1.4.10")) }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
  testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.6.2")
  testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.6.2")
  runtimeOnly("org.jetbrains.kotlin","kotlin-reflect", "1.4.10")
  implementation(kotlin("stdlib-jdk8"))
}

plugins {
  java
  kotlin("jvm") version "1.4.10"
  id("org.jetbrains.intellij") version "0.5.0"
}

//val pluginVersion: String by project

tasks.withType<JavaCompile> {
  sourceCompatibility = "11"
  targetCompatibility = "11"
}

// compile bytecode to java 11 (default is java 6)
tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "11"
  //  freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
  }
}

intellij {
  version = "2020.2.3"
  downloadSources = true
  setPlugins("java", "org.jetbrains.plugins.gradle", "gradle")
}

tasks.publishPlugin {
  token(System.getenv("PUBLISH_TOKEN"))
  channels(version.toString().split('-').getOrElse(1) { "default" }.split('.').first())
}