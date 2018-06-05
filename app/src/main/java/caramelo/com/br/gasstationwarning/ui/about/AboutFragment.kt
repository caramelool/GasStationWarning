package caramelo.com.br.gasstationwarning.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseFragment
import org.kodein.di.generic.instance

class AboutFragment : BaseFragment(aboutModule) {

    private val viewModel: AboutViewModel by kodein.instance()

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}
