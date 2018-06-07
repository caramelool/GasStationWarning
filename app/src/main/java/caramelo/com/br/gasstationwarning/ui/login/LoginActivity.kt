package caramelo.com.br.gasstationwarning.ui.login

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseActivity
import caramelo.com.br.gasstationwarning.ui.home.HomeActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity(loginModule.init) {

    companion object {
        fun getIntent(
                context: Context
        ) = Intent(context, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    private val viewModel: LoginViewModel by kodein.instance()
    private val callbackManager by lazy { CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupFacebook()
        setupAnonymous()

        viewModel.loginLiveData.observe(this, stepObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private val stepObserver = Observer<LoginStep> { step ->
        when(step) {
            is LoginStep.Success -> openHome()
            is LoginStep.Fail,
            is LoginStep.Login -> { showLogin() }
            is LoginStep.Loading -> { showLoading() }
        }
    }

    private fun setupFacebook() {
        facebookLoginButton.setReadPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                viewModel.handleFacebookAccessToken(result)
            }

            override fun onCancel() {
                viewModel.handleCancel()
            }

            override fun onError(error: FacebookException?) {
                viewModel.handleError()
            }
        })
    }

    private fun setupAnonymous() {
        val spannable = SpannableString(anonymousLoginTextView.text)
        spannable.setSpan(UnderlineSpan(), 0, spannable.length, 0)
        anonymousLoginTextView.text = spannable
        anonymousLoginTextView.setOnClickListener {
            viewModel.handleAnonymousLogin()
        }
    }

    private fun openHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading() {
        loginGroup.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun showLogin() {
        loginGroup.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }
}
