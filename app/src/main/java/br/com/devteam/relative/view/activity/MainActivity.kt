package br.com.devteam.relative.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.devteam.relative.R
import br.com.devteam.relative.interfaces.LoadingActivity
import br.com.devteam.relative.viewmodel.UserProfileViewModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    LoadingActivity {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val userProfileViewModel: UserProfileViewModel by lazy {
        ViewModelProvider(this).get(UserProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mountMenu()
        userProfileViewModel.userProfile.observe(this, Observer {
            if (it != null) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.action_nav_home_to_nav_profile)
            }
        })
    }

    fun mountMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //  val fab: FloatingActionButton = findViewById(R.id.fab)
        //  fab.setOnClickListener { view ->
        //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //          .setAction("Action", null).show()
        //  }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_category
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //  userProfileViewModel.getUserProfileImageUri()

        //    if (it!!.success) {
        //        val tmp =
        //            navView.getHeaderView(0).findViewById<ImageView>(R.id.iv_nav_header_profile)
        //        Picasso.get().load(it.data).error(R.drawable.ic_user)
        //            .into(tmp)
        //    } else {
        //        Toast.makeText(
        //            this@MainActivity.applicationContext,
        //            it.userMessage,
        //            Toast.LENGTH_LONG
        //        ).show()
        //     }
        userProfileViewModel.userProfileImageURL.observe(this, Observer { uri ->
            val tmp = navView.getHeaderView(0)
                .findViewById<ImageView>(R.id.iv_nav_header_profile)
            Picasso.get().load(uri).error(R.drawable.ic_user)
                .into(tmp)
        })

        drawerLayout.addDrawerListener(object : DrawerListener {
            override
            fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //Called when a drawer's position changes.
            }

            override
            fun onDrawerOpened(drawerView: View) {
                //Called when a drawer has settled in a completely open state.
                //The drawer is interactive at this point.
                // If you have 2 drawers (left and right) you can distinguish
                // them by using id of the drawerView. int id = drawerView.getId();
                // id will be your layout's id: for example R.id.left_drawer
                val tmp = navView.getHeaderView(0)
                    .findViewById<ImageView>(R.id.iv_nav_header_profile)
                Picasso.get().load(userProfileViewModel.userProfileImageURL.value)
                    .error(R.drawable.ic_user)
                    .into(tmp)
            }

            override
            fun onDrawerClosed(drawerView: View) {
                // Called when a drawer has settled in a completely closed state.
            }

            override fun onDrawerStateChanged(newState: Int) {
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.

            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    override
    fun showLoading(){
        pb_main_loading.visibility = View.VISIBLE;
    }

    override
    fun hideLoading(){
        pb_main_loading.visibility = View.INVISIBLE;
    }
}
