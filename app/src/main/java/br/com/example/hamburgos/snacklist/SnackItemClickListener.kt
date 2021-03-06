package br.com.example.hamburgos.snacklist

import android.view.View

import br.com.example.hamburgos.model.Snack

abstract class SnackItemClickListener {

    abstract fun onClick(type: String, snack: Snack): View.OnClickListener
}
