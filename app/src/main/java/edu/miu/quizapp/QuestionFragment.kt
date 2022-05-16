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
import kotlinx.coroutines.launch

class QuestionFragment : BaseFragment()  {

    lateinit var optionsRadioGroup: RadioGroup

    var currentIndex =0
    var currentScore = 0

    var userChoices = IntArray(15){i -> -1}

    lateinit var questions: List<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val allProgressBar = view.findViewById<ProgressBar>(R.id.allProgressBar)
        val correctProgressBar = view.findViewById<ProgressBar>(R.id.correctProgressBar)

        optionsRadioGroup = view.findViewById<RadioGroup>(R.id.optionsRadioGroup)

        val skipButton = view.findViewById<Button>(R.id.skipButton)
        val nextButton = view.findViewById<Button>(R.id.nextButton)

        launch {
            context?.let {
                questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                checkNextPage()
                postQuestion(view)
            }
        }
        nextButton.setOnClickListener {
            if(userChoices[currentIndex]>-1)
                updateScore(userChoices[currentIndex])
            correctProgressBar.progress = currentScore
            allProgressBar.progress = currentIndex
            postQuestion(view)
        }
        skipButton.setOnClickListener {
           Navigation.findNavController(requireView()).navigate(R.id.action_questionFragment_to_homeFragment2)
        }

        optionsRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener {
                radioGroup, i ->
                    (optionsRadioGroup.getChildAt(0) as RadioButton).setBackgroundColor(Color.GRAY)
                    (optionsRadioGroup.getChildAt(1) as RadioButton).setBackgroundColor(Color.GRAY)
                    (optionsRadioGroup.getChildAt(2) as RadioButton).setBackgroundColor(Color.GRAY)
                    (optionsRadioGroup.getChildAt(3) as RadioButton).setBackgroundColor(Color.GRAY)
                    if(i>0) {
                        view.findViewById<RadioButton>(i).setBackgroundColor(Color.BLUE)

                        when (i) {
                            R.id.option1RadioButton -> userChoices[currentIndex] = 0
                            R.id.option2RadioButton -> userChoices[currentIndex] = 1
                            R.id.option3RadioButton -> userChoices[currentIndex] = 2
                            R.id.option4RadioButton -> userChoices[currentIndex] = 3
                        }

                    }
        })
        return view
    }

    fun postQuestion(view:View){
        if(checkNextPage()) {
            val questionTextView = view.findViewById(R.id.questionTextView) as TextView
            questionTextView.text = ""+(currentIndex+1)+"] "+questions[currentIndex].question

            optionsRadioGroup.clearCheck()
            (optionsRadioGroup.getChildAt(0) as RadioButton).text = "A ) "+ questions[currentIndex].optionA
            (optionsRadioGroup.getChildAt(1) as RadioButton).text = "B ) "+ questions[currentIndex].optionB
            (optionsRadioGroup.getChildAt(2) as RadioButton).text = "C ) "+ questions[currentIndex].optionC
            (optionsRadioGroup.getChildAt(3) as RadioButton).text = "D ) "+ questions[currentIndex].optionD

            currentIndex++;
        }

    }

    fun checkNextPage(): Boolean {
        if(currentIndex +6 >= questions.size){
            val bundle = bundleOf("score" to currentScore,"userChoices" to userChoices)
            Navigation.findNavController(requireView()).navigate(R.id.action_questionFragment_to_resultFragment,bundle)
            return false
        }
        return true
    }

    fun updateScore(currentChose:Int){
        if(questions[currentIndex-1].correctAnswer == ""+currentChose){
            currentScore++
        }
    }


}