<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

Este projeto utiliza Kotlin, Gradle e segue princípios de arquitetura hexagonal. O objetivo é expor uma API para buscar a cotação Ptax do Bacen.

- Siga boas práticas de separação de camadas (domínio, aplicação, infraestrutura, interfaces).
- Utilize testes automatizados para cada camada.
- Prefira dependências externas apenas na camada de infraestrutura.
- O domínio não deve depender de frameworks ou bibliotecas externas.
