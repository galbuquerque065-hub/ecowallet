#  EcoWallet

> Sistema de gestão financeira pessoal com interface gráfica desenvolvido em Java.

---

##  Tecnologias

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=flat&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-000000?style=flat&logo=json&logoColor=white)

---

##  Sobre o projeto

O EcoWallet é uma aplicação desktop de controle financeiro pessoal desenvolvida como projeto final da disciplina de Programação Orientada a Objetos.

O sistema permite ao usuário registrar receitas e despesas, visualizar o saldo atualizado automaticamente e manter um histórico de transações — tudo com interface gráfica intuitiva.

---

##  Funcionalidades

- Registro de receitas e despesas
- Saldo atualizado automaticamente via `ListChangeListener`
- Listagem de transações em `TableView`
- Persistência de dados em arquivo JSON
- Interface construída com FXML

---

##  Arquitetura

- Padrão de projeto **Factory**
- Princípios **SOLID** (SRP aplicado)
- Herança com classe abstrata `Transacao` → `Receita` / `Despesa`
- Persistência via biblioteca `json-simple`

---

##  Como executar

**Pré-requisitos:** JDK 21 e Maven instalados.

```bash
# Clone o repositório
git clone https://github.com/gabriel-alb/ecowallet.git

# Acesse a pasta
cd ecowallet

# Execute com Maven
mvn javafx:run
```

---

##  Autor

**Gabriel Albuquerque**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gabriel-alb)
