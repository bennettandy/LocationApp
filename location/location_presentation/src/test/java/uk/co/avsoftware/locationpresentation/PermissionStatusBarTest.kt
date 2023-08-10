package uk.co.avsoftware.locationpresentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import uk.co.avsoftware.locationpresentation.components.LocationPermissionStatusBar
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@RunWith(RobolectricTestRunner::class)
class PermissionStatusBarTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @Test
    fun `no permissions and no gps show course required`() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            testMappingToMessage(
                viewState = LocationPermissionViewState.default.copy(
                    fineLocationGranted = false,
                    coarseLocationGranted = false,
                    gpsIsActive = false
                ),
                expectedMessage = getString(R.string.location_permission_status_coarse_required)
            )
        }
    }

    @Test
    fun `coarse permission and gps show fine required`() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            testMappingToMessage(
                viewState = LocationPermissionViewState.default.copy(
                    fineLocationGranted = false,
                    coarseLocationGranted = true,
                    gpsIsActive = true
                ),
                expectedMessage = getString(R.string.location_permission_status_fine_required)
            )
        }
    }

    @Test
    fun `coarse permission and no gps show fine required`() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            testMappingToMessage(
                viewState = LocationPermissionViewState.default.copy(
                    fineLocationGranted = false,
                    coarseLocationGranted = true,
                    gpsIsActive = false
                ),
                expectedMessage = getString(R.string.location_permission_status_fine_required)
            )
        }
    }

    @Test
    fun `fine permission and no gps show gps required`() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            testMappingToMessage(
                viewState = LocationPermissionViewState.default.copy(
                    fineLocationGranted = true,
                    coarseLocationGranted = true,
                    gpsIsActive = false
                ),
                expectedMessage = getString(R.string.location_permission_status_gps_required)
            )
        }
    }

    @Test
    @Ignore("need to fixed unresolved activity")
    fun `fine permission and gps show success`() {
        InstrumentationRegistry.getInstrumentation().targetContext.apply {
            testMappingToMessage(
                viewState = LocationPermissionViewState.default.copy(
                    fineLocationGranted = true,
                    coarseLocationGranted = true,
                    gpsIsActive = true
                ),
                expectedMessage = getString(R.string.location_permission_status_success)
            )
        }
    }

    private fun testMappingToMessage(
        viewState: LocationPermissionViewState,
        expectedMessage: String
    ) {
        composeTestRule.apply {
            setContent { LocationPermissionStatusBar(viewState = viewState) }
            onNodeWithTag("status_row")
                .apply {
                    assertIsDisplayed()
                    onChild().apply {
                        assertIsDisplayed()
                        assertTextEquals(expectedMessage)
                    }
                }
        }
    }
}
