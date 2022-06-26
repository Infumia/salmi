val signRequired = !rootProject.property("dev").toString().toBoolean()

dependencies {
  compileOnly(libs.lettuce)
  compileOnly(libs.configurateJackson)
  compileOnly(libs.jacksonDatabind)
  compileOnly(libs.caffeine)

  compileOnlyApi(libs.paperApi)
  compileOnlyApi(libs.lombok)
  compileOnlyApi(libs.annotations)

  annotationProcessor(libs.lombok)
  annotationProcessor(libs.annotations)

  testAnnotationProcessor(libs.lombok)
  testAnnotationProcessor(libs.annotations)
}

tasks {
  javadoc {
    options.encoding = Charsets.UTF_8.name()
    (options as StandardJavadocDocletOptions).tags("todo")
  }

  val javadocJar by creating(Jar::class) {
    dependsOn("javadoc")
    val name = project.extra["qualifiedProjectName"].toString()
    val version = project.version.toString()
    archiveClassifier.set("javadoc")
    archiveClassifier.convention("javadoc")
    archiveBaseName.set(name)
    archiveBaseName.convention(name)
    archiveVersion.set(version)
    archiveVersion.convention(version)
    from(javadoc)
  }

  val sourcesJar by creating(Jar::class) {
    dependsOn("classes")
    val name = project.extra["qualifiedProjectName"].toString()
    val version = project.version.toString()
    archiveClassifier.set("sources")
    archiveClassifier.convention("sources")
    archiveBaseName.set(name)
    archiveBaseName.convention(name)
    archiveVersion.set(version)
    archiveVersion.convention(version)
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
      artifactId = extra["qualifiedProjectName"].toString()
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
