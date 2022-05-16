package edu.miu.quizapp
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.miu.quizapp.db.Question
import kotlin.coroutines.coroutineContext

class RecyclerAdapter(private val questionList:List<Question>, private val userChoices: IntArray): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//class RecyclerAdapter( private val userChoices: IntArray): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.reusable_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val question = questionList.get(i)
        val options = arrayOf(question.optionA,question.optionB,question.optionC,question.optionD)

        viewHolder.itemQuestion.text = "Q"+ (i+1) + ") " + question.question
        viewHolder.itemUserAnswer.text = "Your Answer: "+options[userChoices.get(i).toInt()]
        viewHolder.itemCorrectAnswer.text = "Correct Answer: " + options[Integer.valueOf(question.correctAnswer)]



    }

    override fun getItemCount(): Int {
//        return questionList.size
        return questionList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemQuestion: TextView
        var itemUserAnswer: TextView
        var itemCorrectAnswer: TextView

        init {
            itemQuestion = itemView.findViewById(R.id.questionTextView)
            itemUserAnswer = itemView.findViewById(R.id.userAnswerTextView)
            itemCorrectAnswer = itemView.findViewById(R.id.correctAnswerTextView)

        }
    }
}