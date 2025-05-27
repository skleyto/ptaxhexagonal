// Camada de infraestrutura: implementação do acesso ao Bacen
package org.ptaxhexagonal.infrastructure

import org.ptaxhexagonal.application.ConsultaPtaxUseCase
import org.ptaxhexagonal.domain.Ptax
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeParseException

class ConsultaPtaxBacenAdapter : ConsultaPtaxUseCase {
    private val logger = LoggerFactory.getLogger(ConsultaPtaxBacenAdapter::class.java)

    override fun consultarPtax(data: String): Ptax? {
        logger.info("Consultando PTAX para a data: $data")

        // Validação de formato de data (esperado: yyyy-MM-dd)
        val dataFormatada = try {
            val parsed = LocalDate.parse(data)
            parsed.toString().replace("-", "/")
        } catch (e: DateTimeParseException) {
            logger.warn("Data inválida fornecida pelo usuário: $data. Esperado: yyyy-MM-dd")
            return null
        }

        val url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='$dataFormatada'"

        try {
            val urlObj = java.net.URI(url).toURL()
            val connection = urlObj.openConnection() as java.net.HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            logger.debug("Código de resposta HTTP: $responseCode")
            if (responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                logger.debug("Resposta recebida: $response")
                val regex = "\"cotacaoCompra\":([0-9.]+)".toRegex()
                val match = regex.find(response)
                if (match != null) {
                    val valor = match.groupValues[1].toDouble()
                    logger.info("Valor encontrado: $valor")
                    return Ptax(data, valor)
                } else {
                    logger.warn("Valor da cotação não encontrado na resposta. Verifique se a data informada é um dia útil bancário.")
                }
            } else {
                logger.error("Falha ao consultar Bacen. Código HTTP: $responseCode")
            }
        } catch (e: UnknownHostException) {
            logger.error("Não foi possível conectar ao Bacen. Verifique sua conexão com a internet.", e)
        } catch (e: SocketTimeoutException) {
            logger.error("Tempo de resposta excedido ao consultar o Bacen.", e)
        } catch (e: Exception) {
            logger.error("Erro inesperado ao consultar Bacen: ${e.message}", e)
        }
        return null
    }
}
