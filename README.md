# Accessories Compatibility Layer (Unofficial 1.20.1 Fabric Port)

An unofficial Fabric port of Wisp Forest's Accessories Compatibility Layer for Minecraft 1.20.1. It adapts the Trinkets API to Accessories so mods using either API can share accessory slots and behavior.

This port targets **Minecraft 1.20.1 and Fabric only**. It does not include the upstream NeoForge or Curios integrations.

## Requirements

- Minecraft 1.20.1
- Fabric Loader 0.16.10 or newer
- Fabric API
- Accessories 1.0.0-beta.49+1.20.1 or newer
- Trinkets 3.7.2 or newer
- oωo (owo-lib) 0.11.2+1.20 or newer

## Installation

Install the required mods above on the client and server, then place this port's JAR in the `mods` folder. This compatibility layer does not replace Accessories or Trinkets.

## Port scope and testing

The port updates the shared Accessories hooks and Trinkets wrappers to the 1.20.1 APIs. Slot compatibility data is synchronized through Fabric's 1.20.1 networking API when the server sends datapack contents to a player. The build passes and the development client completed its initial resource load with Accessories, Trinkets, Fabric API, oωo, Cloth Config, and Cardinal Components loaded together. The in-world slot-sync behavior still needs testing in a real multiplayer or integrated-world session.

This is an unofficial community port and is not affiliated with Wisp Forest, the Accessories project, or the Trinkets project. If a feature depends on an API that does not exist in the 1.20.1 dependency versions, it is not included here.

## Credits and license

Original project: [Accessories Compatibility Layer](https://github.com/wisp-forest/accessories-compat-layer), by Wisp Forest / blodhgarm. This port is maintained by JustHari01. The upstream MIT license and copyright notice are retained in `LICENSE`.

AI disclosure: AI assistance was used for the 1.20.1 compatibility port and debugging; the build and development-client startup were checked locally.
