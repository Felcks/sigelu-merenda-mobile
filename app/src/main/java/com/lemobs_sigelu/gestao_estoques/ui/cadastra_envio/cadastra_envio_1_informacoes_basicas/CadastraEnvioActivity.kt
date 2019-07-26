package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.*
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.CadastraEnvioViewModelFactory
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_2_seleciona_item.SelecionaItemEnvioActivity
import com.lemobs_sigelu.gestao_estoques.ui.pedido.activity.VisualizarPedidoActivity
import com.lemobs_sigelu.gestao_estoques.utils.Mask
import com.sigelu.core.lib.DialogUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cadastra_envio.*
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CadastraEnvioViewModelFactory
    var viewModel: CadastraEnvioViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_envio)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CadastraEnvioViewModel::class.java)

        val mainBinding: com.lemobs_sigelu.gestao_estoques.databinding.ActivityCadastraEnvioBinding = DataBindingUtil.setContentView(this, R.layout.activity_cadastra_envio)
        mainBinding.viewModel = viewModel!!
        mainBinding.executePendingBindings()

        //this.adicionarListenersHoraSaida()
        this.adicionarListenerEdtMotorista()

        ll_layout_anterior.setOnClickListener {
            this.clicouAnterior()
        }
        ll_layout_proximo.setOnClickListener {
            this.clicouProximo()
        }


    }

    private fun adicionarListenerEdtMotorista(){

        edt_motorista.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        edt_motorista.esconderTeclado()
                        edt_motorista.clearFocus()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })
    }

    private fun adicionarListenersHoraSaida(){

        edt_data_saida.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        edt_data_saida.clearFocus()
                        edt_hora_saida.requestFocus()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })

        edt_hora_saida.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        edt_hora_saida.clearFocus()
                        edt_hora_saida.esconderTeclado()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })

        val mascaraHoraSaida = Mask.insert(MASCARA_HORARIO, edt_hora_saida)
        val mascaraDataSaida = Mask.insert(MASCARA_DATA, edt_data_saida)

        edt_hora_saida.addTextChangedListener(mascaraHoraSaida)
        edt_data_saida.addTextChangedListener(mascaraDataSaida)

        edt_hora_saida.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if(!edt_hora_saida.checarHoraValida()){
                    Toast.makeText(applicationContext, "Hora inválida", Toast.LENGTH_SHORT).show()
                    edt_hora_saida.setText(Date().toHoraMinuto())
                    edt_hora_saida.esconderTeclado()
                    ll_titulo.requestFocus()
                }
            }
        }

        edt_data_saida.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if(!edt_hora_saida.checarDataValida()){
                    Toast.makeText(applicationContext, "Data inválida", Toast.LENGTH_SHORT).show()
                    edt_data_saida.setText(Date().toDiaMesAno())
                    edt_hora_saida.requestFocus()
                }
            }
        }

        val horaSaida = TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
            var hora = "0" + i
            var minuto = "0" + i1
            if (i.toString().length > 1)
                hora = i.toString()
            if (i1.toString().length > 1)
                minuto = i1.toString()

            edt_hora_saida.setText(hora + ":" + minuto)
        }

        /*
        val mCalendar = Calendar.getInstance()
        iv_relogio_saida.setOnClickListener {

            val activity = this@CadastraEnvioActivity
            TimePickerDialog(activity, horaSaida, mCalendar
                .get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show()
        }

        iv_calendar_saida.setOnClickListener {
            createTimerPicker()
        }*/

    }

    private fun createTimerPicker(){

        val positiveButtonOnClickListener = DialogInterface.OnClickListener { dialog, _ -> run {
            val datePicker = (dialog as AlertDialog).findViewById<DatePicker>(R.id.date_picker)

            val date = GregorianCalendar()
            date.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)

            viewModel!!.dataSaida.set(Date(date.timeInMillis).toDiaMesAno())
        } }
        val negativeButtonClickListener = DialogInterface.OnClickListener { _, _ ->

        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.dialogo_padrao_datepicker, null)
        view.findViewById<TextView>(R.id.tv_titulo).text = "Selecione o dia do envio"

        alertDialogBuilder.setView(view)

        alertDialogBuilder.setPositiveButton("Confirmar", positiveButtonOnClickListener)
        alertDialogBuilder.setNegativeButton("Cancelar", negativeButtonClickListener)
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.create().show()
    }

    private fun clicouProximo(){
        try {
            viewModel!!.cadastraInformacoesIniciais()
            val intent = Intent(this, SelecionaItemEnvioActivity::class.java)
            startActivity(intent)
        }
        catch (e: Exception){
            Snackbar.make(ll_all, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clicouAnterior(){
        this.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, VisualizarPedidoActivity::class.java)
                DialogUtil.buildAlertDialogSimNao(
                    this,
                    "Cancelar envio ",
                    "Deseja sair e cancelar o envio?",
                    {
                        finish()
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    },
                    {}).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}