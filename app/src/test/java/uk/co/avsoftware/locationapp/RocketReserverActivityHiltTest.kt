package uk.co.avsoftware.locationapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class RocketReserverActivityHiltTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // inject Hilt objects from Singleton Component
        hiltAndroidRule.inject()

        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @Test
    fun `activity composable test`() {
        ActivityScenario.launch(RocketReserverActivity::class.java)
            .use { scenario ->
                scenario.moveToState(Lifecycle.State.STARTED)
                scenario.onActivity { activity: RocketReserverActivity ->
                    composeTestRule.onNodeWithText("Location App").assertIsDisplayed()
                }
            }
    }
}
