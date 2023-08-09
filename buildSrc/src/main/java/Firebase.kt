object Firebase {
    private const val bomVersion = "32.2.2"
    const val bom = "com.google.firebase:firebase-bom:${Firebase.bomVersion}"

    // When NOT using the BoM, you must specify versions in Firebase library dependencies
    const val authenticationVersion = "22.1.1"
    const val authentication = "com.google.firebase:firebase-auth-ktx:$authenticationVersion"
}