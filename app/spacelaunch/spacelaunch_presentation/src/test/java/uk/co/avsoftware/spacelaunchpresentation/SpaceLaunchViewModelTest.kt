package uk.co.avsoftware.spacelaunchpresentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import uk.co.avsoftware.commontest.coroutines.UnitTestDispatcherProvider
import uk.co.avsoftware.common.annotation.ApplicationId
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
import javax.inject.Inject

@HiltAndroidTest
@Config(sdk = [33], application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SpaceLaunchViewModelTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var bookedTripsRepository: BookedTripsRepository

    // mock
    lateinit var launchListInteractor: GetLaunchListInteractor

    @Inject
    lateinit var loginInteractor: SpaceLaunchLoginInteractor

    @Inject
    lateinit var launchDetailsInteractor: GetLaunchDetailsInteractor

    @Inject
    lateinit var bookLaunchInteractor: BookLaunchInteractor

    @Inject
    lateinit var cancelLaunchBookingInteractor: CancelLaunchBookingInteractor

    @Inject
    @ApplicationId
    lateinit var appId: String

    lateinit var viewModel: SpaceLaunchViewModel

    @Before
    fun setUp() {
        // inject Hilt objects from Singleton Component
        hiltAndroidRule.inject()

        launchListInteractor = mockk()

        viewModel = SpaceLaunchViewModel(
            bookedTripsRepository = bookedTripsRepository,
            launchListInteractor = launchListInteractor,
            loginInteractor = loginInteractor,
            launchDetailsInteractor = launchDetailsInteractor,
            bookLaunchInteractor = bookLaunchInteractor,
            cancelLaunchBookingInteractor = cancelLaunchBookingInteractor,
            savedStateHandle = SavedStateHandle(),
            applicationId = appId,
            dispatcherProvider = UnitTestDispatcherProvider(),
        )
    }

//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }

    @Test
    fun `when RefreshLaunches received launchListInteractor is invoked turbine`() {
        coEvery { launchListInteractor.invoke() } returns null

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
        val launches: Launches = mockk()
        coEvery { launchListInteractor.invoke() } returns launches

        val expectedState = SpaceLaunchViewState.default
        val loadingState = SpaceLaunchViewState.default.copy(isLoading = true)
        val loadedState = SpaceLaunchViewState.default.copy(isLoading = false, launches = launches)

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
}
