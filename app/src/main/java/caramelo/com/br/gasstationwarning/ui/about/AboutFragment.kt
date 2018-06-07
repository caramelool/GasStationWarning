package caramelo.com.br.gasstationwarning.ui.about

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.BuildConfig
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseFragment
import caramelo.com.br.gasstationwarning.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_about.*
import org.kodein.di.generic.instance

class AboutFragment : BaseFragment(aboutModule.init) {

    private val viewModel: AboutViewModel by kodein.instance()

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val devName = getString(R.string.dev_name)
        val devRm = getString(R.string.dev_rm)
        val versionName = "v.${BuildConfig.VERSION_NAME}"
        versionNameTextView.text = versionName
        devNameTextView.text = getString(R.string.about_dev_name, devName)
        devRmTextView.text = getString(R.string.about_dev_rm, devRm)

        logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val context = context ?: return
        AlertDialog.Builder(context)
                .setTitle(R.string.title_logout)
                .setMessage(R.string.message_logout)
                .setPositiveButton(R.string.yes, { _, _ ->
                    doLogout()
                })
                .setNegativeButton(R.string.no, null)
                .show()

    }

    private fun doLogout() {
        val context = context ?: return
        viewModel.signOut()
        val intent = LoginActivity.getIntent(context)
        context.startActivity(intent)
        activity?.finish()
    }
}
