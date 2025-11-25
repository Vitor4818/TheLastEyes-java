# The Last Eyes: Plataforma de Análise Proativa de Risco

### Apresentação da Solução

O The Last Eyes é uma solução de Micro-Feedback e Análise Proativa de Sentimento que transforma o modo como as empresas mitigam o risco de burnout e a baixa moral. Nosso objetivo é mudar a postura reativa do RH, fornecendo dados acionáveis em tempo real sobre o bem-estar emocional dos colaboradores.

A plataforma coleta Check-ins de Humor diários e permite que a gestão filtre e analise o sentimento por Departamento, identificando e resolvendo focos de estresse antes que se tornem crises organizacionais.

---
### Proposta de Valor e Diferencial Arquitetural

O principal objetivo do The Last Eyes é proporcionar bem-estar mental aos colaboradores por meio da tecnologia e da Arquitetura em Camadas resiliente, garantindo que a rotina da empresa seja ágil e que a análise pesada seja feita de forma confiável.

O sistema resolve o problema de latência e escalabilidade em análises de texto demoradas. A operação é delegada imediatamente, focando em:

- Experiência do Usuário (UX): O usuário envia o texto e a API retorna 202 Accepted (Garantia de resposta rápida), nunca forçando o colaborador a esperar.
- Análise Inteligente e Proativa: O processamento em background não só classifica o humor (API Python) mas também utiliza a IA Generativa (Google Gemini) para criar uma sugestão personalizada de saúde mental e hobbies.
- Resiliência Arquitetural: O uso do RabbitMQ desacopla completamente a API do processo de IA, garantindo que o sistema continue funcional e guarde as tarefas mesmo que os serviços de terceiros (Python/Gemini) estejam temporariamente offline.

### Arquitetura e Componentes Chave (Em Camadas com Mensageria)

1. A aplicação é um monolito modular que se conecta a serviços externos de forma desacoplada.
  
      - API Java (Camadas Web, Service e Persistência)
      - Tecnologia: Java 21, Spring Boot 3.x, JPA/Hibernate.
      - Segurança: Autenticação JWT  (JSON Web Token) e implementação de Cache Estratégico para otimização da performance.
      - Controle: O código é organizado em Camadas (Controller, Service, Repository) com foco na arquitetura limpa.

2. Mensageria e Processamento Assíncrono

      - A comunicação com o serviço de classificação é totalmente assíncrona, sendo o RabbitMQ o ponto central de resiliência.
      - Fila de Mensagens: RabbitMQ (CloudAMQP) atua como um buffer resiliente, garantindo que as tarefas não sejam perdidas se o Worker ou a API Python caírem.
      - IA Generativa: Integração com Google Gemini (via Spring AI) para gerar sugestões de saúde mental. Esta etapa é independente da API Python, garantindo que a sugestão seja sempre gerada (usando fallback de contexto).

3. Persistência

      - Banco de Dados: Oracle Database (para dados de usuário, empresas e registros de check-in).

## 🔗 Links de Deploy e Acesso

| Recurso                        | Detalhe                                           |
|--------------------------------|-------------------------------------------------|
| **Link do Deploy (Aplicação Java)** | https://the-last-eyes-api-3v3p.onrender.com |
| **Documentação (Swagger UI)**      | [Acessar Swagger](https://the-last-eyes-api-3v3p.onrender.com/swagger-ui/index.html#) |
| **Painel do RabbitMQ (CloudAMQP)** | [Acessar painel](https://beaver.rmq.cloudamqp.com) |
| RabbitMQ User: | wwzdqnqm |
| RabbitMQ Password: | wF6nre_bfOCYk1WrzVJQ4bNt9cUKOuCM |



## 🔗 Apresentação e demonstração

| Recurso                           | Link                                         |
|----------------------------------|---------------------------------------------|
| **Vídeo de Demonstração do Software** | [Acessar vídeo de demonstração](https://youtu.be/asOSqmyzDec)          |
| **Link de Apresentação da Solução**  | [Acessar Apresentação MVP - pitch )](https://youtu.be/_OYpnkcw8Hs)      |


## Integrantes

| Nome Completo               | RM       |
|-----------------------------|----------|
| Pedro Henrique Lima Santos  | 558243   |
| Vitor Gomes Martins         | 558244   |
| Leonardo Pimentel Santos    | 557541   |

