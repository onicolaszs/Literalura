# 📚 Literalura
O Literalura é um projeto em Java desenvolvido com Spring Boot, cujo objetivo é consumir dados de livros a partir de uma API externa e armazená-los em um banco de dados relacional (PostgreSQL). A aplicação funciona via console, permitindo ao usuário interagir por meio de um menu textual. 

## ⚙️ Como o projeto funciona?
1. A aplicação se conecta a uma API pública de livros para buscar informações literárias.<br>
2. Os dados retornados (livros, autores, idiomas, etc.) são processados e convertidos em entidades Java.<br>
3. O usuário interage com o sistema através do console, podendo:<br>
   - Buscar livros por título<br>
   - Listar livros cadastrados<br>
   - Listar autores<br>
   - Filtrar livros por idioma<br>
   - Consultar autores vivos em determinado período<br>

## 🗄️ Banco de Dados
- O projeto utiliza PostgreSQL como sistema de gerenciamento de banco de dados.<br>
- Cada pessoa que executar o projeto deve:<br>
  - Ter o PostgreSQL instalado;<br>
  - Criar um banco de dados local;<br>
  - Criar um usuário e senha próprios;<br>
  - Configurar essas credenciais no arquivo application.properties.<br>

 ## 📋 Requisitos
 Antes de rodar o projeto, é necessário ter instalado:
- Java JDK 17 ou superior
- Maven<br>
- PostgreSQL<br>
- IDE Java IntelliJ IDEA<br>

## Considerações finais
Esse projeto foi um desafio proposto pela escola online de tecnologia Alura com o intuito de aplicar boas práticas, organização de código, persistência de dados e integração com APIs externaa.
