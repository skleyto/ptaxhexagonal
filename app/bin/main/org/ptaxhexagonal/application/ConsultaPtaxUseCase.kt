// Camada de aplicação: casos de uso
package org.ptaxhexagonal.application

import org.ptaxhexagonal.domain.Ptax

interface ConsultaPtaxUseCase {
    fun consultarPtax(data: String): Ptax?
}
