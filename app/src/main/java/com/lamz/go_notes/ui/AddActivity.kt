package com.lamz.go_notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lamz.go_notes.domain.Notes
import com.lamz.go_notes.ui.viewmodel.NotesViewModel
import com.lamz.go_notes.ui.viewmodel.obtainViewModel
import com.lamz.go_notes.utils.Utils.getCurrentDate
import com.lamz.gonotes.R
import com.lamz.gonotes.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.add) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        notesViewModel = obtainViewModel(this)
        binding.apply {
            val textWatcher = object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.apply {
                        val isNotEmpty = edtTitle.text.isNotEmpty() || edtDesc.text.isNotEmpty()
                        if (isNotEmpty){
                            btnSave.visibility = View.VISIBLE
                            btnDelete.visibility = View.VISIBLE
                        }else{
                            btnSave.visibility = View.INVISIBLE
                            btnDelete.visibility = View.INVISIBLE
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            }
            edtTitle.addTextChangedListener(textWatcher)
            edtDesc.addTextChangedListener(textWatcher)

            btnSave.setOnClickListener {
                when{
                    edtTitle.text.isEmpty() || edtDesc.text.isEmpty() -> {
                        val date = getCurrentDate()
                        val title = edtTitle.text.toString()
                        val desc = edtDesc.text.toString()
                        val notes = Notes(date = date, title = title, desc = desc)
                        notesViewModel.insert(notes)
                        finish()
                    }
                    else -> {
                        val date = getCurrentDate()
                        val title = edtTitle.text.toString()
                        val desc = edtDesc.text.toString()
                        val notes = Notes(date = date, title = title, desc = desc)
                        notesViewModel.insert(notes)
                        finish()
                    }
                }
            }

            btnDelete.setOnClickListener {
                edtTitle.setText(getString(R.string.empty_text))
                edtDesc.setText(getString(R.string.empty_text))
                btnSave.visibility = View.INVISIBLE
                btnDelete.visibility = View.INVISIBLE
            }

            btnBack.setOnClickListener {
                finish()
            }
        }

    }
}