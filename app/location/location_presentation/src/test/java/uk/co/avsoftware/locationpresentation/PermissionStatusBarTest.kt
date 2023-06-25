package uk.co.avsoftware.locationpresentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import uk.co.avsoftware.locationpresentation.components.LocationPermissionStatusBar
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState
import uk.co.avsoftware.location_presentation.R

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
    fun `simple composable test`() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val statusBarNode = composeTestRule.onNodeWithTag("status_row")
        val locationPermissionRequiredString = context.getString(R.string.location_permission_status_coarse_required)

        composeTestRule.setContent {
            LocationPermissionStatusBar(viewState = LocationPermissionViewState.default)
        }

        statusBarNode.apply {
            assertIsDisplayed()
            onChild().apply {
                assertIsDisplayed()
                assertTextEquals(locationPermissionRequiredString)
            }
        }
    }
}