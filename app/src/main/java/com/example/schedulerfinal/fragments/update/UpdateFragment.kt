package com.example.schedulerfinal.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schedulerfinal.R
import com.example.schedulerfinal.model.Task
import com.example.schedulerfinal.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        view.update_title.setText(args.currentTask.title)
        view.update_description.setText(args.currentTask.description)
        view.update_selected_date.setText(args.currentTask.date.toString())
        view.update_select_date.setOnClickListener {
            clickDatePicker(view)
        }
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        view.update_task.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)



        return view
    }

    private fun updateItem(){
        val title = update_title.text.toString()
        val description = update_description.text.toString()
        val date = update_selected_date.text.toString()


        if(inputCheck(title, description, date)){
            val updatedTask = Task(args.currentTask.id, title, description, date)

            mTaskViewModel.updateTask(updatedTask)

            Toast.makeText(requireContext(), "Task Updated successfully!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
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
            update_selected_date.setText(selectedDate)
        }, year, month, day)
        dpd.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "${args.currentTask.title} Successfully removed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete ${args.currentTask.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.title}")
        builder.create().show()
    }

}