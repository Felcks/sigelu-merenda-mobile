package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import io.reactivex.Observable

class CarregaListaMaterialCadastradoRepository {

    fun carregaMateriais(context: Context): Observable<List<MaterialParaCadastro>> {

        return Observable.create { subscribe ->
            subscribe.onNext(materiaisCadastrados)
            subscribe.onComplete()
        }
    }
}