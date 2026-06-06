# Refatoração de Sistema Java – Ação de Extensão

Este projeto consiste na **refatoração de um sistema Java existente**, realizada como parte de uma **ação de extensão acadêmica**.

O objetivo principal foi **melhorar a qualidade do código**, aplicando boas práticas de desenvolvimento, organização arquitetural e padronização, sem alterar o comportamento funcional do sistema.

A refatoração teve caráter **educacional e prático**, permitindo analisar um código real, identificar problemas estruturais e aplicar soluções técnicas adequadas.

## 🎯 Objetivos da Refatoração

- Melhorar a **legibilidade e manutenção** do código
- Reduzir acoplamento e responsabilidades excessivas
- Aplicar **boas práticas de orientação a objetos**
- Organizar melhor pacotes, classes e métodos
- Tornar o sistema mais preparado para evolução futura
- Consolidar o aprendizado prático em Java

## 🧩 Problemas Identificados no Código Original

Antes da refatoração, o sistema apresentava alguns problemas comuns, como:

- Classes com muitas responsabilidades (violação do SRP)
- Métodos longos e pouco legíveis
- Falta de padronização de nomes
- Baixa separação entre camadas (negócio, controle, dados)
- Código duplicado
- Dificuldade de entendimento para novos desenvolvedores

## 🛠️ Principais Melhorias Aplicadas

Durante a refatoração, foram realizadas as seguintes ações:

- Reorganização de pacotes por responsabilidade
- Extração de métodos para reduzir complexidade
- Renomeação de classes, métodos e variáveis para maior clareza
- Separação de regras de negócio e lógica de controle
- Aplicação de princípios de boas práticas (SOLID quando aplicável)
- Remoção de código redundante ou não utilizado

> ⚠️ Importante: a refatoração **não alterou o comportamento funcional** do sistema, apenas sua estrutura interna.

## 🚀 Deploy da Aplicação

A aplicação refatorada está disponível para acesso no seguinte endereço:

**Link do deploy:**
https://taskhub-backend-7wyq.onrender.com/swagger-ui/index.html#/

## 🧱 Tecnologias Utilizadas

- **Java 21**
- Spring 4.0.3
- PostgreSQL
- Git para versionamento
