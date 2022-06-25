import org.gradle.api.Project

private val jarModule = setOf(
  "Api",
  "Paper",
).salmi()

private val publishingModule = setOf(
  "Api",
).salmi()

private val pluginModule = setOf(
  "Paper",
).salmi()

fun Project.isPublishing() = publishingModule.contains(getQualifiedProjectName())

fun Project.isPlugin() = pluginModule.contains(getQualifiedProjectName())

fun Project.isJar() = jarModule.contains(getQualifiedProjectName())

private fun Iterable<String>.salmi() = map { "Salmi$it" }
