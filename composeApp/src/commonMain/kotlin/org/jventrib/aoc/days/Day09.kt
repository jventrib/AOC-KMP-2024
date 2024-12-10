package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day09 =
    day(9) {
        var blockId = 0
        val diskMap = input.first().mapIndexed { i, c ->
            if (i % 2 == 0) {
                Digit(c.digitToInt(), DigitType.FILE_LENGTH, blockId++)
            } else {
                Digit(c.digitToInt(), DigitType.FREE_SPACE)
            }
        }.toMutableList()

        part1(1928, 6200294120911L) {
            render {
            }
            exec {
//                println(diskMap.diskMapToString())
                while (true) {
                    var fsIndex =
                        diskMap.indexOfFirst { it.type == DigitType.FREE_SPACE && it.length > 0 }
                    val firstFreeSpace = diskMap[fsIndex]
                    val availables = firstFreeSpace.length

                    for (it in 0..<availables) {
                        val indexOfLast = diskMap.indexOfLast { it.type == DigitType.FILE_LENGTH }
                        val last = diskMap[indexOfLast]
                        last.length--
                        if (last.length == 0) {
                            diskMap.removeAt(indexOfLast)
                        }
                        val idx = diskMap.indexOf(firstFreeSpace)
                        val prevFl = diskMap[idx - 1]
                        if (prevFl.blockId == last.blockId) {
                            prevFl.length++
                        } else {
                            fsIndex =
                                diskMap.indexOfFirst { it.type == DigitType.FREE_SPACE && it.length > 0 }
                            diskMap.add(fsIndex++, Digit(1, DigitType.FILE_LENGTH, last.blockId))
                        }

                        firstFreeSpace.length--
                        if (firstFreeSpace.length == 0) {
                            diskMap.removeAt(fsIndex)
                        }
                    }
                    if (diskMap.none { it.type == DigitType.FREE_SPACE && it.length > 0 }) break
                }
                diskMap.checksum()
            }
        }
        part2(2858, 0) {
            render { }
            exec {
                println(diskMap.diskMapToString())
                var done = false
                while (!done) {
                    diskMap.withIndex<Digit>()
                        .filter<IndexedValue<Digit>> { (i, it) -> it.type == DigitType.FILE_LENGTH }
                        .sortedByDescending<IndexedValue<Digit>, Int> { (i, it) -> it.blockId }
                        .forEach { indexedFile ->
                            val fs = diskMap
                                .withIndex()
                                .firstOrNull { it -> it.value.type == DigitType.FREE_SPACE
                                        && it.value.length >= indexedFile.value.length && it.index < indexedFile.index }
                            if (fs != null) {
                                diskMap.add(fs.index, indexedFile.value.copy())
                                fs.value.length -= indexedFile.value.length
                                if (fs.value.length <= 0) {
                                    diskMap.removeAt(fs.index + 1)
                                }
                                indexedFile.value.type = DigitType.FREE_SPACE
                                indexedFile.value.blockId = null
                            } else done = true

//                            println(diskMap.diskMapToString())
                        }
                }
                diskMap.checksum()
            }
        }
    }

private fun MutableList<Digit>.checksum(): Long {
    var resultIdx = 0L
    return fold(0L) { acc, digit ->
            var st = 0L
            repeat(digit.length) {
                st += (digit.blockId ?: 0) * resultIdx++
            }
            acc + st
        }
}

private data class Digit(
    var length: Int,
    var type: DigitType,
    var blockId: Int? = null,
)

enum class DigitType {
    FILE_LENGTH,
    FREE_SPACE
}

private fun List<Digit>.diskMapToString(): String {
    return map<Digit, List<String>> { d ->
        (0..<d.length).map {
            d.blockId?.toString() ?: "."
        }
    }.flatten().joinToString("")
}
