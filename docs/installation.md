# Installation

This guide explains how to add Bytephoria libraries to your project.

---

## 🔗 Repository
Add the Bytephoria repository to your build system:

### Gradle (Kotlin DSL)
```kotlin
repositories {
    maven {
        name = "bytephoriaRepository"
        url = uri("https://repo.bytephoria.team/releases")
    }
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>bytephoria-repository</id>
        <name>Bytephoria</name>
        <url>https://repo.bytephoria.team/releases</url>
    </repository>
</repositories>
```

---

## 📦 Dependencies
### Gradle (Kotlin DSL)
```kotlin
// Layout module (includes 'item' automatically)
implementation("team.bytephoria.layout:layout:1.0.0")

// Item module (standalone, does not include 'layout')
implementation("team.bytephoria.layout:item:1.0.0")
```

### Maven
```xml
<!-- Layout module (includes 'item') -->
<dependency>
    <groupId>team.bytephoria.layout</groupId>
    <artifactId>layout</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Item module (standalone) -->
<dependency>
    <groupId>team.bytephoria.layout</groupId>
    <artifactId>item</artifactId>
    <version>1.0.0</version>
</dependency>

```