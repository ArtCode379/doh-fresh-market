package dohdohs.grocery.dohfreshmarket.di

import androidx.room.Room
import dohdohs.grocery.dohfreshmarket.data.database.HUFFNDatabase
import org.koin.dsl.module

private const val DB_NAME = "huffn_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = HUFFNDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<HUFFNDatabase>().cartItemDao() }

    single { get<HUFFNDatabase>().orderDao() }
}