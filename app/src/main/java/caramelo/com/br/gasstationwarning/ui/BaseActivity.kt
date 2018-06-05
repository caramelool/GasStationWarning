package caramelo.com.br.gasstationwarning.ui

import android.arch.lifecycle.Lifecycle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

abstract class BaseActivity(
        private val module: Kodein.Module
): AppCompatActivity(), KodeinAware {

    private val closestKodein by closestKodein()
    override val kodein = Kodein.lazy {
        extend(closestKodein)
        import(module)
        bind<FragmentActivity>() with provider { this@BaseActivity }
        bind<Lifecycle>() with provider { lifecycle }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }
}