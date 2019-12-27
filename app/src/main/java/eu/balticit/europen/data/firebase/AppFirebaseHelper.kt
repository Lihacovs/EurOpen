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

package eu.balticit.europen.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * Reads and writes the data from Firebase database.
 */
//TODO: check in class works as a singleton
object AppFirebaseHelper {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mStorage: FirebaseStorage = FirebaseStorage.getInstance()

    //=//=// F I R E B A S E  -  A U T H E N T I C A T I O N //=//=//

    fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

}