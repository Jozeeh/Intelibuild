# Intelibuild

> A utility mod for builders and developers.

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-blue?style=flat-square)
![Fabric API](https://img.shields.io/badge/Fabric%20API-0.141.4%2B-orange?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

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

1. Install [Fabric Loader](https://fabricmc.net/) for Minecraft 1.21.11.
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) (v0.141.4+ for 1.21.11).
3. Download the latest Intelibuild release from [Releases](https://github.com/Jozeeh/Intelibuild/releases).
4. Place both `.jar` files in your Minecraft instance's `mods/` folder.

---

## Development — Building from Source

### Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 21 |
| Gradle | 9.5.0+ (wrapper included) |

### Build

```bash
git clone https://github.com/Jozeeh/Intelibuild.git
cd Intelibuild
./gradlew build
```

The compiled `.jar` will be at `build/libs/intelibuild-*.jar`.

### Useful Tasks

```bash
./gradlew build          # Build the mod
./gradlew runClient      # Launch a development instance
./gradlew clean          # Clean previous builds
```

---

## Dependencies

| Dependency | Required Version | Purpose |
|---|---|---|
| Minecraft | 1.21.11 | Game base |
| Fabric Loader | >= 0.19.3 | Mod loader |
| Fabric API | 0.141.4+ | Required APIs |
| Java | 21 | Runtime environment |

---

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---
