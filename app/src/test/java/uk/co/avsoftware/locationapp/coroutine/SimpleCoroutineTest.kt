package uk.co.avsoftware.locationapp.coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SimpleCoroutineTest {

    private suspend fun fetchData(): String {
        println("Starting Data Load")
        delay(1000L) // ignored by test scope, test dispatcher, test scheduler
        println("Completed Data Load")
        return "Hello world"
    }

    @Test
    fun testUsingStandardTestDispatcher() = runTest {
        // this: TestScope
        // Standard Test Dispatcher queues coroutines
        repeat(3) {
            launch {
                fetchData()
            }
        }
        println("Completed")
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testUsingUnconfinedTestDispatcher() = runTest(UnconfinedTestDispatcher()) {
        // eagerly starts coroutines
        repeat(3) {
            launch {
                fetchData()
            }
        }
        println("Completed")
    }
}
