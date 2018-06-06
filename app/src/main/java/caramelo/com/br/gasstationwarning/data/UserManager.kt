package caramelo.com.br.gasstationwarning.data

import caramelo.com.br.gasstationwarning.data.model.User
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class UserManager(
    private val firebaseAuth: FirebaseAuth,
    private val loginManager: LoginManager
) {

    fun getUser(): User {
        val currentUser = firebaseAuth.currentUser ?: throw NullPointerException()
        val displayName = if (currentUser.isAnonymous) "Anonymous"
            else currentUser.displayName ?: "Anonymous"
        return User(currentUser.uid, displayName)
    }

    fun signOut() {
        firebaseAuth.signOut()
        loginManager.logOut()
    }


    fun isLogged() : Boolean {
        return firebaseAuth.currentUser != null
    }
}