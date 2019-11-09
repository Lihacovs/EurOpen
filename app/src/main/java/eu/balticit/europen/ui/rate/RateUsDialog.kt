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

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import eu.balticit.europen.R
import java.util.*

class RateUsDialog : DialogFragment() {

    private var isRatingSecondaryActionShown = false

    private lateinit var mRatingMessageView: View
    private lateinit var mPlayStoreRatingView: View
    private lateinit var mRatingBar: RatingBar
    private lateinit var mSubmitButton: Button
    private lateinit var mLaterButton: Button
    private lateinit var mRatingMessageEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_rate_us, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialog = Dialog(Objects.requireNonNull(activity))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRatingMessageView = view.findViewById(R.id.view_rating_message)
        mPlayStoreRatingView = view.findViewById(R.id.view_play_store_rating)
        mRatingBar = view.findViewById(R.id.rating_bar_feedback)
        mSubmitButton = view.findViewById(R.id.btn_submit)
        mLaterButton = view.findViewById(R.id.btn_later)
        mRatingMessageEditText = view.findViewById(R.id.et_message)
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
            onSubmitButtonClick(mRatingBar.rating, mRatingMessageEditText.text.toString())
            //Toast.makeText(activity, "Rating: " + mRatingBar.rating, Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btn_later).setOnClickListener {
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_rate_on_play_store).setOnClickListener{
            Toast.makeText(activity, "Play store rate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSubmitButtonClick(rating: Float, message: String) {
        if (rating == 0.0F) {
            Toast.makeText(
                activity,
                getString(R.string.rating_not_provided_error),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!isRatingSecondaryActionShown) {
            if (rating == 5.0F) {
                mPlayStoreRatingView.visibility = View.VISIBLE
                mSubmitButton.visibility = View.GONE
                mRatingBar.setIsIndicator(true)
            } else {
                mRatingMessageView.visibility = View.VISIBLE
            }
            isRatingSecondaryActionShown = true
            return
        }
        mRatingMessageEditText.hideKeyboard()
        Toast.makeText(activity, getString(R.string.rating_thanks) +": "+ message, Toast.LENGTH_SHORT).show()
        dismiss()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}