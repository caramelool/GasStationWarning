package caramelo.com.br.gasstationwarning.di

import caramelo.com.br.gasstationwarning.data.repository.CommentRepository
import caramelo.com.br.gasstationwarning.data.repository.StationRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val repositoryModule = Kodein.Module {
    bind<StationRepository>() with provider {
        StationRepository(instance("stations_collection"))
    }
    bind<CommentRepository>() with provider {
        CommentRepository(instance("comments_collection"))
    }
}