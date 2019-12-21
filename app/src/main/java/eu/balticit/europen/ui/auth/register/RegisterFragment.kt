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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eu.balticit.europen.R

/**
 * Register fragment. Creates user account in Firebase server.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerAddPhotoIV: ImageView = view.findViewById(R.id.iv_register_add_photo)
        registerAddPhotoIV.setOnClickListener {
            Toast.makeText(activity, "Upload user photo", Toast.LENGTH_SHORT).show()
        }

        val registerBirthDateET: EditText = view.findViewById(R.id.et_register_birth_date)
        registerBirthDateET.setOnClickListener {
            Toast.makeText(activity, "Pick date from calendar", Toast.LENGTH_SHORT).show()
        }

        val registerTermsTextView: TextView = view.findViewById(R.id.tv_register_terms)
        registerTermsTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_nav_register_to_nav_about)
        }

        val registerUserButton: Button = view.findViewById(R.id.btn_register_register)
        registerUserButton.setOnClickListener{
            Toast.makeText(activity, "Register user in firebase DB", Toast.LENGTH_SHORT).show()
        }
    }
}