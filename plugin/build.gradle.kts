dependencies {
  implementation(project(":api"))
  implementation(project(":nms:v1_18_R2"))
  implementation(versionmatchedLibrary)

  compileOnly(placeholderApiLibrary)
  compileOnly(luckPermsLibrary)

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
      include("plugin.yml")
    }
  }
}
