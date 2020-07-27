package br.com.example.hamburgos.snacklist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Snack
import br.com.example.hamburgos.ui.PromotionActivity
import br.com.example.hamburgos.ui.RequestActivity
import br.com.example.hamburgos.ui.SnackDetailActivity
import br.com.example.hamburgos.util.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class SnackListActivity : AppCompatActivity(), SnackListContract.View, NavigationView.OnNavigationItemSelectedListener {

    private var adapter: SnackListAdapter? = null
    private var presenter: SnackListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = SnackListPresenter(this)
        adapter = SnackListAdapter(this)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        adapter?.setClickListener(object : SnackItemClickListener() {
            override fun onClick(type: String, snack: Snack): View.OnClickListener {
                return View.OnClickListener {
                    if (Constants.ADD == type) {
                        val builder = AlertDialog.Builder(this@SnackListActivity)
                        builder.setTitle(getString(R.string.confirmation) + " - " + snack.name)
                        builder.setMessage(snack.ingredientListString)
                        builder.setPositiveButton(R.string.label_yes) { _, _ -> presenter?.addRequest(snack) }
                        builder.setNegativeButton(R.string.label_no) { dialogInterface, _ -> dialogInterface.dismiss() }
                        builder.show()
                    } else if (Constants.CUSTOM == type) {
                        val intent = Intent(this@SnackListActivity, SnackDetailActivity::class.java)
                        intent.putExtra(Constants.SNACK, snack.id)
                        startActivity(intent)
                    }
                }
            }
        })

        val layoutParams = LinearLayoutManager(this)
        listSnacks.layoutManager = layoutParams
        listSnacks.adapter = adapter

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
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_promotions) {
            val intent = Intent(this@SnackListActivity, PromotionActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_shopper) {
            val intent = Intent(this@SnackListActivity, RequestActivity::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setContent(snacks: List<Snack>) {
        adapter?.setContent(snacks)
    }

    override fun setError(errorMessage: String?) {
        val builder = AlertDialog.Builder(this@SnackListActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.dismiss() }
        builder.show()
    }

    override fun requestConfirmation() {
        val intent = Intent(this@SnackListActivity, RequestActivity::class.java)
        startActivity(intent)
    }

}
