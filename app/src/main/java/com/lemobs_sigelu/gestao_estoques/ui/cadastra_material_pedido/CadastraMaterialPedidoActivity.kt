package com.lemobs_sigelu.gestao_estoques.ui.cadastra_material_pedido

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraMaterialPedidoBinding
import com.lemobs_sigelu.gestao_estoques.ui.confirma_materiais_pedido.ConfirmaMateriaisPedidoActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_material_pedido.*
import javax.inject.Inject

class CadastraMaterialPedidoActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraMaterialPedidoViewModelFactory
    var viewModel: CadastraMaterialPedidoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraMaterialPedidoViewModel::class.java)
        val mainBinding: ActivityCadastraMaterialPedidoBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_material_pedido)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        val itemEnvio = viewModel!!.getMaterial()
        if(itemEnvio != null) {
            tv_1.text = itemEnvio.itemEstoque?.nomeAlternativo
            tv_2.text = itemEnvio.itemEstoque?.descricao
            tv_3.text = itemEnvio.itemEstoque?.unidadeMedida?.getNomeESiglaPorExtenso()
            tv_4.setText(itemEnvio.quantidadeUnidade.toString())
        }

//        tv_5.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                if(tv_5.text.isNotEmpty()) {
//                    val valor = tv_5.text.toString().replace(',', '.').toDouble()
//                    viewModel!!.setQuantidadeMaterial(applicationContext, valor)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.btn_done){

            val resultado = viewModel!!.confirmaCadastroMaterial()
            when (resultado) {
                -1.0 -> Toast.makeText(applicationContext, "Ocorreu algum erro", Toast.LENGTH_SHORT).show()
                -2.0 -> Toast.makeText(applicationContext, "Digite um valor maior que zero.", Toast.LENGTH_SHORT).show()
                else -> {
                    viewModel!!.cadastraQuantidadeMaterial(resultado)
                    val intent = Intent(this, ConfirmaMateriaisPedidoActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}