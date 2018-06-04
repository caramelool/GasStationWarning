package caramelo.com.br.gasstationwarning.ui.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import caramelo.com.br.gasstationwarning.data.LoginManager

class SplashViewModel(
    private val loginManager: LoginManager
) : ViewModel() {

    var livedata: MutableLiveData<SplashStatus>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                delayValidation()
            }
            return field
        }

    private fun delayValidation() {
        Handler().postDelayed({
            checkSplashStatus()
        }, 1000)
    }

    private fun checkSplashStatus() {
        if (loginManager.isLogged()) {
            livedata?.postValue(SplashStatus.Home())
        } else {
            livedata?.postValue(SplashStatus.Login())
        }
    }
}

sealed class SplashStatus {
    class Login : SplashStatus()
    class Home : SplashStatus()
}