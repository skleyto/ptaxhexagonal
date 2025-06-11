package org.ptaxhexagonal.infrastructure

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.PtaxDto
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeParseException

class ConsultaPtaxBacenAdapter : ConsultaPtaxUseCase {
    private val logger = LoggerFactory.getLogger(ConsultaPtaxBacenAdapter::class.java)

    override fun consultarPtax(data: String): PtaxDto? {
        logger.info("Consultando PTAX para a data: $data")
        val dataFormatada = try {
            val parsed = LocalDate.parse(data)
            "%02d-%02d-%04d".format(parsed.monthValue, parsed.dayOfMonth, parsed.year)
        } catch (e: DateTimeParseException) {
            logger.warn("Data inválida fornecida pelo usuário: $data. Esperado: yyyy-MM-dd")
            return null
        }

        val url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='$dataFormatada'"
        logger.info("URL consultada: $url")

        return try {
            val connection = java.net.URI(url).toURL().openConnection() as java.net.HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                logger.debug("Resposta recebida: $response")

                val compra = "\"cotacaoCompra\":([0-9.]+)".toRegex().find(response)?.groupValues?.get(1)?.toDoubleOrNull()
                val venda = "\"cotacaoVenda\":([0-9.]+)".toRegex().find(response)?.groupValues?.get(1)?.toDoubleOrNull()
                val dataHora = "\"dataHoraCotacao\":\"([^\"]+)\"".toRegex().find(response)?.groupValues?.get(1)

                if (compra != null && venda != null && dataHora != null) {
                    logger.info("Compra: $compra, Venda: $venda, DataHora: $dataHora")
                    return PtaxDto(data, compra, venda, dataHora)
                } else {
                    logger.warn("Valores não encontrados na resposta. Verifique se a data informada é um dia útil bancário.")
                    null
                }
            } else {
                logger.error("Falha ao consultar Bacen. Código HTTP: ${connection.responseCode}")
                null
            }
        } catch (e: UnknownHostException) {
            logger.error("Não foi possível conectar ao Bacen. Verifique sua conexão com a internet.", e)
            null
        } catch (e: SocketTimeoutException) {
            logger.error("Tempo de resposta excedido ao consultar o Bacen.", e)
            null
        } catch (e: Exception) {
            logger.error("Erro inesperado ao consultar Bacen: ${e.message}", e)
            null
        }
    }
}
