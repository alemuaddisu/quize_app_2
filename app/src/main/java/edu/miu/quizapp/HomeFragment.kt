package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.miu.quizapp.db.Question
import edu.miu.quizapp.db.QuestionDatabase
import edu.miu.quizapp.utils.BaseFragment
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment()  {

    private lateinit var questions: List<Question>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val startButton = view.findViewById<Button>(R.id.startButton)
        val scoreTextView = view.findViewById<TextView>(R.id.scoreTextView)
        val util = context?.let { Utility(it) };
        val topScore = util?.getTopScore()
        scoreTextView.text = ""+topScore
        launch {
            context?.let {
                questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
            }
        }
        startButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_questionFragment)
        }
        return view
    }


}