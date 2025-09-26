# Spring Boot + Maven Skeleton

Um esqueleto de projeto robusto e opinativo para iniciar microserviços em Java, utilizando Spring Boot, Maven e integração com Docker para um ambiente de desenvolvimento rápido e consistente.

Criado por **Douglas Luciano** [![LinkedIn](https://img.shields.io/badge/LinkedIn-Douglas_Luciano-0077B5?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/douglasluciano2/)

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-4.0.0-critical.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## 🚀 Sobre o Projeto

Este repositório serve como um ponto de partida acelerado para a criação de novas aplicações. Ele já vem pré-configurado com uma seleção de tecnologias e boas práticas comuns no desenvolvimento de APIs REST, incluindo persistência de dados, migrações de banco de dados e um ambiente de desenvolvimento containerizado.

## ✨ Tecnologias Inclusas

* **Framework:** Spring Boot 3.3.4
* **Linguagem:** Java 21
* **Build Tool:** Maven
* **Web:** Spring Web (para APIs REST)
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** MySQL (via Docker)
* **Migrações:** Flyway
* **Utilitários:**
    * Lombok (para redução de código boilerplate)
    * Spring Boot DevTools (para live reload)
* **Ambiente de Dev:** Spring Boot Docker Compose Support
* **Monitoramento:** Spring Boot Actuator (endpoint de health)

## ⚙️ Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina:
* JDK 21 ou superior
* Docker e Docker Compose
* Uma IDE de sua preferência (IntelliJ IDEA, VS Code, Eclipse)
* Git

## 🏁 Como Executar

Siga os passos abaixo para clonar e executar a aplicação localmente.

**1. Clone o repositório**
```bash
git clone [https://github.com/dougluciano2/io-dougluciano-java-springboot-maven-skeleton.git](https://github.com/dougluciano2/io-dougluciano-java-springboot-maven-skeleton.git)
cd io-dougluciano-java-springboot-maven-skeleton
```
**2. Execute a aplicação e verifique**

O esqueleto vem com um `RestController` de exemplo para que você possa verificar se a aplicação subiu corretamente. Após iniciar, você pode testar o endpoint principal de três formas:

* **1 - Usando Postman ou similares:**
    * Crie uma nova requisição do tipo `GET`.
    * Insira a URL: `http://localhost:8080/`
    * Clique em "Send".

* **2 - Via cURL (no terminal):**
    * Abra seu terminal ou prompt de comando e execute:
    ```bash
    curl http://localhost:8080/
    ```
* **3 - Via Navegador:**
    * Abra seu browser favorito e acesse o endereço `http://localhost:8080/`.


* *Em qualquer forma, você deverá receber um Json response da seguinte forma:*

```json
{
    "name": "I'm working!!! Lombok's working!"
}
```

**3. Altere os atributos de configuração**

Agora que você viu que tudo funciona, você deverá alterar os valores em alguns arquivos para corresponder ao nome da sua aplicação:

* **1º - pom.xml:**
```xml
<groupId>io.dougluciano</groupId> <!-- pacote do seu projeto -->
<artifactId>skelleton</artifactId> <!-- Artifact ID do projeto -->
<version>0.0.1-SNAPSHOT</version> <!-- versão -->
<name>skelleton</name> <!-- Nome do projeto -->
<description>Skelleton only</description> <!-- descrição do projeto -->
```
* **2º - application.properties:**

spring.application.name=skelleton

Altere essa linha para o nome da sua aplicação

* **3º - compose.yaml:**

Altere o nome do container, usuário, senha e root

Caso opte por usar um banco diferente do que está descrito, será necessário verificar a documentação correspondente


Obrigado!!!!

