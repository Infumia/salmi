import org.gradle.api.Project

private val jarModule = setOf(
  "Api",
  "Plugin",
  "NmsV1_18_R2"
).salmi()

private val publishingModule = setOf(
  "Api",
).salmi()

fun Project.isPublishing() = publishingModule.contains(getQualifiedProjectName())

fun Project.isJar() = jarModule.contains(getQualifiedProjectName())

private fun Iterable<String>.salmi() = map { "Salmi$it" }
