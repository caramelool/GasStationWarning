package caramelo.com.br.gasstationwarning.ui.about

import android.arch.lifecycle.ViewModel
import caramelo.com.br.gasstationwarning.data.UserManager

class AboutViewModel(
    private val userManager: UserManager
) : ViewModel() {


    fun signOut() {
        userManager.signOut()
    }
}