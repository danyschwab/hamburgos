package br.com.example.hamburgos.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.com.example.hamburgos.R
import br.com.example.hamburgos.listener.SnackItemClickListener
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.request.Presenter
import br.com.example.hamburgos.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var adapter: SnacksAdapter? = null
    private var presenter: Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = Presenter(this)
        adapter = SnacksAdapter(this)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        adapter!!.setClickListener(object : SnackItemClickListener() {
            override fun onClick(type: String, snack: Snack): View.OnClickListener {
                return View.OnClickListener {
                    if (Constants.ADD == type) {
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle(getString(R.string.confirmation) + " - " + snack.name)
                        builder.setMessage(snack.ingredientListString)
                        builder.setPositiveButton(R.string.label_yes) { dialogInterface, i -> presenter!!.addRequest(snack) }
                        builder.setNegativeButton(R.string.label_no) { dialogInterface, i -> dialogInterface.dismiss() }
                        builder.show()
                    } else if (Constants.CUSTOM == type) {
                        val intent = Intent(this@MainActivity, SnackDetailActivity::class.java)
                        intent.putExtra(Constants.SNACK, snack.id)
                        startActivity(intent)
                    }
                }
            }
        })

        val layoutParams = LinearLayoutManager(this)
        list_snacks.layoutManager = layoutParams
        list_snacks.adapter = adapter

        presenter!!.getSnacks()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_promotions) {
            val intent = Intent(this@MainActivity, PromotionActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_shopper) {
            val intent = Intent(this@MainActivity, RequestActivity::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun setContent(snacks: List<Snack>) {
        adapter!!.setContent(snacks)
    }

    fun setError(errorMessage: String?) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.show()
    }

    fun requestConfirmation() {
        val intent = Intent(this@MainActivity, RequestActivity::class.java)
        startActivity(intent)
    }

}
