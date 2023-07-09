package uk.co.avsoftware.spacelaunchpresentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import uk.co.avsoftware.core.annotation.ApplicationId
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.CancelLaunchBookingInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel
import javax.inject.Inject

@HiltAndroidTest
@Config(sdk = [33], application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class SpaceLaunchViewModelTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var bookedTripsRepository: BookedTripsRepository

    @Inject
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
    @Throws(Exception::class)
    fun setUp() {
        // inject Hilt objects from Singleton Component
        hiltAndroidRule.inject()

        viewModel = SpaceLaunchViewModel(
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

    @Test
    fun simpleTest() {
        assertNotNull(viewModel)
    }
}
