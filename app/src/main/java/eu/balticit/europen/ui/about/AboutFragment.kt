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

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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

        //Opens app introduction screens
        val watchIntroIv: ImageView = view.findViewById(R.id.iv_about_watch_intro)
        watchIntroIv.setOnClickListener {
            mPrefs.watchAppIntro(false)
            val intent = Intent(activity, IntroActivity::class.java)
            startActivity(intent)
        }

        //Opens email to send to developer
        val sendEmailIv: ImageView = view.findViewById(R.id.iv_about_send_email)
        sendEmailIv.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", getString(R.string.app_email), null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body))
            startActivity(
                Intent.createChooser(
                    emailIntent,
                    getString(R.string.email_chooser_title)
                )
            )
        }

        //Opens GooglePlay to rate app
        val rateAppIv: ImageView = view.findViewById(R.id.iv_about_send_rate)
        rateAppIv.setOnClickListener {
            val appPackageName: String = context!!.packageName
            try {
                activity!!.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            activity!!.resources.getString(R.string.app_market_link) + appPackageName
                        )
                    )
                )
            } catch (e: ActivityNotFoundException) {
                activity!!.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            activity!!.resources.getString(R.string.app_google_play_store_link) + appPackageName
                        )
                    )
                )
            }
        }
    }
}