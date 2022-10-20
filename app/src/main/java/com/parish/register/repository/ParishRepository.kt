package com.parish.register.repository

import com.parish.register.db.dao.DaoBorn
import com.parish.register.nineteen.common.SharedPrefsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParishRepository @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val daoBorn: DaoBorn
) {

    /*fun getQuiz(category: Category?): Flow<Resource<List<Question>>> {
        return FirebaseHelper.loadFileData(
            BOOKS_LIST_FILE_NAME,
            query = { daoQuestions.getAllQuestions().map { it.toQuestion() } },
            shouldFetch = { SyncHelper.isSyncNeed(sharedPrefsManager.getLastSynced(TAG_QUIZ)) },
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
        sharedPrefsManager.saveLastSynced(TAG_QUIZ)
    }*/

    companion object {
        private const val BOOKS_LIST_FILE_NAME = "quiz-19.tsv"
        private const val TAG_QUIZ = "TAG_QUIZ"
    }
}