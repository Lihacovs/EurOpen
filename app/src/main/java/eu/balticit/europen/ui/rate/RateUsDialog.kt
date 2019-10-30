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

package eu.balticit.europen.ui.rate

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import eu.balticit.europen.R
import java.util.*

class RateUsDialog : DialogFragment() {

    private lateinit var mRatingMessageView: View
    private lateinit var mPlayStoreRatingView: View
    private lateinit var mRatingBar: RatingBar
    private lateinit var mSubmitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_rate_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRatingMessageView = view.findViewById(R.id.view_rating_message)
        mPlayStoreRatingView = view.findViewById(R.id.view_play_store_rating)
        mRatingBar = view.findViewById(R.id.rating_bar_feedback)
        mSubmitButton = view.findViewById(R.id.btn_submit)
        setUp(view)
    }

    private fun setUp(view: View) {
        mRatingMessageView.visibility = View.GONE
        mPlayStoreRatingView.visibility = View.GONE

        val stars: LayerDrawable = mRatingBar.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(
            ContextCompat.getColor(
                Objects.requireNonNull(context)!!,
                R.color.colorAccent
            ),
            PorterDuff.Mode.SRC_ATOP
        )

        stars.getDrawable(0).setColorFilter(
            ContextCompat.getColor(
                Objects.requireNonNull(context)!!,
                R.color.shadow
            ),
            PorterDuff.Mode.SRC_ATOP
        )

        stars.getDrawable(1).setColorFilter(
            ContextCompat.getColor(
                Objects.requireNonNull(context)!!,
                R.color.shadow
            ),
            PorterDuff.Mode.SRC_ATOP
        )

        mSubmitButton.setOnClickListener {
            Toast.makeText(activity, "Rating: " + mRatingBar.rating, Toast.LENGTH_SHORT).show()
        }
    }
}