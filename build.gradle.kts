plugins {
  java
  `java-library`
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

val signRequired = !rootProject.property("dev").toString().toBoolean()

allprojects {
  group = "tr.com.infumia"
}

subprojects {
  apply<JavaPlugin>()
  apply<JavaLibraryPlugin>()

  if (isJar()) {
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
      mavenLocal()
    }

    dependencies {
      compileOnly(lombokLibrary)
      compileOnly(annotationsLibrary)
      compileOnly(velocityLibrary)

      annotationProcessor(lombokLibrary)
      annotationProcessor(annotationsLibrary)

      testImplementation(lombokLibrary)
      testImplementation(annotationsLibrary)

      testAnnotationProcessor(lombokLibrary)
      testAnnotationProcessor(annotationsLibrary)
    }
  }

  if (isPlugin()) {
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

  if (isPublishing()) {
    apply {
      plugin("maven-publish")
      plugin("signing")
    }

    tasks {
      javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).tags("todo")
      }

      val javadocJar by creating(Jar::class) {
        dependsOn("javadoc")
        define(classifier = "javadoc")
        from(javadoc)
      }

      val sourcesJar by creating(Jar::class) {
        dependsOn("classes")
        define(classifier = "sources")
        from(sourceSets["main"].allSource)
      }

      build {
        dependsOn(sourcesJar)
        dependsOn(javadocJar)
      }
    }

    publishing {
      publications {
        val publication = create<MavenPublication>("mavenJava") {
          groupId = project.group.toString()
          artifactId = getQualifiedProjectName()
          version = project.version.toString()

          from(components["java"])
          artifact(tasks["sourcesJar"])
          artifact(tasks["javadocJar"])
          pom {
            name.set("Salmi")
            description.set("Fully Customizable Tab plugin for Velocity servers.")
            url.set("https://infumia.com.tr/")
            licenses {
              license {
                name.set("MIT License")
                url.set("https://mit-license.org/license.txt")
              }
            }
            developers {
              developer {
                id.set("portlek")
                name.set("Hasan Demirtaş")
                email.set("utsukushihito@outlook.com")
              }
            }
            scm {
              connection.set("scm:git:git://github.com/infumia/salmi.git")
              developerConnection.set("scm:git:ssh://github.com/infumia/salmi.git")
              url.set("https://github.com/infumia/salmi")
            }
          }
        }

        signing {
          isRequired = signRequired
          if (isRequired) {
            useGpgCmd()
            sign(publication)
          }
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
