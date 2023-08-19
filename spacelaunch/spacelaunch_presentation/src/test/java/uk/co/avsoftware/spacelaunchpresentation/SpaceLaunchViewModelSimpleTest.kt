package uk.co.avsoftware.spacelaunchpresentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.CancelLaunchBookingInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchAction
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewState

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceLaunchViewModelSimpleTest {

    private val bookedTripsRepository: BookedTripsRepository = mockk()

    // mock
    val launchListInteractor: GetLaunchListInteractor = mockk()

    private val loginInteractor: SpaceLaunchLoginInteractor = mockk()

    private val launchDetailsInteractor: GetLaunchDetailsInteractor = mockk()

    private val bookLaunchInteractor: BookLaunchInteractor = mockk()

    private val cancelLaunchBookingInteractor: CancelLaunchBookingInteractor = mockk()

    private val appId: String = "testId"

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when RefreshLaunches received launchListInteractor is invoked`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        coEvery { launchListInteractor.invoke() } returns null

        val viewModel = buildViewModel()

        val expectedState = SpaceLaunchViewState.default

        assertEquals(expectedState, viewModel.uiState.value)

        viewModel.receiveAction(SpaceLaunchAction.RefreshLaunches(null))

        val loadingState = SpaceLaunchViewState.default.copy(isLoading = true)

        assertEquals(loadingState, viewModel.uiState.value)

        coVerify(exactly = 1) { launchListInteractor() }
    }

    @Test
    fun `when RefreshLaunches received launchListInteractor is invoked turbine`() {
        coEvery { launchListInteractor.invoke() } returns null
        coEvery { bookedTripsRepository.bookedTripsFlow() } returns emptyFlow()

        val viewModel = buildViewModel()

        val expectedState = SpaceLaunchViewState.default
        val loadingState = SpaceLaunchViewState.default.copy(isLoading = true)
        val loadedState = SpaceLaunchViewState.default.copy(isLoading = false)

        runBlocking {
            viewModel.uiState.test {
                assertEquals(expectedState, awaitItem())
                viewModel.receiveAction(SpaceLaunchAction.RefreshLaunches(null))
                assertEquals(loadingState, awaitItem())
                assertEquals(loadedState, awaitItem())
            }
        }

        coVerify(exactly = 1) { launchListInteractor() }
    }

    @Test
    fun `when RefreshLaunches received launchListInteractor is invoked returning items`() {
        val launches = Launches(
            cursor = null,
            launches = emptyList(),
            hasMore = false,
        )
        coEvery { launchListInteractor.invoke() } returns launches
        coEvery { bookedTripsRepository.bookedTripsFlow() } coAnswers { emptyFlow() }

        val viewModel = buildViewModel()

        val expectedState = SpaceLaunchViewState.default
        val loadingState = SpaceLaunchViewState.default.copy(isLoading = true)
        val loadedState = SpaceLaunchViewState.default.copy(isLoading = false, launches = launches)

        runBlocking {
            viewModel.uiState.test {
                assertEquals(expectedState, awaitItem())
                viewModel.receiveAction(SpaceLaunchAction.RefreshLaunches(null))
                assertEquals(loadingState, awaitItem())
                assertEquals(loadedState, awaitItem())
                awaitComplete()
            }
        }

        coVerify(exactly = 1) { launchListInteractor() }
        coVerify(exactly = 1) { bookedTripsRepository.bookedTripsFlow() }
    }

    private fun buildViewModel(): SpaceLaunchViewModel = SpaceLaunchViewModel(
        bookedTripsRepository = bookedTripsRepository,
        launchListInteractor = launchListInteractor,
        loginInteractor = loginInteractor,
        launchDetailsInteractor = launchDetailsInteractor,
        bookLaunchInteractor = bookLaunchInteractor,
        cancelLaunchBookingInteractor = cancelLaunchBookingInteractor,
        savedStateHandle = SavedStateHandle(),
        applicationId = appId,
    )
}
