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

package eu.balticit.europen.ui.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import eu.balticit.europen.R
import eu.balticit.europen.data.prefs.AppSharedPrefs
import eu.balticit.europen.ui.intro.IntroActivity

class AboutFragment : Fragment() {

    private lateinit var mPrefs: AppSharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mPrefs = AppSharedPrefs.getInstance(activity)

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val watchIntro: ImageView = view.findViewById(R.id.iv_about_link_functions)
        watchIntro.setOnClickListener { v ->
            mPrefs.watchAppIntro(false)
            val intent = Intent(activity, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}