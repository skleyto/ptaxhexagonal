// Camada de aplicação: casos de uso
package org.ptaxhexagonal.application

import org.ptaxhexagonal.domain.PtaxDto

interface ConsultaPtaxUseCase {
    fun consultarPtax(data: String): PtaxDto?
}
