package com.example.calefit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.calefit.data.ExerciseList
import com.example.calefit.local.ExerciseDao
import com.example.calefit.local.LocalDataBase
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import com.example.calefit.local.TemplateDao
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var exerciseDao: ExerciseDao
    private lateinit var templateDao: TemplateDao
    private lateinit var db: LocalDataBase

    private val sampleDailyExercise by lazy {
        LocalExerciseListModel(
            date = "2022-08-09",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "스쿼트",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "30")
                    ),
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "2", "100")
                    )
                ),
                ExerciseList.Exercise(
                    id = "3",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "5", "400")
                    )
                )
            )
        )
    }

    private val sampleDailyExercise2 by lazy {
        LocalExerciseListModel(
            date = "2022-08-10",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "덤벨프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "310")
                    ),
                )
            )
        )
    }

    private val sampleDailyExercise3 by lazy {
        LocalExerciseListModel(
            date = "2021-07-10",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "덤벨프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "310")
                    ),
                )
            )
        )
    }

    private val sampleDailyExercise4 by lazy {
        LocalExerciseListModel(
            date = "2022-08-11",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "덤벨프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "310")
                    ),
                )
            )
        )
    }

    private val sampleTemplate by lazy {
        LocalExerciseTemplateModel(
            date = "2022-08-09",
            title = "3분할루틴"
        )
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LocalDataBase::class.java
        ).build()
        exerciseDao = db.userExerciseDao()
        templateDao = db.userTemplateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeExerciseListAndRead() {
        val job = CoroutineScope(Dispatchers.IO)
        val savedThisYearData = exerciseDao.getYearExerciseList("2022-01-01", "2022-12-31")
        val savedLastYearData = exerciseDao.getYearExerciseList("2021-01-01", "2021-12-31")
        val savedTemplate = templateDao.observeTemplates()

        job.launch {

            launch {
                exerciseDao.insertExercise(sampleDailyExercise)
                exerciseDao.insertExercise(sampleDailyExercise2)
                exerciseDao.insertExercise(sampleDailyExercise3)
                templateDao.insertTemplate(sampleTemplate)
            }.join()

            launch {
                savedThisYearData.collect {
                    assertEquals(it[0].date, "2022-08-09")
                    assertEquals(it[0].list.size, 3)
                    assertEquals(it.size, 2)
                }
            }

            launch {
                savedLastYearData.collect {
                    assertEquals(it[0].date, "2021-07-10")
                    assertEquals(it.size, 1)
                }
            }

            val specificExerciseData = exerciseDao.getSpecificExerciseList("2022-08-10")
            assertEquals(specificExerciseData.date, sampleDailyExercise2.date)

            launch {
                savedTemplate.collect {
                    assertEquals(it[0].date, "2022-08-09")
                    assertEquals(it[0].title, "3분할루틴")
                }
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteSpecificExistData() {
        val job = CoroutineScope(Dispatchers.IO)

        job.launch {
            launch {
                exerciseDao.insertExercise(sampleDailyExercise)
                templateDao.insertTemplate(sampleTemplate)
            }.join()

            var result = 0
            var templateResult = 0

            launch {
                result = exerciseDao.deleteWorkOutData("2022-08-09")
                templateResult = templateDao.deleteWorkOutData("2022-08-09")
            }.join()

            assertEquals(result, 1)
            assertEquals(templateResult, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testFlowData() {
        val job = CoroutineScope(Dispatchers.IO)
        val savedThisYearData = exerciseDao.getYearExerciseList("2022-01-01", "2022-12-31")

        job.launch {
            launch {
                exerciseDao.insertExercise(sampleDailyExercise)
                exerciseDao.insertExercise(sampleDailyExercise4)
            }.join()

            launch {
                savedThisYearData.onEach {
                    it.forEach { data ->
                        println(data.date)
                    }
                }.collect()
            }

            launch {
                exerciseDao.insertExercise(sampleDailyExercise2)
            }.join()
        }
    }
}