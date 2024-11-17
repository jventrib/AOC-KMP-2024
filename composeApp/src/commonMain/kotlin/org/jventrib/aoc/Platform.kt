package org.jventrib.aoc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform