package com.example.schedulerfinal.fragments.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.schedulerfinal.R
import com.example.schedulerfinal.model.Task
import com.example.schedulerfinal.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.util.*

class addFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        view.select_date.setOnClickListener {
            clickDatePicker(view)
        }
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        view.add_task.setOnClickListener { insertDataToDatabase() }
        return view
    }

    private fun insertDataToDatabase(){
        val title = add_title.text.toString()
        val description = add_description.text.toString()
        val date = selected_date.text.toString()

        if(inputCheck(title, description, date)){
        val task =
            Task(0, title, description, date)

        mTaskViewModel.addTask(task)
        Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, description: String, date: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description) )
    }

    fun clickDatePicker(view: View){
        val myCalendar = Calendar.getInstance();
        val year = myCalendar.get(Calendar.YEAR);
        val month = myCalendar.get(Calendar.MONTH);
        val day = myCalendar.get(Calendar.DAY_OF_MONTH);
        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener
        { view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(context, "The date chosen was: " + (selectedMonth + 1) + "/" + selectedDayOfMonth + "/"+ selectedYear, Toast.LENGTH_LONG).show()
            val selectedDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = simpleDateFormat.parse(selectedDate)
            selected_date.setText(selectedDate)
        }, year, month, day)
        dpd.show()
    }


}