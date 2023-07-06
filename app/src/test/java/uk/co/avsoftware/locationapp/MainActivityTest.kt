package uk.co.avsoftware.locationapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import uk.co.avsoftware.locationapp.screens.BottomBar

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @Test
    fun `simple composable test`() {
        // Arrange or setup
        composeTestRule.setContent {
            BottomBar()
        }

        composeTestRule.onNodeWithTag("BottomBar").assertIsDisplayed()
    }

    @Test
    fun `activity composable test`() {
        ActivityScenario.launch(MainActivity::class.java)
            .use { scenario ->
                scenario.onActivity { activity: MainActivity ->

                    composeTestRule.onNodeWithTag("Floating Button").assertIsDisplayed()

                    activity.recreate()

                    composeTestRule.onNodeWithTag("Floating Button").assertIsDisplayed()
                }
            }
    }
}
