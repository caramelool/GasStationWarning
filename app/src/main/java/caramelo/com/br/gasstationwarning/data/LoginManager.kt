package caramelo.com.br.gasstationwarning.data

import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class LoginManager(
    private val firebaseAuth: FirebaseAuth,
    private val loginManager: LoginManager
) {

    fun signOut() {
        firebaseAuth.signOut()
        loginManager.logOut()
    }

    fun isLogged() : Boolean {
        return firebaseAuth.currentUser != null
    }
}