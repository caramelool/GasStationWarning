package caramelo.com.br.gasstationwarning.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val firebaseModule = Kodein.Module {
    bind<FirebaseAuth>() with singleton { FirebaseAuth.getInstance() }
    bind<FirebaseFirestoreSettings>() with singleton {
        FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .setPersistenceEnabled(true)
            .build()
    }
    bind<FirebaseFirestore>() with singleton {
        val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = instance()
        return@singleton db
    }
    bind<CollectionReference>("stations_collection") with provider {
        instance<FirebaseFirestore>().collection("stations")
    }
    bind<CollectionReference>("comments_collection") with provider {
        instance<FirebaseFirestore>().collection("comments")
    }
}