package org.jventrib.aoc.days

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import org.jventrib.aoc.Day
import org.jventrib.aoc.day

val day03 =
    day(3) {
      val result = mutableLongStateOf(0)
      val matches = mutableStateListOf<IntRange>()
      val exclusions = mutableStateListOf<MatchResult>()
      part1(161, 170778545) {
        render { render(result, matches) }
        exec {
          findMatches(result, matches, exclusions)
        }
      }
      part2(48, 82868252) {
        render { render(result, matches, exclusions) }
        exec {
          findMatchesWithExclusions(result, matches, exclusions)
        }
      }
    }

private suspend fun Day<Long>.findMatchesWithExclusions(
  result: MutableLongState,
  matches: SnapshotStateList<IntRange>,
  exclusions: SnapshotStateList<MatchResult>
): Long {
  """don't\(\).*?do\(\)"""
      .toRegex()
      .findAll(input.joinToString(""))
      .asFlow()
      .onEach { delay(30) }
      .toCollection(exclusions)
  findMatches(result, matches, exclusions)
  return result.value
}

private suspend fun Day<Long>.findMatches(
  result: MutableLongState,
  matches: SnapshotStateList<IntRange>,
  exclusions: SnapshotStateList<MatchResult>
): Long {
  """mul\((\d+),(\d+)\)"""
      .toRegex()
      .findAll(input.joinToString(""))
      .asFlow()
      .filter { m -> exclusions.none { e -> e.range.contains(m.range) } }
      .map { match ->
        delay(3)
        result.value += match.groupValues.let { it[1].toInt() * it[2].toInt() }
        match.range
      }
      .toCollection(matches)
      return result.value
}

@Composable
private fun Day<Long>.render(
  result: MutableLongState,
  matches: SnapshotStateList<IntRange>,
  exclusions: SnapshotStateList<MatchResult>? = null
) {
  Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    Button(
        onClick = {
          scope.coroutineContext.cancelChildren()
          result.value = 0
          matches.clear()
          exclusions?.clear()
          scope.launch {
            findMatchesWithExclusions(result, matches, exclusions!!)
          }
        },
    ) {
      Text("Refresh")
    }

    Box(Modifier) {
      Box(Modifier.padding(20.dp)) {
        val stateVertical = rememberScrollState()
        Box(Modifier.verticalScroll(stateVertical)) {
          Text(
              buildAnnotatedString {
                append(input.joinToString("") { it })
                matches.forEach { r ->
                  addStyle(SpanStyle(background = Color.Green), r.first, r.last + 1)
                }
                exclusions?.forEach { e ->
                  addStyle(
                      SpanStyle(color = Color.LightGray),
                      e.range.first,
                      e.range.last + 1,
                  )
                }
                toAnnotatedString()
              },
              fontSize = 14.sp,
          )
          //        VerticalScrollbar(
          //            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
          //            adapter = rememberScrollbarAdapter(stateVertical),
          //        )
        }
      }
      Text(
          "Result ${result.value}",
          modifier = Modifier.align(Alignment.TopEnd).padding(end = 15.dp),
      )
    }
  }
}

fun IntRange.contains(other: IntRange) = other.first in this && other.last in this
