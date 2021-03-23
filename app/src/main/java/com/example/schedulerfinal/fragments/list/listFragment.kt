package com.example.schedulerfinal.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulerfinal.R
import com.example.schedulerfinal.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class listFragment : Fragment() {
    private lateinit var  mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        mTaskViewModel.readAllTask.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllTasks()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTasks() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mTaskViewModel.deleteAllTasks()
            Toast.makeText(requireContext(), "Successfully deleted every task!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete all tasks?")
        builder.create().show()
    }

}