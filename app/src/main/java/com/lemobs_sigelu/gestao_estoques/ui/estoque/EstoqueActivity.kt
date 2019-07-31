package com.lemobs_sigelu.gestao_estoques.ui.estoque

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import dagger.android.AndroidInjection
import javax.inject.Inject

class EstoqueActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: EstoqueViewModelFactory
    var viewModel: EstoqueViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar : ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}