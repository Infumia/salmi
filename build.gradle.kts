plugins {
  java
  `java-library`
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

allprojects {
  group = "tr.com.infumia"
}

subprojects {
  apply<JavaPlugin>()
  apply<JavaLibraryPlugin>()
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
    mavenLocal()
  }

  if (isPlugin()) {
    dependencies {
      implementation(project(":api"))

      implementation(lettuceLibrary)
      implementation(configurateJacksonLibrary)
      implementation(jacksonDatabindLibrary)
      implementation(caffeineLibrary)
    }

    tasks {
      processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
          expand("pluginVersion" to project.version)
          include("velocity-plugin.json")
          include("plugin.yml")
        }
      }
    }
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
