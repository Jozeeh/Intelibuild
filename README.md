# Intelibuild

> A utility mod for builders and developers.

![Minecraft](https://img.shields.io/badge/Minecraft-26.1%20%7C%2026.2-blue?style=flat-square)
![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square)
![Fabric API](https://img.shields.io/badge/Fabric%20API-0.161.0%2B-orange?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## Supported versions

| Minecraft | Intelibuild jar | Fabric API |
|---|---|---|
| 26.2 | `intelibuild-1.2+26.2.jar` | `0.161.0+26.2` |
| 26.1 (includes 26.1.1 and 26.1.2) | `intelibuild-1.2+26.1.jar` | `0.145.1+26.1` |

There is **no separate download for each patch release**: the 26.1 jar declares the `~26.1`
compatibility range, so it also runs on 26.1.1 and 26.1.2.

---

## Features

### Copy Block States

Hold `Left Ctrl` + middle-click on a block in **Creative mode** to copy it with all its state properties (facing, waterlogged, axis, half, etc.).

Properties are displayed as colored lore on the copied item:

| Color | Meaning |
|---|---|
| Light gray | Property name |
| Aqua | `true` value |
| Light red | `false` value |
| Yellow | Non-boolean value |

### Block ID Panel

When you open chat (`T`), a panel appears at the top-right corner with two tabs:

| Tab | Content |
|---|---|
| **Hotbar** | Icon + full item ID (e.g. `minecraft:stone`) in yellow text |
| **Inventory** | 4×9 grid showing every item in your inventory |

Click any item in the panel to insert its full namespaced ID into the chat input.

- **F6** — Toggles panel visibility.
- The key is rebindable from Minecraft controls → **Intelibuild** category.

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) (0.19.5 or newer) for Minecraft 26.1 or 26.2.
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) matching your Minecraft version.
3. Download the matching Intelibuild release from [Releases](https://github.com/Jozeeh/Intelibuild/releases).
4. Place both `.jar` files in your Minecraft instance's `mods/` folder.

---

## Development — Building from Source

The project uses [Stonecutter](https://stonecutter.kikugie.dev) so a single source tree builds
every supported Minecraft version. Each version is a Gradle subproject named after it
(`26.1`, `26.2`).

### Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 25 |
| Gradle | 9.8.0 (wrapper included) |

### Build

```bash
git clone https://github.com/Jozeeh/Intelibuild.git
cd Intelibuild
./gradlew build            # every supported version
./gradlew :26.2:build      # a single version
./gradlew buildAndCollect  # every version, jars collected in build/libs/1.2/
```

Per-version properties (Minecraft compatibility range, Fabric API version, …) live in
[`stonecutter.properties.toml`](stonecutter.properties.toml).

### Useful Tasks

```bash
./gradlew :26.2:runClient            # Launch a dev instance on 26.2
./gradlew stonecutterSet -Pstonecutter.version=26.1   # Switch the active version
./gradlew clean                      # Clean previous builds
```

---

## Dependencies

| Dependency | Required Version | Purpose |
|---|---|---|
| Minecraft | 26.1 or 26.2 | Game base |
| Fabric Loader | >= 0.19.5 | Mod loader |
| Fabric API | 0.145.1+ (26.1) / 0.161.0+ (26.2) | Required APIs |
| Java | 25 | Runtime environment |

---

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.