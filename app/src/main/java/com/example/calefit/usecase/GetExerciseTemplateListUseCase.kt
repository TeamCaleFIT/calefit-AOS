package com.example.calefit.usecase

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.repository.ExerciseRepository
import java.util.*
import javax.inject.Inject

class GetExerciseTemplateListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Aggregate<List<ExerciseTemplateSummary>> {
        return when (val rawData = repository.getExerciseDataFromRoom()) {
            is Aggregate.Success -> {
                val templateList = mutableListOf<ExerciseTemplateSummary>()
                rawData.data.forEach { map ->
                    val exerciseList = map.value
                    if (exerciseList.templateName != DEFAULT_TEMPLATE_NAME) {
                        templateList.add(
                            ExerciseTemplateSummary(
                                id = UUID.randomUUID(),
                                templateTitle = exerciseList.templateName,
                                exerciseCount = exerciseList.list.size,
                                exerciseDate = exerciseList.date
                            )
                        )
                    }
                }
                templateList.sortBy { it.templateTitle }
                Aggregate.Success(templateList)
            }
            else -> {
                Aggregate.Error(IllegalArgumentException())
            }
        }
    }

    private companion object {
        const val DEFAULT_TEMPLATE_NAME = ""
    }
}