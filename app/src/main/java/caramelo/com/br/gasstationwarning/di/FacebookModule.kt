package caramelo.com.br.gasstationwarning.di

import com.facebook.login.LoginManager
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val facebookModule = Kodein.Module {
    bind<LoginManager>() with singleton { LoginManager.getInstance() }
}