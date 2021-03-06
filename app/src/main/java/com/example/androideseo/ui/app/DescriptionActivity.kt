package com.example.androideseo.ui.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androideseo.R
import com.example.androideseo.data.LocalPreferences
import com.example.androideseo.databinding.ActivityDescriptionBinding
import com.example.androideseo.databinding.ActivitySensorBinding
import com.example.androideseo.ui.fragment.HistoriqueInfoActivity

/**
 * TODO
 * Classe correspondant a l'activité situé dans /mainActivity/parametre/a_propos/
 */
class DescriptionActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDescriptionBinding // <-- Référence à notre ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setTitle(R.string.Description)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.btnapi.setOnClickListener {
            startActivity(DescriptionAPIActivity.getStartIntent(this@DescriptionActivity))
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DescriptionActivity::class.java)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
