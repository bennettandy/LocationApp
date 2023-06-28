package uk.co.avsoftware.locationapp

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("uk.co.avsoftware.locationapp", appContext.packageName)

        val scenario = launchActivity<MainActivity>()
        scenario.onActivity {
                activity ->
            // access to activity here
            assertNotNull(activity)
        }
        // destroy and recreate activity, we shouldn't lose any state
        scenario.recreate()
    }
}
