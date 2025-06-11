package org.ptaxhexagonal.interfaces

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.PtaxDto

class FakeConsultaPtaxUseCase : ConsultaPtaxUseCase {
    override fun consultarPtax(data: String): PtaxDto? {
        return PtaxDto(
            data = data,
            cotacaoCompra = 5.0,
            cotacaoVenda = 5.1,
            dataHoraCotacao = "2024-05-23T13:00:00"
        )
    }
}

class PtaxControllerTest {
    @Test
    fun `deve retornar PtaxDto correto`() {
        val controller = PtaxController(FakeConsultaPtaxUseCase())
        val resultado = controller.getPtax("2024-05-23")
        assertNotNull(resultado)
        assertEquals(5.0, resultado?.cotacaoCompra)
        assertEquals(5.1, resultado?.cotacaoVenda)
        assertEquals("2024-05-23T13:00:00", resultado?.dataHoraCotacao)
    }
}
