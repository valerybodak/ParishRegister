package com.parish.register.repository

import com.parish.register.db.dao.DaoBorn
import com.parish.register.model.Category
import com.parish.register.common.FirebaseHelper
import com.parish.register.common.Resource
import com.parish.register.common.SharedPrefsManager
import com.parish.register.common.SyncHelper
import com.parish.register.model.ListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRegisterRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: DaoBorn
) {

    fun getQuiz(category: Category = Category.ALL): Flow<Resource<List<ListItem>>> {
        return FirebaseHelper.loadFileData(
            BORN_LIST_FILE_NAME,
            query = { daoQuestions.getAllQuestions().map { it.toQuestion() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_PARISH_REGISTER)) },
            observeProgress = true,
            saveFetchResponse = { rawItems -> saveQuiz(rawItems) }
        )
    }

    private suspend fun saveQuiz(rawItems: List<String>) {
        rawItems.forEach { line ->
            line.toQuestionEntity()?.let { remoteQuestion ->
                val localQuestion = daoQuestions.getQuestion(remoteQuestion.id)
                if (localQuestion != null) {
                    daoQuestions.update(
                        localQuestion.copy(
                            difficulty = remoteQuestion.difficulty,
                            text = remoteQuestion.text,
                        )
                    )
                } else {
                    daoQuestions.insert(remoteQuestion)
                }

                val answers = line.toAnswerEntityList()
                if(answers.isNotEmpty()) {
                    daoAnswers.deleteAnswersForQuestion(remoteQuestion.id)
                    daoAnswers.insertAll(answers)
                }
            }
        }
        sharedPrefsManager.saveLastSynced(TAG_PARISH_REGISTER)
    }

    companion object {
        private const val BORN_LIST_FILE_NAME = "quiz-19.tsv"
        private const val TAG_PARISH_REGISTER = "TAG_PARISH_REGISTER"
    }
}