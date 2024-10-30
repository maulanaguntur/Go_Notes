package com.lamz.go_notes.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamz.go_notes.domain.Notes
import com.lamz.go_notes.ui.viewmodel.NotesViewModel
import com.lamz.go_notes.ui.viewmodel.obtainViewModel
import com.lamz.gonotes.R
import com.lamz.gonotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnAdd.setOnClickListener {
                intentActivity(this@MainActivity, AddActivity::class.java)
            }
            btnSearch.setOnClickListener {
                intentActivity(this@MainActivity, SearchActivity::class.java)
            }
        }

        showRecyclerView()
    }

    private fun showRecyclerView(){
        viewModel = obtainViewModel(this@MainActivity)
        with(binding){
            viewModel.allNotes.observe(this@MainActivity){notes ->
                val adapter = NotesAdapter(notes)
                rvNotes.adapter = adapter
                rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)
                val dividerDrawable = AppCompatResources.getDrawable(this@MainActivity, R.drawable.custom_divider)
                if (dividerDrawable != null){
                    val dividerItemDecoration = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
                    dividerItemDecoration.setDrawable(dividerDrawable)
                    rvNotes.addItemDecoration(dividerItemDecoration)
                }

                adapter.setOnItemClick(object : NotesAdapter.OnItemClick{
                    override fun onItemClicked(notes: Notes) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("notes", notes)
                        startActivity(intent)
                    }

                })
            }
        }
    }

    private fun <T> intentActivity(context: Context, targetActivity : Class<T>){
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }
}