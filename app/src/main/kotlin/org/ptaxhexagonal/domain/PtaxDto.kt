package org.ptaxhexagonal.domain

data class PtaxDto(
    val data: String,
    val cotacaoCompra: Double,
    val cotacaoVenda: Double,
    val dataHoraCotacao: String
)