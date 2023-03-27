package com.dev.dzikirkita.ui.todo_list

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.Todo
import com.dev.dzikirkita.databinding.ActivityTodoAddUpdateBinding
import com.dev.dzikirkita.util.DateHelper
import com.dev.dzikirkita.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
                binding.fabDelete.visibility = View.VISIBLE
                todo?.let { todo ->
                    binding.edtTitle.setText(todo.judul)
                    binding.edtDescription.setText(todo.deskripsi)
                }
                binding.fabDelete.setOnClickListener {
                    showDeleteDialog(todo as Todo)
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
                    binding.edtTitle.error = "Judul harus diisi!"
                }
                description.isEmpty() -> {
                    binding.edtDescription.error = "Deskripsi harus diisi!"
                }
                else -> {
                    todo.let { todo ->
                        todo?.judul = title
                        todo?.deskripsi = description
                    }
                    if (isEdit) {
                        todoViewModel.updateTodo(todo as Todo)
                    } else {
                        todo?.let { todo ->
                            todo.date = DateHelper.getCurrentDate()
                        }
                        todoViewModel.insertTodo(todo as Todo)
                    }
                    finish()
                }
            }
        }

        coloringIconFab()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun coloringIconFab() {
        val csl = ContextCompat.getColorStateList(this, R.color.white)
        val fabSubmit: FloatingActionButton = findViewById(R.id.fab_submit)
        val fabDelete: FloatingActionButton = findViewById(R.id.fab_delete)
        val drawableSubmit = fabSubmit.drawable?.mutate()
        val drawableDelete = fabDelete.drawable?.mutate()
        drawableSubmit?.let {
            DrawableCompat.setTintList(it, csl)
            fabSubmit.setImageDrawable(it)
        }
        drawableDelete?.let {
            DrawableCompat.setTintList(it, csl)
            fabDelete.setImageDrawable(it)
        }
    }

    private fun showDeleteDialog(todo: Todo) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle("Konfirmasi")
            setMessage("Apakah anda yakin ingin menghapus aktivitas ini?")
            setCancelable(false)
            setPositiveButton("Ya") { _, _ ->
                todoViewModel.deleteTodo(todo)
                finish()
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): TodoViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TodoViewModel::class.java]
    }

    companion object {
        const val EXTRA_TODO = "extra_todo"
    }
}