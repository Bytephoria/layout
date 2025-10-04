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

---
### ⚙️ Initialization (Required for layout)
If you are using the layout module, you must initialize it before using any inventory layouts.
This step registers the required internal listeners for handling inventory events such as click, open, and close.
Call the following method once during your plugin’s startup (typically inside onEnable()):

```java
package team.bytephoria.layout.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import team.bytephoria.layout.layouts.Layout;

public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize the Layout library (required for the library to work properly)
        Layout.init(this);
    }

}

```
###### 💡 The item module does not require initialization and can be used independently.`

### ✅ Summary
- Add the Bytephoria repository to your build system.
- Include the desired dependencies (`layout` or `item`).
- Call `Layout.init(plugin)` once if using the layout module.