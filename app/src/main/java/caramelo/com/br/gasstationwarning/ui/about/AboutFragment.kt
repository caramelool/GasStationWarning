package caramelo.com.br.gasstationwarning.ui.about

import android.os.Bundle
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
        val versionName = "v.${BuildConfig.VERSION_NAME}"
        versionNameTextView.text = versionName
        devNameTextView.text = getString(R.string.about_dev_name, devName)

        logoutButton.setOnClickListener {
            val context = context ?: return@setOnClickListener
            viewModel.signOut()
            val intent = LoginActivity.getIntent(context)
            context.startActivity(intent)
            activity?.finish()
        }
    }
}
