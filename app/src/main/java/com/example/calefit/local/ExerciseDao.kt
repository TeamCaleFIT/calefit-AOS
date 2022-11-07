package com.example.calefit.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.calefit.data.localdto.LocalExerciseListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(value: LocalExerciseListModel)

    @Query("SELECT * FROM exercise")
    fun getAllExerciseList(): Flow<List<LocalExerciseListModel>>

    @Query("SELECT * FROM exercise WHERE date = :selectedDate")
    fun getSpecificExerciseListByDate(selectedDate: String): Flow<LocalExerciseListModel>

    @Query("SELECT * FROM exercise WHERE date BETWEEN :start And :end")
    fun getYearExerciseList(start: String, end: String): Flow<List<LocalExerciseListModel>>

    //'1' will be returned and it stands for 'delete success'
    @Query("DELETE FROM exercise WHERE date = :selectedDate")
    suspend fun deleteWorkOutData(selectedDate: String): Int
}