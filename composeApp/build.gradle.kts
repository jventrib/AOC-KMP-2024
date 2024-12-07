@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.kotest.multiplatform)
  alias(libs.plugins.ktfmt)
}

kotlin {

  compilerOptions {
    freeCompilerArgs.add("-Xmulti-dollar-interpolation")
  }

  jvm()

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    moduleName = "composeApp"
    browser {
      val rootDirPath = project.rootDir.path
      val projectDirPath = project.projectDir.path
      commonWebpackConfig {
        outputFileName = "composeApp.js"
        devServer =
            (devServer ?: KotlinWebpackConfig.DevServer()).apply {
              static =
                  (static ?: mutableListOf()).apply {
                    // Serve sources to debug inside browser
                    add(rootDirPath)
                    add(projectDirPath)
                  }
            }
      }
    }
    binaries.executable()
  }

  sourceSets {
    val jvmMain by getting

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)
    }
    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.swing)
      implementation(libs.mosaic)
    }

    val commonTest by getting {
      dependencies {
        implementation(libs.kotest.assertions.core)
        implementation(libs.kotest.framework.engine)
        implementation(libs.kotest.framework.datatest)
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation(libs.kotest.runner.junit5)
        implementation(libs.kotlin.reflect)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "org.jventrib.aoc.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "org.jventrib.aoc"
      packageVersion = "1.0.0"
    }
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
  filter { isFailOnNoMatchingTests = false }
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events =
        setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  compilerOptions.freeCompilerArgs.add("-Xdebug")
}

//tasks.build {
//  dependsOn("ktfmtFormat")
//}
