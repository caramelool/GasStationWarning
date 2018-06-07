package caramelo.com.br.gasstationwarning.ui.splash

import android.arch.lifecycle.Observer
import android.os.Bundle
import caramelo.com.br.gasstationwarning.ui.BaseActivity
import caramelo.com.br.gasstationwarning.ui.home.HomeActivity
import caramelo.com.br.gasstationwarning.ui.login.LoginActivity
import org.kodein.di.generic.instance

class SplashActivity : BaseActivity(splashModule.init) {

    private val viewModel: SplashViewModel by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.livedata?.observe(this, splashObserver)
    }

    private val splashObserver = Observer<SplashStatus> {
        when (it) {
            is SplashStatus.Login -> openLogin()
            is SplashStatus.Home -> openHome()
        }
        finish()
    }

    private fun openLogin() {
        val intent = LoginActivity.getIntent(this)
        startActivity(intent)
    }

    private fun openHome() {
        val intent = HomeActivity.getIntent(this)
        startActivity(intent)
    }
}
