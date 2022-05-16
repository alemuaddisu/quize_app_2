package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import edu.miu.quizapp.utils.Utility

class ResultFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val backButton = view.findViewById<Button>(R.id.backButton)
        val reviewButton = view.findViewById<Button>(R.id.reviewButton)
        val yourScore = view.findViewById<TextView>(R.id.yourScoreTextView)
        val topScore = view.findViewById<TextView>(R.id.topScoreTextView)
        val correctAnswer = view.findViewById<TextView>(R.id.correctTextView)
        val wrongAnswer = view.findViewById<TextView>(R.id.wrongTextView)

        val util = context?.let { Utility(it) };
        var currentScore = arguments?.getInt("score")
        var userChoices = arguments?.getIntArray("userChoices")

        correctAnswer.text = "" + currentScore
        wrongAnswer.text = "" + (15 - currentScore!!)
        yourScore.text = "" + (currentScore * 5)
        var topScored = util?.getTopScore()
        if(currentScore!! > topScored!!){
            topScored = currentScore
            util?.setTopScore(topScored)
        }

        topScore.text = "" + topScored

        backButton.setOnClickListener{

            Navigation.findNavController(requireView()).navigate(R.id.action_resultFragment_to_homeFragment)
        }

        reviewButton.setOnClickListener{
            val bundle = bundleOf("userChoices" to userChoices)
            Navigation.findNavController(requireView()).navigate(R.id.action_resultFragment_to_reviewFragment,bundle)
        }


        return view
    }
}