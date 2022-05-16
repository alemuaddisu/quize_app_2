package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import edu.miu.quizapp.utils.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var titleTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        titleTextView = view.findViewById(R.id.titleTextView)

        titleTextView.postDelayed({
            val utility = context?.let { Utility(it) }
            if(utility?.isFirstRun() == true)
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_welcomeFragment)
            else
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment)
        }, 2000)

        return view
    }
}