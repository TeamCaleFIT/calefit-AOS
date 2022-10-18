package com.example.calefit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import com.example.calefit.local.ExerciseDao
import com.example.calefit.local.LocalDataBase
import com.example.calefit.local.TemplateDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

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
                        ExerciseList.Sets("1", 3, 30)
                    ),
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 2, 100)
                    )
                ),
                ExerciseList.Exercise(
                    id = "3",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 5, 400)
                    )
                )
            )
        )
    }

    private val templateExercise2 by lazy {
        LocalExerciseTemplateModel(
            templateName = "템플릿2",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "스쿼트",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 3, 30)
                    ),
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 2, 100)
                    )
                ),
                ExerciseList.Exercise(
                    id = "3",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 5, 400)
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
                        ExerciseList.Sets("1", 3, 310)
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
                        ExerciseList.Sets("1", 3, 310)
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
                        ExerciseList.Sets("1", 3, 310)
                    ),
                )
            )
        )
    }

    private val templateExercise1 by lazy {
        LocalExerciseTemplateModel(
            templateName = "템플릿1",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "덤벨프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", 3, 310)
                    ),
                )
            )
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

        job.launch {

            launch {
                exerciseDao.insertExercise(sampleDailyExercise)
                exerciseDao.insertExercise(sampleDailyExercise2)
                exerciseDao.insertExercise(sampleDailyExercise3)
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
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteSpecificExistData() {
        val job = CoroutineScope(Dispatchers.IO)

        job.launch {
            launch {
                exerciseDao.insertExercise(sampleDailyExercise)
            }.join()

            var result = 0
            val templateResult = 0

            launch {
                result = exerciseDao.deleteWorkOutData("2022-08-09")
            }.join()

            assertEquals(result, 1)
            assertEquals(templateResult, 1)
        }
    }


    @Test
    @Throws(Exception::class)
    fun putTemplateDataAndDelete() {
        val job = CoroutineScope(Dispatchers.IO)

        job.launch {
            launch {
                templateDao.insertTemplate(templateExercise1)
                templateDao.insertTemplate(templateExercise2)
            }.join()

            launch {
                templateDao.observeTemplates().collect {
                    assertEquals(it.size, 2)
                    assertEquals(it[0].templateName, "템플릿1")
                }
            }.join()

            launch {
                templateDao.deleteWorkOutData("템플릿1")
                templateDao.deleteWorkOutData("템플릿")
                templateDao.observeTemplates().collect {
                    assertEquals(it.size, 1)
                    assertEquals(it[0].templateName, "템플릿2")
                }
            }.join()
        }
    }
}