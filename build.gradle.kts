import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nexos.intellij.ddd"

buildscript {
  repositories {
    mavenCentral()
  }
  //dependencies { classpath(kotlin("gradle-plugin", "1.4.10")) }
}

repositories {
  mavenCentral()
  maven (url= "https://www.jetbrains.com/intellij-repository/releases")
  maven ( url= "https://jetbrains.bintray.com/intellij-third-party-dependencies" )
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
  testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
  testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")
  runtimeOnly("org.jetbrains.kotlin","kotlin-reflect", "1.5.10")
 // implementation(kotlin("stdlib-jdk8"))
}

plugins {
  java
  id("org.jetbrains.kotlin.jvm") version "1.5.10"
  id("org.jetbrains.intellij") version "1.1.2"
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

tasks.withType<Test> {
  useJUnitPlatform()
}

intellij {
  updateSinceUntilBuild.set(false)
  downloadSources.set(true)
  plugins.set(listOf("java", "org.jetbrains.plugins.gradle"))
}

tasks.publishPlugin {
  token.set(System.getenv("PUBLISH_TOKEN"))
  channels.set(listOf(version.toString().split('-').getOrElse(1) { "default" }.split('.').first()))
}
