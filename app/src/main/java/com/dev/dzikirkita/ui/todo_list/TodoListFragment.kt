package com.dev.dzikirkita.ui.todo_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.Todo
import com.dev.dzikirkita.databinding.FragmentTodoListBinding
import com.dev.dzikirkita.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment(), TodoAdapter.OnItemClickListener {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel
    private lateinit var rvAdapter: TodoAdapter
    private lateinit var rvAdapterChecked: TodoAdapter
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Aktivitas"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAdd = requireActivity().findViewById(R.id.fab_add_todo)

        coloringIconFab()

        viewModel = obtainViewModel(this)

        fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), TodoAddUpdateActivity::class.java))
        }

        viewModel.getAllTodos().observe(viewLifecycleOwner) {
            rvAdapter.setListTodo(it)
        }

        viewModel.getTodoChecked().observe(viewLifecycleOwner) {
            rvAdapterChecked.setListTodo(it)
        }

        setRecyclerView()
        setRecyclerViewChecked()
    }

    override fun onResume() {
        super.onResume()

        fabAdd.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        fabAdd.visibility = View.GONE
    }

    private fun coloringIconFab() {
        val csl = ContextCompat.getColorStateList(requireContext(), R.color.white)
        val fab = fabAdd
        val drawable = fab.drawable?.mutate()
        drawable?.let {
            DrawableCompat.setTintList(it, csl)
            fab.setImageDrawable(it)
        }
    }

    private fun setRecyclerView() {
        rvAdapter = TodoAdapter(this)
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = rvAdapter
        }
    }

    private fun setRecyclerViewChecked() {
        rvAdapterChecked = TodoAdapter(this)
        binding.rvTodosDone.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = rvAdapterChecked
        }
    }

    private fun obtainViewModel(fragment: Fragment): TodoViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[TodoViewModel::class.java]
    }

    override fun onCheckBoxClick(todo: Todo, isChecked: Boolean) {
        viewModel.onTodoCheckedChanged(todo, isChecked)
    }
}