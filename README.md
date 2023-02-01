# Grupo 🏍️Rondomotos🏍️ e 🏁Atlântica Motos🏁, Concessionária Honda.

## ⚠️Problemática⚠️

* A forma de comissionamento dos funcionários foi reformulada e incluida escalonamento por quantidade de vendas e descontos concedidos aos clientes.
* No comissionamento anterior a empresa absorvia os custos com taxas de cartão de crédito.
* As vendas realizadas abaixo do valor de tabela, ou seja, com desconto concedido e a empresa também deixava de arrecadar.
* Força o faturamento de motocicletas no valor de tabela ou acima.
* Os colaboradores do setor de faturamento de cada loja, ao virar o mês realizava o preenchimento de uma planilha no excel e gerava as comissões da vendas.
* Os colaborares encaminha um email para o departameto de RH da matriz com o anexo a planilha com a comissão.
* Com a planilha na caixa de entrada a equipe de RH entrava em coferência dessas informações para poder rodar a folha de pagamento no quinto dia util do mês.
* Extinguir algumas rotinas e automatizar outras.
* Eliminar erros de calculos.

## ✔️Resolução✔️

Desenvolver uma aplicação ao qual processa os dois relatórios gerados pelo DMS - Microwork Cloud

* Relatório de faturamento mensal
* Relatório de vendas mensal

Gerar a comissão das vendas das motocicletas automaticas.
Elaborar um tutorial de utilização da aplicação web.

## 👨‍💻Tecnologia utilizadas👨‍💻

* ☕Java Web☕
* 📝Processamento de arquivos CSV📝
* 🧮Calculos artiméticos🧮
* 🐱Tomcat🐱

## 🧮Vamos falar do calculo🧮

* Valor de tabela motocicleta XYZ = R$ 16.000,00.
* Escalonamento de comissão: 5%, 3%, 2% e 1%.
* Formas de pagamentos Cartão de Crédito, Duplicata e Cheque.
* Fluxo de caixa descontado. -> Lógica para realizar no calculo utilizado.
* Buscar o valor das vendas parceladas para o VP(Valor Presente).

## 💻Pulo do gato💻

Observar a class calcularVP
[class calcularVP](https://github.com/escoobi/rondomotos/blob/master/eclipse-workspace/rondomotos/src/br/com/rondomotos/calcularVP.java)
Utilizado a função Math.pow em um for.


