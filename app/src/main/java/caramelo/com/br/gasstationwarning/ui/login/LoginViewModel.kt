package caramelo.com.br.gasstationwarning.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(
    private val auth: FirebaseAuth
) : ViewModel() {

    val loginLiveData = MutableLiveData<LoginStep>()

    fun handleFacebookAccessToken(loginResult: LoginResult?) {
        loginLiveData.postValue(LoginStep.Loading())
        if (loginResult == null) {
            handleError()
            return
        }
        val accessToken = loginResult.accessToken
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> onLoginSuccessful()
                        else -> handleError()
                    }
                }
    }

    fun handleAnonymousLogin() {
        loginLiveData.postValue(LoginStep.Loading())
        auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> onLoginSuccessful()
                        else -> handleError()
                    }
                }
    }

    fun handleError() {
        loginLiveData.postValue(LoginStep.Fail())
    }

    private fun onLoginSuccessful() {
        loginLiveData.postValue(LoginStep.Success())
    }
}

sealed class LoginStep {
    class Loading: LoginStep()
    class Success : LoginStep()
    class Fail : LoginStep()
}