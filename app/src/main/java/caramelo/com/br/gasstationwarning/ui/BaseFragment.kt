package caramelo.com.br.gasstationwarning.ui

import android.support.v4.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

abstract class BaseFragment(
        private val module: Kodein.Module
) : Fragment(), KodeinAware {

    private val closestKodein by closestKodein()
    override val kodein = Kodein.lazy {
        extend(closestKodein)
        import(module)
        bind<Fragment>() with singleton { this@BaseFragment }
    }
}