package org.jventrib.aoc

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jakewharton.mosaic.runMosaic
import com.jakewharton.mosaic.ui.Text
import kotlinx.coroutines.delay

suspend fun main() = runMosaic {
  var count by remember { mutableIntStateOf(0) }

  Text("The count is: $count")

  LaunchedEffect(Unit) {
    for (i in 1..20) {
      delay(250)
      count = i
    }
  }
}
