import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nexos.intellij.ddd"

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
  maven (url= "https://www.jetbrains.com/intellij-repository/releases")
  maven ( url= "https://jetbrains.bintray.com/intellij-third-party-dependencies" )
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
  id("org.jetbrains.intellij") version "0.6.3"
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
  version = "203.5419-EAP-CANDIDATE-SNAPSHOT"
  downloadSources = true
  setPlugins("java", "org.jetbrains.plugins.gradle", "gradle")
}

tasks.publishPlugin {
  token(System.getenv("PUBLISH_TOKEN"))
  channels(version.toString().split('-').getOrElse(1) { "default" }.split('.').first())
}