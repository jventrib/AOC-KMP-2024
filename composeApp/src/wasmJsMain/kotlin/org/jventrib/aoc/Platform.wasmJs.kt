package org.jventrib.aoc

class WasmPlatform : Platform {
  override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
