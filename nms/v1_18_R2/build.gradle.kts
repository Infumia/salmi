plugins {
  id("io.papermc.paperweight.userdev") version "1.3.7"
}

dependencies {
  paperDevBundle("1.18.2-R0.1-SNAPSHOT")
}

tasks {
  reobfJar {
    val output = project.layout.buildDirectory.file("libs/${getQualifiedProjectName()}.jar")
    outputJar.set(output)
    outputJar.convention(output)
  }
}
