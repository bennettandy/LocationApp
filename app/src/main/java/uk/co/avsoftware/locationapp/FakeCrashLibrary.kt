package uk.co.avsoftware.locationapp

import timber.log.Timber

object FakeCrashLibrary {
    fun log(priority: Int, tag: String?, message: String) {
        Timber.v("Log $priority, $tag, $message")
    }

    fun logError(t: Throwable) {
        Timber.v(t, "Error ${t.message}")
    }

    fun logWarning(t: Throwable) {
        Timber.v(t, "Warning ${t.message}")
    }
}
