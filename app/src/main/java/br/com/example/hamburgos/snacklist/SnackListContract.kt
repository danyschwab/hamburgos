package br.com.example.hamburgos.snacklist

import br.com.example.hamburgos.model.Snack

interface SnackListContract {
    interface View{
        fun setContent(list: List<Snack>)
        fun requestConfirmation()
        fun setError(errorMessage: String?)
    }
    interface Presenter{

    }
}