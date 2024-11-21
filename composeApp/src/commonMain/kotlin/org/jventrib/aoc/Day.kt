package org.jventrib.aoc

import androidx.compose.runtime.Composable

interface Day<T> {

    @Composable
    fun render()

    suspend fun step(): T
}
