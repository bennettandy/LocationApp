package uk.co.avsoftware.locationapp.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelMainDispatcherTest {

    @Test
    fun brokenTestLoadMessage() {
        // we don't have access to Main Dispatcher in Unit Test so anything
        // using viewModelScope within the viewModel will fail
        val viewModel = MyViewModel()

        // "Module with the Main dispatcher had failed to initialize"
        try {
            viewModel.loadMessage()
            Assert.fail("Should have thrown RuntimeException")
        } catch (e: RuntimeException) {
            assertThat(e.message).contains("Module with the Main dispatcher had failed to initialize")
            println("RuntimeException")
        }
    }

    @Test
    fun testLoadMessage() = runTest {
        // we don't have access to Main Dispatcher in Unit Test so anything
        // using viewModelScope within the viewModel will fail
        val viewModel = MyViewModel()

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // "Module with the Main dispatcher had failed to initialize"
        viewModel.loadMessage()

        assertEquals(viewModel.message.value, "Hello World!")
    }

    @After
    fun clearDown() {
        Dispatchers.resetMain()
    }
}

class MyViewModel : ViewModel() {
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    fun loadMessage() {
        viewModelScope.launch {
            _message.value = "Hello World!"
        }
    }
}
