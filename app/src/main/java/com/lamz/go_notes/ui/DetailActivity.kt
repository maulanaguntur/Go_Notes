package com.lamz.go_notes.ui

import android.os.Build
import android.os.Build.VERSION
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
import com.lamz.go_notes.utils.Utils
import com.lamz.gonotes.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.detail) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = obtainViewModel(this)

        val notes : Notes? = if(VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("notes", Notes::class.java)
        }else{
            intent.getParcelableExtra("notes")
        }

        notes?.let { note ->
            binding.apply {
                tvDate.text = note.date
                edtTitle.setText(note.title)
                edtDesc.setText(note.desc)


                val textWatcher = object : TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        btnSave.visibility = View.VISIBLE
                        btnSave.setOnClickListener {
                            val id = note.id
                            val date = Utils.getCurrentDate()
                            val title = edtTitle.text.toString()
                            val desc = edtDesc.text.toString()
                            val updatedNotes = Notes(id, date, title, desc)
                            viewModel.update(updatedNotes)
                            finish()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                }

                edtTitle.addTextChangedListener(textWatcher)
                edtDesc.addTextChangedListener(textWatcher)

                btnDelete.setOnClickListener {
                    viewModel.delete(note)
                    finish()
                }

                btnBack.setOnClickListener {
                    finish()
                }
            }
        }
    }
}