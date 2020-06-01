package br.com.devteam.relative.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.devteam.relative.R
import br.com.devteam.relative.interfaces.LoadingActivity
import kotlinx.android.synthetic.main.activity_host.*

class StartActivity : AppCompatActivity(),
    LoadingActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val contentView: View = findViewById(R.id.start_activity_fragment)
    }

    override
    fun showLoading(){
        pb_main_loading.visibility = View.VISIBLE;
    }

    override
    fun hideLoading(){
        pb_main_loading.visibility = View.INVISIBLE;
    }
}
