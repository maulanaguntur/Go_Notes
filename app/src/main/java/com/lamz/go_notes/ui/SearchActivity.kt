package com.lamz.go_notes.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
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
import com.lamz.gonotes.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.search) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            searchBar.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s.toString()
                    searchNotes(query)
                }

                override fun afterTextChanged(s: Editable?) {
                    val query = s.toString()
                    searchNotes(query)
                }

            })
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            binding.search.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun searchNotes(query : String){
        viewModel = obtainViewModel(this)
        if (query.isEmpty()){
            showRecyclerView(emptyList())
        }else{
            viewModel.searchNotes(query).observe(this){note ->
                showRecyclerView(note)
            }
        }
    }

    private fun showRecyclerView(notes : List<Notes>){
        viewModel = obtainViewModel(this@SearchActivity)
        with(binding){
            val adapter = NotesAdapter(notes)
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this@SearchActivity)
            val dividerDrawable = AppCompatResources.getDrawable(this@SearchActivity, R.drawable.custom_divider)
            if (dividerDrawable != null){
                val dividerItemDecoration = DividerItemDecoration(this@SearchActivity, LinearLayoutManager.VERTICAL)
                dividerItemDecoration.setDrawable(dividerDrawable)
                rvNotes.addItemDecoration(dividerItemDecoration)
            }

            adapter.setOnItemClick(object : NotesAdapter.OnItemClick{
                override fun onItemClicked(notes: Notes) {
                    val intent = Intent(this@SearchActivity, DetailActivity::class.java)
                    intent.putExtra("notes", notes)
                    startActivity(intent)
                }

            })
        }
    }
}