package com.amolinaj.eventmaster.di

import android.content.Context
import androidx.room.Room
import com.amolinaj.eventmaster.data.local.EventMasterDatabase
import com.amolinaj.eventmaster.data.local.dao.EventCategoryDao
import com.amolinaj.eventmaster.data.local.dao.EventItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventMasterDatabase {
        return Room.databaseBuilder(
            context,
            EventMasterDatabase::class.java,
            "eventmaster.db"
        )
            .addCallback(SeedDatabaseCallback())
            .build()
    }

    @Provides
    fun provideEventCategoryDao(database: EventMasterDatabase): EventCategoryDao {
        return database.eventCategoryDao()
    }

    @Provides
    fun provideEventItemDao(database: EventMasterDatabase): EventItemDao {
        return database.eventItemDao()
    }

    private class SeedDatabaseCallback : androidx.room.RoomDatabase.Callback() {

        override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("INSERT INTO categories(name, description) VALUES('Deportes', 'Eventos y competencias deportivas')")
            db.execSQL("INSERT INTO categories(name, description) VALUES('Tecnologia', 'Conferencias y meetups')")
            db.execSQL("INSERT INTO categories(name, description) VALUES('Musica', 'Conciertos y festivales')")

            db.execSQL("INSERT INTO events(category_id, title, description, date, location, image_res_name) VALUES(1, 'Maraton de Santiago', 'Competencia abierta para corredores amateurs y profesionales.', '26/04/2026', 'Santiago', 'event_sports')")
            db.execSQL("INSERT INTO events(category_id, title, description, date, location, image_res_name) VALUES(2, 'Congreso de Tecnologia', 'Charlas tecnicas con reconocidos exponentes de la tecnologia.', '28/07/2026', 'Sala de Teatro UCSC, Concepcion', 'event_tech')")
            db.execSQL("INSERT INTO events(category_id, title, description, date, location, image_res_name) VALUES(3, 'Festival de Prueba', 'Una noche con bandas y experiencias en vivo.', '12/06/2026', 'Parque Bicentenario, Concepcion', 'event_music')")
        }
    }
}
