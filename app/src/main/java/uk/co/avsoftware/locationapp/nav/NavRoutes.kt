package uk.co.avsoftware.locationapp.nav

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object LocationPage : NavRoutes("location")
    object SpaceLaunch : NavRoutes("spaceLaunch")
}
