package com.comind.myefreicard.data

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFS_NAME = "efrei_card_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_LOGGED_IN_EMAIL = "logged_in_email"
    private const val KEY_BIOMETRICS_ENABLED = "biometrics_enabled"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var loggedInEmail: String?
        get() = prefs.getString(KEY_LOGGED_IN_EMAIL, null)
        set(value) = prefs.edit().putString(KEY_LOGGED_IN_EMAIL, value).apply()

    var isBiometricsEnabled: Boolean
        get() = prefs.getBoolean(KEY_BIOMETRICS_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_BIOMETRICS_ENABLED, value).apply()

    val currentProfile: Profile
        get() {
            val email = loggedInEmail
            return ProfilesData.profiles.firstOrNull { it.student.email == email }
                ?: ProfilesData.gaspard
        }

    val currentStudent: Student
        get() = currentProfile.student

    val currentCourses: List<Course>
        get() = currentProfile.courses

    val currentFacilities: List<Facility>
        get() = ProfilesData.getFacilitiesForAccess(currentProfile.accessLevel)

    fun logout() {
        isLoggedIn = false
        loggedInEmail = null
    }
}
