package com.lemobs_sigelu.gestao_estoques.ui.lista_materiais

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_lista_materiais.*
import javax.inject.Inject

class ListaMaterialActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var viewModel: ListaMaterialViewModel? = null
    private var adapter: ListaMaterialAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_materiais)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListaMaterialViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })
        viewModel!!.carregarListaObras()
    }


    fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {

        Log.i("script2", "Carregando")
    }

    private fun renderDataState(result: Any?) {

        Log.i("script2", "sucesso ao carregar")
        if(result is List<*>){
            this.iniciarAdapter(result as List<Material>)
        }
    }

    private fun renderErrorState(throwable: Throwable?) {

        Log.i("script2", "Erro ao carregar")
    }

    private fun iniciarAdapter(list: List<Material>){

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_lista.layoutManager = layoutManager

        this.adapter = ListaMaterialAdapter(applicationContext, list)
        rv_lista.adapter = adapter
    }
}