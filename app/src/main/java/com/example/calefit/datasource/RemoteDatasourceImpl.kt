package com.example.calefit.datasource

import android.util.Log
import com.example.calefit.local.ExerciseDao
import com.example.calefit.network.RemoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoteDatasourceImpl @Inject constructor(
    private val api: RemoteApi,
    private val exerciseDao: ExerciseDao
) : RemoteDatasource {

    override fun getDateList() {
        CoroutineScope(Dispatchers.Default).launch {
            val result = api.getDateList()
            Log.d("dataSource", result.toString())
        }
    }
}