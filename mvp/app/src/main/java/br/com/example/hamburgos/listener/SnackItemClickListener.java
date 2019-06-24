package br.com.example.hamburgos.listener;

import android.view.View;

import br.com.example.hamburgos.model.Snack;

public abstract class SnackItemClickListener {

    public abstract View.OnClickListener onClick(String type, Snack snack);
}
