package edu.miu.quizapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import edu.miu.quizapp.db.Question
import java.io.IOException

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Utility(val context: Context) {

    private val preferance: SharedPreferences =context.getSharedPreferences("preference",0);
    private lateinit var editor: SharedPreferences.Editor


    fun setTopScore(score:Int){
            editor = preferance.edit()
            editor.putInt("score",score)
            editor.commit();
        }
        fun getTopScore():Int{
            return preferance.getInt("score",0)
        }

        fun setRun(){
            editor = preferance.edit()
            editor.putBoolean("firstRun",false)
            editor.commit();
        }
        fun isFirstRun():Boolean{
            return preferance.getBoolean("firstRun",true)
        }
        fun toast(message: String) {
            Toast.makeText(this.context,message, Toast.LENGTH_SHORT).show()
        }

    //Json utility

    fun getDataFromJson(fileName: String): List<Question> {

        val jsonFileString = getJsonDataFromAsset("$fileName.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(jsonFileString, listType)
    }

    //private
    private fun getJsonDataFromAsset( fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}
