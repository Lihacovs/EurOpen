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

package eu.balticit.europen.ui.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import eu.balticit.europen.R
import eu.balticit.europen.data.prefs.AppSharedPrefs


class IntroActivity : AppIntro2() {

     private lateinit var mPrefs: AppSharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        mPrefs = AppSharedPrefs.getInstance(this)
        mPrefs.countInstances(this)
        if (mPrefs.isAppIntroWatched()) {
            finish()
        }

        if (supportActionBar != null) supportActionBar!!.hide()

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SlideFragment.newInstance(R.layout.intro_layout_one))
        addSlide(SlideFragment.newInstance(R.layout.intro_layout_two))
        addSlide(SlideFragment.newInstance(R.layout.intro_layout_three))
        addSlide(SlideFragment.newInstance(R.layout.intro_layout_four))
        addSlide(SlideFragment.newInstance(R.layout.intro_layout_five))


        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(Color.parseColor("#3F51B5"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showStatusBar(false)
        showSkipButton(false)
        isProgressButtonEnabled = true
        setZoomAnimation()
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        // Do something when users tap on Skip button.
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        // Do something when users tap on Done button.
        mPrefs.watchAppIntro(true)
        finish()
    }
}