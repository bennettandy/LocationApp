package uk.co.avsoftware.locationapp

import android.R
import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
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
    fun clickingButton_shouldChangeResultsViewText() {
        // Arrange or setup
        composeTestRule.setContent {
            BottomBar()
        }

        composeTestRule.onNodeWithTag("BottomBar").assertIsDisplayed()
    }

    @Test
    fun `when I test, then it works`() {

        ActivityScenario.launch(MainActivity::class.java)
            .use { scenario ->
                scenario.onActivity { activity: MainActivity ->

                    composeTestRule.onNodeWithTag("Floating Button").assertIsDisplayed()

                    activity.recreate()

                    composeTestRule.onNodeWithTag("Floating Button").assertIsDisplayed()

                }
            }

        // An alternative to ActivityScenario is to use AndroidComposeTestRule
        // val composeTestRule = createAndroidComposeRule<MainActivity>()
        // See: https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/AndroidComposeTestRule
    }
}