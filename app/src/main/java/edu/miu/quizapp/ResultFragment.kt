package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import edu.miu.quizapp.utils.BaseFragment
import java.lang.Integer.max

class ResultFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val backButton = view.findViewById<Button>(R.id.backButton)
        var kir = true
        val yourScore = view.findViewById<TextView>(R.id.yourScoreTextView)
        val topScore = view.findViewById<TextView>(R.id.topScoreTextView)

        val util = context?.let { Utility(it) };
        var currentScore = arguments?.getInt("score")
        yourScore.text = "" + currentScore
        var topScored = util?.getTopScore()
        if(currentScore!! > topScored!!){
            topScored = currentScore
            util?.setTopScore(topScored)
        }

        topScore.text = "" + topScored

        backButton.setOnClickListener{
        if(kir){
                kir=false
                Toast.makeText(context,"what eveadf",Toast.LENGTH_LONG)
            }
            else
            Navigation.findNavController(requireView())
                .navigate(R.id.action_resultFragment_to_homeFragment)
        }
        return view
    }
}