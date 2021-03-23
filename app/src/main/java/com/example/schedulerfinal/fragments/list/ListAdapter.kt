package com.example.schedulerfinal.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulerfinal.R
import com.example.schedulerfinal.fragments.list.listFragmentDirections.Companion.actionListFragmentToUpdateFragment
import com.example.schedulerfinal.model.Task
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter :RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var taskList = emptyList<Task>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.itemView.show_date.text = currentItem.date.toString()
        holder.itemView.show_title.text = currentItem.title
        holder.itemView.show_description.text = currentItem.description

        holder.itemView.rowLayout.setOnClickListener {
            val action = listFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}