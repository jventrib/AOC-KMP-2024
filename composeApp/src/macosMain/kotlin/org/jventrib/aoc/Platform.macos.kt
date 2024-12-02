package org.jventrib.aoc

class MacOSPlatform : Platform {
  override val name: String = "MacOS"
}


actual fun getPlatform(): Platform {
  return MacOSPlatform()
}
