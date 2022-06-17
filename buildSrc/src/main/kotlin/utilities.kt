import org.gradle.api.Project
import org.gradle.api.tasks.bundling.AbstractArchiveTask

private const val PREFIX = "Salmi"

fun Project.getQualifiedProjectName(): String {
  if (parent == null) {
    return PREFIX
  }
  val parentName = parent!!.getQualifiedProjectName()
  return parentName + name[0].toUpperCase() + name.substring(1)
}

fun AbstractArchiveTask.define(
  name: String = project.getQualifiedProjectName(),
  classifier: String? = null,
  version: String? = null
) {
  archiveClassifier.set(classifier)
  archiveClassifier.convention(classifier)
  archiveBaseName.set(name)
  archiveBaseName.convention(name)
  archiveVersion.set(version)
  archiveVersion.convention(version)
}
