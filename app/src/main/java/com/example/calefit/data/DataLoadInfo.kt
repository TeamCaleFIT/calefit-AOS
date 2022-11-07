package com.example.calefit.data

import java.io.Serializable

data class DataLoadInfo(
    val initialClickedDate: String = "",
    val templateName: String = "",
) : Serializable