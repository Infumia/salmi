val signRequired = !rootProject.property("dev").toString().toBoolean()

dependencies {
  compileOnly(lettuceLibrary)
  compileOnly(configurateJacksonLibrary)
  compileOnly(jacksonDatabindLibrary)

  compileOnlyApi(lombokLibrary)
  compileOnlyApi(annotationsLibrary)
  compileOnlyApi(velocityLibrary)

  annotationProcessor(lombokLibrary)
  annotationProcessor(annotationsLibrary)

  testAnnotationProcessor(lombokLibrary)
  testAnnotationProcessor(annotationsLibrary)
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
