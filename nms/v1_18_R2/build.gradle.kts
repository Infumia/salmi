plugins {
  id("io.papermc.paperweight.userdev") version "1.3.8"
}

dependencies {
  paperDevBundle("1.18.2-R0.1-SNAPSHOT")
}

tasks {
  reobfJar {
    outputJar.set(layout.buildDirectory.file("libs/${project.extra["qualifiedProjectName"]}.jar"))
  }

  build {
    dependsOn(reobfJar)
  }
}
