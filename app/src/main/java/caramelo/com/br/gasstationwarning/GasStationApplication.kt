package caramelo.com.br.gasstationwarning

import android.content.Context
import android.content.pm.PackageManager
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Base64
import android.util.Log
import caramelo.com.br.gasstationwarning.data.UserManager
import caramelo.com.br.gasstationwarning.di.facebookModule
import caramelo.com.br.gasstationwarning.di.firebaseModule
import caramelo.com.br.gasstationwarning.di.repositoryModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import java.security.MessageDigest

class GasStationApplication : MultiDexApplication(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        logKeyHash()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override val kodein = Kodein.lazy {
        import(firebaseModule)
        import(facebookModule)
        import(repositoryModule)
        bind<UserManager>() with provider { UserManager(instance(), instance()) }
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