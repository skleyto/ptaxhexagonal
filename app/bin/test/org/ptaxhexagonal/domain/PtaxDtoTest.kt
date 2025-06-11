package org.ptaxhexagonal.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PtaxDtoTest {
    @Test
    fun `deve criar PtaxDto corretamente`() {
        val dto = PtaxDto("2024-05-23", 5.0, 5.1, "2024-05-23T13:00:00")
        assertEquals("2024-05-23", dto.data)
        assertEquals(5.0, dto.cotacaoCompra)
        assertEquals(5.1, dto.cotacaoVenda)
        assertEquals("2024-05-23T13:00:00", dto.dataHoraCotacao)
    }
}