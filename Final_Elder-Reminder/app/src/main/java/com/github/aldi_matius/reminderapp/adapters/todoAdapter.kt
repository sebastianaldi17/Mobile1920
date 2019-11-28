package com.github.aldi_matius.reminderapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.aldi_matius.reminderapp.R
import com.github.aldi_matius.reminderapp.data.Task
import kotlinx.android.synthetic.main.task_item.view.*

class todoAdapter : ListAdapter<Task, todoAdapter.TaskContainer>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.duetime == newItem.duetime
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskContainer {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskContainer(itemView)
    }

    override fun onBindViewHolder(holder: TaskContainer, position: Int) {
        val currentTask: Task = getItem(position)

        holder.textViewTitle.text = currentTask.title
        holder.textViewDescription.text = currentTask.description
        holder.textViewDueTime.text = currentTask.duetime
        holder.textViewDueDate.text = currentTask.duedate
    }

    fun getTaskIndex(position: Int): Task {
        return getItem(position)
    }

    inner class TaskContainer(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewDescription: TextView = itemView.text_view_description
        var textViewDueTime: TextView = itemView.text_view_duetime
        var textViewDueDate: TextView = itemView.text_view_duedate
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
