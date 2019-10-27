/*
 * Copyright (C) 2019 Baltic Information Technologies
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.balticit.europen.data.prefs

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

/**
 * Reads and writes the data from android shared preferences.
 */
class AppSharedPrefs {

    var counter: Int = 0

    constructor(){
        counter++
    }

    companion object {
        private const val PREF_KEY_INTRO_WATCHED = "PREF_KEY_INTRO_WATCHED"
        private const val prefFileName = "europen_shared_prefs"
        private lateinit var mPrefs: SharedPreferences
        private val instance: AppSharedPrefs = AppSharedPrefs()

        fun getInstance(context: Context): AppSharedPrefs {
            mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
            return instance
        }
    }

    fun countInstances(context: Context) {
        Toast.makeText(context, "SharedPrefs instance count: " + counter, Toast.LENGTH_SHORT).show()
    }

    /**
     * Puts true value if AppIntro was watched
     */
    fun watchAppIntro(watched: Boolean) {
        mPrefs.edit().putBoolean(PREF_KEY_INTRO_WATCHED, watched).apply()
    }

    fun isAppIntroWatched(): Boolean {
        return mPrefs.getBoolean(PREF_KEY_INTRO_WATCHED, false)
    }
}
