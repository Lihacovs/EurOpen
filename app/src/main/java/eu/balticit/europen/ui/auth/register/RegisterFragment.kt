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

package eu.balticit.europen.ui.auth.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import eu.balticit.europen.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Register fragment. Creates user account in Firebase server.
 */
class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var mEmailEt: EditText
    private lateinit var mPasswordEt: EditText
    private lateinit var mNameEt: EditText
    private lateinit var mSurnameEt: EditText
    private lateinit var mPhotoIv: ImageView
    private lateinit var mBirthDateEt: EditText
    private var mGender: String = "Not specified"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        auth = FirebaseAuth.getInstance()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEmailEt = view.findViewById(R.id.et_register_email)
        mPasswordEt = view.findViewById(R.id.et_register_password)
        mNameEt = view.findViewById(R.id.et_register_name)
        mSurnameEt = view.findViewById(R.id.et_register_surname)
        mPhotoIv = view.findViewById(R.id.iv_register_add_photo)
        mBirthDateEt = view.findViewById(R.id.et_register_birth_date)


        mPhotoIv.setOnClickListener {
            Toast.makeText(activity, "Upload user photo", Toast.LENGTH_SHORT).show()
        }

        val myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "d MMM yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            mBirthDateEt.setText(sdf.format(myCalendar.time))
        }

        //TODO: Make spinner instead of calendar for birth date picker
        mBirthDateEt.setOnClickListener {
            DatePickerDialog(
                activity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val registerTermsTextView: TextView = view.findViewById(R.id.tv_register_terms)
        registerTermsTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_nav_register_to_nav_about)
        }

        val radioGroup: RadioGroup = view.findViewById(R.id.rg_register_radio_group)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_register_male -> mGender = "Male"
                R.id.rb_register_female -> mGender = "Female"
                R.id.rb_register_other -> mGender = "Not specified"
            }

        }

        val registerUserButton: Button = view.findViewById(R.id.btn_register_register)
        registerUserButton.setOnClickListener {
            when {
                mEmailEt.text.toString().isEmpty() -> {
                    Toast.makeText(
                        activity, getString(R.string.register_empty_email), Toast.LENGTH_SHORT
                    ).show()
                }
                !isEmailValid(mEmailEt.text.toString()) -> {
                    Toast.makeText(
                        activity, getString(R.string.register_invalid_email), Toast.LENGTH_SHORT
                    ).show()
                }
                mPasswordEt.text.toString().isEmpty() -> {
                    Toast.makeText(
                        activity, getString(R.string.register_empty_password), Toast.LENGTH_SHORT
                    ).show()
                }
                mPasswordEt.text.trim().length < 6 -> {
                    Toast.makeText(
                        activity, getString(R.string.register_short_password), Toast.LENGTH_SHORT
                    ).show()
                }
                mNameEt.text.toString().isEmpty() -> {
                    Toast.makeText(
                        activity, getString(R.string.register_empty_name), Toast.LENGTH_SHORT
                    ).show()
                }
                mNameEt.text.trim().length < 2 -> {
                    Toast.makeText(
                        activity, getString(R.string.register_short_name), Toast.LENGTH_SHORT
                    ).show()
                }
                mSurnameEt.text.toString().isEmpty() -> {
                    Toast.makeText(
                        activity, getString(R.string.register_empty_surname), Toast.LENGTH_SHORT
                    ).show()
                }
                mSurnameEt.text.toString().length < 2 -> {
                    Toast.makeText(
                        activity, getString(R.string.register_short_surname), Toast.LENGTH_SHORT
                    ).show()
                }
                mBirthDateEt.text.toString().isEmpty() -> {
                    Toast.makeText(
                        activity, getString(R.string.register_empty_birth_date), Toast.LENGTH_SHORT
                    ).show()
                }
                else -> createFirebaseUser(
                    mEmailEt.text.toString(),
                    mPasswordEt.text.toString(),
                    "photoUrl",
                    mNameEt.text.toString(),
                    mSurnameEt.text.toString(),
                    mGender,
                    mBirthDateEt.toString()
                    )
            }

        }
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val emailPattern = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun createFirebaseUser(
        email: String,
        password: String,
        photoUrl: String,
        name: String,
        surname: String,
        gender: String,
        birthDate: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(activity, "Firebase User created", Toast.LENGTH_SHORT)
                .show()
            activity?.onBackPressed()
        }.addOnFailureListener { e ->
            when (e) {
                is FirebaseAuthUserCollisionException -> Toast.makeText(
                    activity, getString(R.string.register_email_already_used), Toast.LENGTH_SHORT
                ).show()
                is FirebaseAuthWeakPasswordException -> Toast.makeText(
                    activity, e.reason, Toast.LENGTH_SHORT
                ).show()
                is FirebaseAuthInvalidCredentialsException -> Toast.makeText(
                    activity, e.message, Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(
                    activity, getString(R.string.register_some_error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}