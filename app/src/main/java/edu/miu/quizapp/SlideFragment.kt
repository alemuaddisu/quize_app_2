package edu.miu.quizapp

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.miu.quizapp.db.QuestionDatabase
import edu.miu.quizapp.utils.Utility
import kotlinx.coroutines.launch


class SlideFragment : BaseFragment() {

    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dotsLayout: LinearLayout? = null
    private lateinit var layouts: IntArray
    private var btnSkip: Button? = null
    private var btnNext: Button? = null
    private var cancelButton: FloatingActionButton? = null
    private var nextButton: FloatingActionButton? = null
    private var prevButton: FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slide, container, false)

        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        viewPager = view.findViewById(R.id.view_pager) as ViewPager

        cancelButton = view.findViewById(R.id.cancelFloatingActionButton) as FloatingActionButton
        prevButton = view.findViewById(R.id.prevFloatingActionButton) as FloatingActionButton
        nextButton = view.findViewById(R.id.nextFloatingActionButton) as FloatingActionButton


        layouts = intArrayOf(
            R.layout.fragment_slide1,
            R.layout.fragment_slide2,
            R.layout.fragment_slide3
        )


        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager?.adapter = myViewPagerAdapter

        nextButton!!.setOnClickListener{
            val currentPage = viewPager!!.currentItem
            if (currentPage < layouts.size - 1) {
                viewPager?.currentItem = currentPage + 1
                prevButton?.isEnabled = true;
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_welcomeFragment_to_homeFragment)
            }
        }
        prevButton!!.setOnClickListener{
            val currentPage = viewPager!!.currentItem
            if (currentPage > 0) {
                viewPager?.currentItem = currentPage - 1
            }
            if(currentPage <= 1){
                prevButton?.isEnabled = false;
            }

        }
        cancelButton!!.setOnClickListener{
            Navigation.findNavController(requireView())
                .navigate(R.id.action_welcomeFragment_to_homeFragment)
        }

        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                when(position){
                    layouts.size-1 -> {
                        nextButton?.isEnabled = false;
                    }
                    0 -> {
                        prevButton?.isEnabled = false;
                    }
                    1 -> {
                        prevButton?.isEnabled = true;

                        nextButton?.isEnabled = true;
                    }
                }
            }


            override fun onPageScrollStateChanged(state: Int) {
            }

        })


          launch {
            context?.let {
                val utility = Utility(it)
                val questions = utility.getDataFromJson("questions")
                QuestionDatabase(it)
                    .getQuestionDao().addQuestions(questions)
                utility.setRun()

            }
        }
        return view

}

 inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val view: View? = layoutInflater?.inflate(layouts[position], container, false)
            container.addView(view)
            return view!!
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

}