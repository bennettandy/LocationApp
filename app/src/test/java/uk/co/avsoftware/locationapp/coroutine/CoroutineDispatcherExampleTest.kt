package uk.co.avsoftware.locationapp.coroutine

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class CoroutineDispatcherExampleTest {

    @Test
    fun testUsingStandardTestDispatcher() = runTest {
        val repository = NameRepository(
            database = NameDatabase(),
            // use the same coroutine dispatcher using same testScheduler
            dispatcher = StandardTestDispatcher(testScheduler),
        )

        repository.initialise()
        // advance time until coroutines have completed, database is initialised
        testScheduler.advanceUntilIdle()

        launch {
            repository.insertName("John")
        }
        launch {
            repository.insertName("Smith")
        }
        launch {
            val names = repository.readNames()
            assertThat(names).hasSize(2)
            repository.readNames().forEach {
                println("Read name $it")
            }
        }
    }
}

class NameRepository(
    private val database: NameDatabase = NameDatabase(),
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) {
    private val scope = CoroutineScope(dispatcher)
    suspend fun initialise() {
        scope.launch {
            // init is slow,
            // it needs to complete before any insert or read is called
            database.init()
        }
    }

    suspend fun insertName(name: String) = withContext(dispatcher) {
        // fail if uninitialised
        database.insertName(name)
    }

    suspend fun readNames(): List<String> = withContext(dispatcher) {
        // fail if uninitialised
        database.readNames()
    }
}

class NameDatabase {
    private var isInitialised = false
    private val list: MutableList<String> = mutableListOf()

    suspend fun init() {
        delay(2000L)
        isInitialised = true
        println("Database initialised")
    }

    suspend fun insertName(name: String) {
        if (!isInitialised) throw Exception("Not initialised")
        delay(200L)
        list.add(name)
    }

    suspend fun readNames(): List<String> {
        if (!isInitialised) throw Exception("Not initialised")
        delay(200)
        return list.toList()
    }
}
