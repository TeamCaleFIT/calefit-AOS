package com.example.calefit.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(value: LocalExerciseTemplateModel)

    @Query("SELECT * FROM template")
    fun observeTemplates(): Flow<List<LocalExerciseTemplateModel>>

    @Query("DELETE FROM template WHERE date = :selectedDate")
    suspend fun deleteWorkOutData(selectedDate: String): Int
}