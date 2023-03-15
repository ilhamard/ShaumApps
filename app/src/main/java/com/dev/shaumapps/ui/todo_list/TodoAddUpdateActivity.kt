package com.dev.shaumapps.ui.todo_list

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.data.local.entity.Todo
import com.dev.shaumapps.databinding.ActivityTodoAddUpdateBinding
import com.dev.shaumapps.util.DateHelper
import com.dev.shaumapps.util.ViewModelFactory

class TodoAddUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoAddUpdateBinding

    private lateinit var todoViewModel: TodoViewModel
    private var isEdit = false
    private var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TODO, Todo::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_TODO)
        }

        if (todo != null) {
            isEdit = true
        } else {
            todo = Todo()
        }

        val actionBarTitle: String

        if (isEdit) {
            actionBarTitle = "Update Aktivitas"
            if (todo != null) {
                todo?.let { todo ->
                    binding.edtTitle.setText(todo.judul)
                    binding.edtDescription.setText(todo.deskripsi)
                }
            }
        } else {
            actionBarTitle = "Tambah Aktivitas"
        }

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = actionBarTitle

        todoViewModel = obtainViewModel(this)

        binding.fabSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = "Ga bole kosong atuh!"
                }
                description.isEmpty() -> {
                    binding.edtDescription.error = "Ga bole kosong ah!"
                }
                else -> {
                    todo.let { todo ->
                        todo?.judul = title
                        todo?.deskripsi = description
                    }
                    if (isEdit) {
                        todoViewModel.updateTodo(todo as Todo)
                        showToast("Diupdate lah!")
                    } else {
                        todo?.let { todo ->
                            todo.date = DateHelper.getCurrentDate()
                        }
                        todoViewModel.insertTodo(todo as Todo)
                        showToast("Ditambahkan hohoy!")
                    }
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): TodoViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TodoViewModel::class.java]
    }

    companion object {
        const val EXTRA_TODO = "extra_todo"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}