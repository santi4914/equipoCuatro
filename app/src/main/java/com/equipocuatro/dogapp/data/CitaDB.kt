package com.equipocuatro.dogapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.utils.Constants.NAME_BD

@Database(entities = [Cita::class], version = 1, exportSchema = false)
abstract class CitaDB : RoomDatabase() {
    abstract fun citaDao(): CitaDao

    companion object {
        // Una sola instancia de la base de datos para toda la app.
        @Volatile
        private var instancia: CitaDB? = null

        fun getDatabase(context: Context): CitaDB {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    CitaDB::class.java,
                    NAME_BD
                ).build().also { instancia = it }
            }
        }
    }
}
