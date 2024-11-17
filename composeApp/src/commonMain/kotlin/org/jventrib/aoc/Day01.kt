package org.jventrib.aoc

import androidx.compose.foundation.Canvas
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

private data class PartState(var count: MutableIntState)

@Composable
fun Day01() = DayPart<PartState>(
    state = {
        rememberPartState()
    },
    render = {
        val count by count
        val size = count * 10f
        Canvas(Modifier) {
            drawRect(color = Color.Red, size = Size(size, size))
        }
        Text("count is $this")
    },
    stepper = {
        var count by count
        (1..800).forEach {
            delay(10)
            count = it
        }
        count
    }
)

@Composable
private fun <T> DayPart(
    state: @Composable () -> State<T>,
    render: @Composable T.() -> Unit,
    stepper: suspend T.() -> Any
): Any {
    val partState by state()
    partState.render()
    lateinit var result: Any
    LaunchedEffect(Unit) {
        result = partState.stepper()
    }
    return result
}

@Composable
private fun rememberPartState(): State<PartState> = remember {
    mutableStateOf(PartState(mutableIntStateOf(0)))
}

interface IPartState