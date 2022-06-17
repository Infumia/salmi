# salmi
[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/Infumia/salmi/workflows/build/badge.svg)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/tr.com.infumia/SalmiApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/tr.com.infumia/SalmiApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)
## How to Use (Developers)
### velocity-plugin.json (Velocity)
```json
{
  "dependencies": [
    {
      "id": "salmi",
      "optional": false
    }
  ]
}
```
### Maven
```xml
<dependencies>
  <dependency>
    <groupId>tr.com.infumia</groupId>
    <artifactId>SalmiApi</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```
### Gradle
```groovy
plugins {
  id "java"
}

dependencies {
  compileOnly "tr.com.infumia:SalmiApi:VERSION"
}
```
## How to Use (Server Owners)
Download the latest Jar files here https://github.com/Infumia/salmi/releases/latest

Put the Jar file into your mods/plugins directory.
