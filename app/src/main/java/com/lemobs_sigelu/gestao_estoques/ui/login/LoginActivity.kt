package com.lemobs_sigelu.gestao_estoques.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityLoginBinding
import com.lemobs_sigelu.gestao_estoques.ui.lista_pedidos.ListaPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity: AppCompatActivity(){


    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory
    var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })

        val mainBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        btn_login.setOnClickListener {
            viewModel!!.login()
        }
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderDataState(result: Any?) {
        val intent = Intent(this, ListaPedidoActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun renderErrorState(throwable: Throwable?) {
        Toast.makeText(applicationContext, throwable?.message, Toast.LENGTH_SHORT).show()
    }

}