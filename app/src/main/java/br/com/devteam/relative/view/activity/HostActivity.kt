package br.com.devteam.relative.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.devteam.relative.R
import br.com.devteam.relative.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val contentView: View = findViewById(R.id.fragment)
        contentView.setOnClickListener {
            hideKeyboard(this@HostActivity)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment.childFragmentManager.fragments.forEach(){
//            if(resultCode == R.integer.google_signIn_RC){
//                if()
//            }
            it.onActivityResult(requestCode, resultCode, data)
        }
//        for (fragment in supportFragmentManager.fragments) {
//            fragment.onActivityResult(requestCode, resultCode, data)
//        }
    }

    fun showLoading(){
        pb_main_loading.visibility = View.VISIBLE;
    }

    fun hideLoading(){
        pb_main_loading.visibility = View.INVISIBLE;
    }
}
