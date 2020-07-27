package br.com.example.hamburgos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Order
import br.com.example.hamburgos.request.RequestPresenter
import kotlinx.android.synthetic.main.activity_promotion.*
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_request.textEmptyList

class RequestActivity : AppCompatActivity() {

    private var adapter: RequestAdapter? = null
    private var presenter: RequestPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        presenter = RequestPresenter(this)
        adapter = RequestAdapter(this)

        val layoutParams = LinearLayoutManager(this)
        listRequest.layoutManager = layoutParams
        listRequest.adapter = adapter

        presenter!!.getRequests()
    }

    fun setContent(orders: List<Order>?) {
        if (orders == null || orders.isEmpty()) {
            textEmptyList.visibility = View.VISIBLE
            listRequest.visibility = View.GONE
        } else {
            textEmptyList.visibility = View.GONE
            listRequest.visibility = View.VISIBLE
            adapter?.setContent(orders)
        }
    }

    fun setContent(order: Order) {
        textEmptyList.visibility = View.GONE
        listRequest.visibility = View.VISIBLE
        adapter?.addContent(order)
    }

    fun setError(errorMessage: String?) {
        val builder = AlertDialog.Builder(this@RequestActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }
}
