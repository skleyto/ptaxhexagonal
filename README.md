<<<<<<< HEAD
# ptaxhexagonal
Projeto realizado a fim de aplicar conhecimento em Hexagonal, Conceitos de Solid e para treinar Kotlin
=======
# PtaxHexagonalApi

Este projeto é uma API em Kotlin estruturada com princípios de arquitetura hexagonal. O objetivo é fornecer um endpoint para consultar a cotação Ptax diretamente do site do Bacen (Banco Central do Brasil).

## Estrutura Sugerida
- **Domínio:** regras de negócio e entidades centrais
- **Aplicação:** casos de uso e orquestração
- **Infraestrutura:** integração com Bacen, frameworks, web, etc.
- **Interfaces:** controladores/rest endpoints

## Como rodar
1. Certifique-se de ter Java 21+ instalado e configurado.
2. No terminal, execute:
   ```
   ./gradlew.bat build
   ./gradlew.bat run
   ```

## Próximos Passos
- Implementar as camadas hexagonais
- Criar integração HTTP com o Bacen
- Expor endpoint REST para consulta do Ptax
>>>>>>> 72a0788 (Projeto Kotlin Hexagonal PTAX - estrutura inicial e integração Bacen)
