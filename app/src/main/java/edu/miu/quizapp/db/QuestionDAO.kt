package edu.miu.quizapp.db

import androidx.room.*

@Dao
interface QuestionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestions(question: List<Question>)

    @Query("SELECT * FROM question ORDER BY id")
    suspend fun getAllQuestions(): List<Question>


    @Query("DELETE FROM question WHERE 1=1")// TODO: tobe delted 
    suspend fun deleteAllQuestions()
}
