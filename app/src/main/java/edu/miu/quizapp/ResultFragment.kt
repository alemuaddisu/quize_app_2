package edu.miu.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import edu.miu.quizapp.utils.BaseFragment

class ResultFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val backButton = view.findViewById<Button>(R.id.backButton)
        var kir = true

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