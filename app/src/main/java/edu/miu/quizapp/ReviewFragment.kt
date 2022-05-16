package edu.miu.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.miu.quizapp.db.QuestionDatabase
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private var layoutManager: RecyclerView.LayoutManager? = null
private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReviewFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        val homeButton = view.findViewById<Button>(R.id.homeButton)

        homeButton.setOnClickListener{
            Navigation.findNavController(requireView()).navigate(R.id.action_reviewFragment_to_homeFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val userChoices = arguments?.getIntArray("userChoices")
        launch {
            context?.let {
                val questions = QuestionDatabase(it).getQuestionDao().getAllQuestions()
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = RecyclerAdapter(questions, userChoices!!)
                }
            }
        }
    }

}