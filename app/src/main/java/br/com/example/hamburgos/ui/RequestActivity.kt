package br.com.example.hamburgos.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.example.hamburgos.R
import br.com.example.hamburgos.model.Request
import br.com.example.hamburgos.request.RequestPresenter
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    private var adapter: RequestAdapter? = null
    private var presenter: RequestPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        presenter = RequestPresenter(this)
        adapter = RequestAdapter(this)

        val layoutParams = LinearLayoutManager(this)
        list_request.layoutManager = layoutParams
        list_request.adapter = adapter

        presenter!!.getRequests()
    }

    fun setContent(requests: List<Request>?) {
        if (requests == null || requests.isEmpty()) {
            text_empty_list.visibility = View.VISIBLE
            list_request.visibility = View.GONE
        } else {
            text_empty_list.visibility = View.GONE
            list_request.visibility = View.VISIBLE
            adapter!!.setContent(requests)
        }
    }

    fun setContent(request: Request) {
        text_empty_list.visibility = View.GONE
        list_request.visibility = View.VISIBLE
        adapter!!.addContent(request)
    }

    fun setError(errorMessage: String) {
        val builder = AlertDialog.Builder(this@RequestActivity)
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }
}
