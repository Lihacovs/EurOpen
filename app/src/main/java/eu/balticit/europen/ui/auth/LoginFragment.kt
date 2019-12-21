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

package eu.balticit.europen.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import eu.balticit.europen.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        auth = FirebaseAuth.getInstance()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText: EditText = view.findViewById(R.id.et_login_email)
        val email = emailEditText.text
        val passwordEditText: EditText = view.findViewById(R.id.et_login_password)
        val password = passwordEditText.text

        val serverLoginButton: Button = view.findViewById(R.id.btn_login_server)
        serverLoginButton.setOnClickListener{
            when {
                email.isEmpty() -> {
                    Toast.makeText(activity, getString(R.string.login_empty_email), Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(activity, getString(R.string.login_empty_password), Toast.LENGTH_SHORT).show()
                }
                !isEmailValid(email.toString()) -> {
                    Toast.makeText(activity, getString(R.string.login_invalid_email), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(activity, "FireBase server login", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val googleLoginButton: Button = view.findViewById(R.id.btn_login_google)
        googleLoginButton.setOnClickListener{
            Toast.makeText(activity, "FireBase Google login", Toast.LENGTH_SHORT).show()
        }

        val facebookLoginButton: Button = view.findViewById(R.id.btn_login_facebook)
        facebookLoginButton.setOnClickListener{
            Toast.makeText(activity, "FireBase Facebook login", Toast.LENGTH_SHORT).show()
        }

        val registerButton: Button = view.findViewById(R.id.btn_login_register)
        registerButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_nav_login_to_nav_register)
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val emailPattern = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

}