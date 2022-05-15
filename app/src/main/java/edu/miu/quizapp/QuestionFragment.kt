package edu.miu.quizapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import edu.miu.quizapp.db.Question
import edu.miu.quizapp.db.QuestionDatabase
import edu.miu.quizapp.utils.BaseFragment
import edu.miu.quizapp.utils.toast
import kotlinx.coroutines.launch

class QuestionFragment : BaseFragment()  {

    private lateinit var tvQuestion: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var questions: List<Question>
    private var qstnIdx = 0
    private var score = 0
    private lateinit var selectedChoice: String
    private lateinit var currentQuestion: Question
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        val skipButton = view.findViewById<Button>(R.id.skipButton)
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        val allProgressBar = view.findViewById<ProgressBar>(R.id.allProgressBar)
        val correctProgressBar = view.findViewById<ProgressBar>(R.id.correctProgressBar)
       
        tvQuestion = view.findViewById(R.id.tv_question)
        launch {
            context?.let {
                questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                changeQuestion(view)
            }
        }
        nextButton.setOnClickListener {
            evaluateAnswer(selectedChoice)
            correctProgressBar.progress = score;
            allProgressBar.progress = qstnIdx;
            changeQuestion(view)
        }
        skipButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_questionFragment_to_homeFragment2)
        }
        optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup)
        optionsRadioGroup.setOnCheckedChangeListener(this::handler)
        return view
    }


    private fun changeQuestion(view: View) {
        if(qstnIdx < questions.size){ // TODO: remove add value to go to all page
            currentQuestion = questions[qstnIdx]
            tvQuestion.text = currentQuestion.question
            val optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup) as RadioGroup
            val questionChoices = listOf(currentQuestion.optionA,currentQuestion.optionB,currentQuestion.optionC,currentQuestion.optionD)
            for (i in 0 until optionsRadioGroup.childCount) {
                (optionsRadioGroup.getChildAt(i) as RadioButton).text = questionChoices[i]
            }
            qstnIdx++
            optionsRadioGroup.clearCheck()
        }
        else{
            val bundle = bundleOf("score" to score)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_questionFragment_to_resultFragment,bundle)
        }
    }

    private fun handler(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.option1RadioButton -> selectedChoice = "a"
            R.id.option2RadioButton -> selectedChoice = "b"
            R.id.option3RadioButton -> selectedChoice = "c"
            R.id.option4RadioButton -> selectedChoice = "d"
        }
    }

    private fun evaluateAnswer(ans: String){
        if(currentQuestion.correctAnswer == ans){
            score++
        }

    }



}