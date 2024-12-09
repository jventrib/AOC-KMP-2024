package org.jventrib.aoc.days

import org.jventrib.aoc.day

val day09 =
    day(9) {

        part1(1928, 0) {
            render {
            }
            exec {
                var blockId = 0
                val diskMap = input.first().mapIndexed { i, c ->
                    if (i % 2 == 0) {
                        Digit(c.digitToInt(), DigitType.FILE_LENGTH, blockId++)
                    } else {
                        Digit(c.digitToInt(), DigitType.FREE_SPACE)
                    }
                }.toMutableList()
//                println(diskMap.diskMapToString())
                while (true) {

                    var fsIndex = diskMap.indexOfFirst { it.type == DigitType.FREE_SPACE && it.length > 0 }
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
                            fsIndex = diskMap.indexOfFirst { it.type == DigitType.FREE_SPACE && it.length > 0 }
                            diskMap.add(fsIndex++, Digit(1, DigitType.FILE_LENGTH, last.blockId))
                        }

                        firstFreeSpace.length--
                        if (firstFreeSpace.length == 0) {
                            diskMap.removeAt(fsIndex)
                        }
                    }
                    if (diskMap.none { it.type == DigitType.FREE_SPACE && it.length > 0 }) break
                }
//                val diskMapToString = diskMap.diskMapToString()
//                println(diskMapToString)
//                val sum = diskMapToString.mapIndexed { i, c ->
//                    c.digitToInt().toLong() * i
//                }.sum()
//                sum


                var resultIdx = 0L
                diskMap.filter { it.blockId != null }
                    .fold(0L) { acc, digit ->
                        var st = 0L
                        repeat(digit.length) {
                            st += digit.blockId!! * resultIdx++
                        }
                        acc + st
                    }
            }
        }
        part2(0, 0) {
            render { }
            exec {
                0
            }
        }
    }

private data class Digit(
    var length: Int,
    val type: DigitType,
    val blockId: Int? = null,
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
