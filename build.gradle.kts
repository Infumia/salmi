import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.diffplug.spotless.LineEnding

plugins {
  java
  `java-library`
  `maven-publish`
  signing
  id("com.diffplug.spotless") version "6.7.2"
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

allprojects {
  group = "tr.com.infumia"
}

subprojects {
  apply<JavaPlugin>()
  apply<JavaLibraryPlugin>()
  apply<SpotlessPlugin>()

  if (isPublishing()) {
    apply<MavenPublishPlugin>()
    apply<SigningPlugin>()
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  tasks {
    compileJava {
      options.encoding = Charsets.UTF_8.name()
    }

    jar {
      define()
    }

    build {
      dependsOn(jar)
    }
  }

  repositories {
    mavenCentral()
    maven(SNAPSHOTS)
    maven(PAPERMC)
    maven(SPONGEPOWERED)
    maven(PAPI)
    maven(CODEMC_NMS)
    mavenLocal()
  }

  extensions.configure<SpotlessExtension> {
    lineEndings = LineEnding.UNIX
    isEnforceCheck = false

    java {
      targetExclude("**/proto/**")
      importOrder()
      removeUnusedImports()
      endWithNewline()
      indentWithSpaces(2)
      trimTrailingWhitespace()
      prettier(
        mapOf(
          "prettier" to "2.7.1",
          "prettier-plugin-java" to "1.6.2"
        )
      ).config(
        mapOf(
          "parser" to "java",
          "tabWidth" to 2,
          "useTabs" to false
        )
      )
    }
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
