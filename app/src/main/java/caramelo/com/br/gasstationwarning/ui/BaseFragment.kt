package caramelo.com.br.gasstationwarning.ui

import android.arch.lifecycle.Lifecycle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

abstract class BaseFragment(
        private val module: Kodein.Builder.() -> Unit
) : Fragment(), KodeinAware {
    private val closestKodein by closestKodein()
    override val kodein = Kodein.lazy {
        extend(closestKodein)
        import(Kodein.Module(init = module))
        bind<Fragment>() with provider { this@BaseFragment }
        bind<FragmentActivity>() with provider { activity ?: throw IllegalStateException() }
        bind<Lifecycle>() with provider { lifecycle }
    }
}

fun <T : Parcelable> Fragment.getParcelable(key: String): T? = arguments?.getParcelable(key)