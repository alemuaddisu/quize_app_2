package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.miu.quizapp.utils.BaseFragment
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)
        val scoreTextView = view.findViewById<TextView>(R.id.scoreTextView)

        launch {
            context?.let {
                val util =  Utility(it)
                val topScore = util?.getTopScore()
                scoreTextView.text = ""+topScore
            }
        }

        startButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_questionFragment)
        }

        return view
    }


}