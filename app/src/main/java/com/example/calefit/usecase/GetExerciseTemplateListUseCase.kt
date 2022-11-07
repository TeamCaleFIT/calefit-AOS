package com.example.calefit.usecase

import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetExerciseTemplateListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<ExerciseTemplateSummary>> {
        return repository.getExerciseTemplateFromRoom().map { data ->
            val templateList = mutableListOf<ExerciseTemplateSummary>()
            data.values.forEach {
                templateList.add(
                    ExerciseTemplateSummary(
                        id = UUID.randomUUID(),
                        templateTitle = it.templateName,
                        exerciseCount = it.list.size,
                        exerciseDate = it.date
                    )
                )
            }
            templateList.apply {
                sortBy { it.templateTitle }
            }
        }
    }
}