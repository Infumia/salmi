dependencies {
  implementation(project(":api"))
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
