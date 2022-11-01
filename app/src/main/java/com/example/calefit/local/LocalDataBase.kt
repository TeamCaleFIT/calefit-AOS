package com.example.calefit.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel

@Database(
    entities = [LocalExerciseListModel::class, LocalExerciseTemplateModel::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun userExerciseDao(): ExerciseDao

    abstract fun userTemplateDao(): TemplateDao

    companion object {
        private var instance: LocalDataBase? = null

        fun getInstance(context: Context): LocalDataBase? {
            if (instance == null) {
                synchronized(LocalDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDataBase::class.java,
                        "database"
                    ).build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}