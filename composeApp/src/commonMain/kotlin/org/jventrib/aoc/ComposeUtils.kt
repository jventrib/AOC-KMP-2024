package org.jventrib.aoc

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <E> PartBlock<E>.AOCPartWindows(result: State<E>, block: @Composable () -> Unit) {
  Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    Button(
        onClick = {},
    ) {
      Text("Refresh")
    }

    Box(Modifier) {
      Box(Modifier.padding(20.dp)) {
        val scrollState = rememberScrollState()
        Box(Modifier.fillMaxSize().verticalScroll(scrollState).horizontalScroll(scrollState).border(1.dp, Color.Gray)) {
          block()
        }
      }
      Text(
          "Result ${result.value}",
          modifier = Modifier.align(Alignment.TopEnd).padding(end = 15.dp),
      )
    }
  }
}
