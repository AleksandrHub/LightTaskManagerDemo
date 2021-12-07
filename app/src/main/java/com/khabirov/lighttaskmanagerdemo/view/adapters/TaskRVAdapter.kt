package com.niww.lighttaskmanager.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.Task
import kotlinx.android.synthetic.main.task_list_recyclerview_item.view.*

class TaskRVAdapter(
    var tasks: ArrayList<Task>,
    private val taskListener: TaskItemListener
) :
    RecyclerView.Adapter<TaskRVAdapter.RecyclerItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_recyclerview_item, parent, false),
            taskListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class RecyclerItemViewHolder(
        view: View,
        private val taskListener: TaskItemListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            setBackgroundColor(task)
            itemView.tv_task_item_named.text = task.name
            itemView.tv_task_item_timing.text = task.timing
            itemView.tv_task_item_employer.text = task.employer
            itemView.tv_task_item_performer.text = task.performer

            itemView.btn_task_item_chat.setOnClickListener { taskListener.onChatClick(task) }
            itemView.setOnClickListener { taskListener.onTaskClick(task) }
        }

        private fun setBackgroundColor(task: Task) = when (task.state) {
            0 -> {
                itemView.cv_task_item_root.setBackgroundResource(R.color.doTaskBackground)
                itemView.btn_task_item_chat.setBackgroundResource(R.color.doTaskBackground)
                itemView.task_item_state_line.setBackgroundResource(R.color.red)
            }
            1 -> {
                itemView.cv_task_item_root.setBackgroundResource(R.color.doingTaskBackground)
                itemView.btn_task_item_chat.setBackgroundResource(R.color.doingTaskBackground)
                itemView.task_item_state_line.setBackgroundResource(R.color.yellow)
            }
            2 -> {
                itemView.cv_task_item_root.setBackgroundResource(R.color.doneTaskBackground)
                itemView.btn_task_item_chat.setBackgroundResource(R.color.doneTaskBackground)
                itemView.task_item_state_line.setBackgroundResource(R.color.green)
            }
            else -> {
                itemView.cv_task_item_root.setBackgroundResource(R.color.white)
                itemView.task_item_state_line.setBackgroundResource(R.color.white)
            }
        }
    }

}
