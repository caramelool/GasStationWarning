package caramelo.com.br.gasstationwarning

import android.app.Application
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import caramelo.com.br.gasstationwarning.data.LoginManager
import caramelo.com.br.gasstationwarning.di.facebookModule
import caramelo.com.br.gasstationwarning.di.firebaseModule
import caramelo.com.br.gasstationwarning.di.repositoryModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import java.security.MessageDigest

class GasStationApplication : Application(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        logKeyHash()
    }

    override val kodein = Kodein.lazy {
        import(firebaseModule)
        import(facebookModule)
        import(repositoryModule)
        bind<LoginManager>() with provider { LoginManager(instance(), instance()) }
    }

    private fun logKeyHash() {
        try {
            val info = packageManager.getPackageInfo(
                    "caramelo.com.br.gasstationwarning",
                    PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: Throwable) { }
    }
}