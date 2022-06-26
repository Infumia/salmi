import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

apply<ShadowPlugin>()

dependencies {
  implementation(project(":api"))
  implementation(project(":nms:v1_18_R2", "reobf"))

  implementation(libs.versionmatched)

  compileOnly(libs.placeholderApi)
  compileOnly(libs.luckPerms)

  implementation(libs.lettuce)
  implementation(libs.configurateJackson)
  implementation(libs.jacksonDatabind)
  implementation(libs.caffeine)
}

tasks {
  processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
      expand("pluginVersion" to project.version)
      include("plugin.yml")
    }
  }

  withType<ShadowJar> {
    val name = project.extra["qualifiedProjectName"].toString()
    val version = project.version.toString()
    archiveClassifier.set(null as String?)
    archiveClassifier.convention(null as String?)
    archiveBaseName.set(name)
    archiveBaseName.convention(name)
    archiveVersion.set(version)
    archiveVersion.convention(version)
    rootProject.findProperty("paperPluginsFolder")?.let {
      val path = it.toString()
      if (path.isNotEmpty() && path.isNotBlank()) {
        destinationDirectory.set(File(path))
      }
    }
  }

  build {
    dependsOn("shadowJar")
  }
}
