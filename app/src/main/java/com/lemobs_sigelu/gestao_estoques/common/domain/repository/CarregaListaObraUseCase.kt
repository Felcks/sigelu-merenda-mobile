package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.LISTA_OBRAS_MOCKADAS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable


class CarregaListaObraUseCase {

    fun getObras(): Observable<List<Obra>> {

        return Observable.create { subscriber ->

            subscriber.onNext(LISTA_OBRAS_MOCKADAS)
            subscriber.onComplete()
        }
    }
}