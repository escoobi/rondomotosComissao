package br.com.rondomotos;

import java.util.ArrayList;

public class ComissaoFinal {
	public static String linha;
	public static ArrayList<String> alistVP;
	public static ArrayList<String> comissaoDuplicada;

	public static void ComissaoFinal() {
		// TODO Auto-generated constructor stub

		String cheia = "";
		try {

			Double valor = 0.0;
			comissaoDuplicada = new ArrayList<String>();
			for (String proposta : tratar_proposta.temp_proposta) {
				String[] linhasProposta = proposta.split(";");

				if ((consultaVendedor(linhasProposta[9]).contentEquals("on") && !linhasProposta[9].contentEquals("RONDO MOTOS LTDA"))) {

					for (String propostaSegunda : tratar_proposta.temp_proposta) {
						String[] linhasPropostaSegunda = propostaSegunda.split(";");

						if (linhasProposta[3].equals(linhasPropostaSegunda[3])) {
							linhasPropostaSegunda[6] = linhasPropostaSegunda[6].replaceAll(",", ".");
							valor = Double.parseDouble(linhasPropostaSegunda[6]) + valor;

						}

					}
					linhasProposta[8] = linhasProposta[8].replaceAll(",", ".");

					if (altaCC.carregar(linhasProposta[4])) {

						linhasProposta[8] = linhasProposta[8].replaceAll(",", ".");

						cheia = linhasProposta[3] + ";" + linhasProposta[1] + ";" + linhasProposta[10] + ";"
								+ linhasProposta[4] + ";" + linhasProposta[9] + ";" + linhasProposta[8] + ";" + valor
								+ ";" + (valor / Double.parseDouble(linhasProposta[8])) * 100;
						Double comissao = 0.0;
						String[] cheiaCalculada = cheia.split(";");
						cheiaCalculada[6] = cheiaCalculada[6].replaceAll(",", ".");
						if (Double.parseDouble(cheiaCalculada[7]) >= 97) {
							comissao = 0.012 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] + ";" + 1.2);
						}
						if (Double.parseDouble(cheiaCalculada[7]) <= 96.999) {
							comissao = 0.008 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] + ";" + 0.8);
						}
					} else {

						linhasProposta[8] = linhasProposta[8].replaceAll(",", ".");
						cheia = linhasProposta[3] + ";" + linhasProposta[1] + ";" + linhasProposta[10] + ";"
								+ linhasProposta[4] + ";" + linhasProposta[9] + ";" + linhasProposta[8] + ";" + valor
								+ ";" + (valor / Double.parseDouble(linhasProposta[8])) * 100;
						Double comissao = 0.0;
						String[] cheiaCalculada = cheia.split(";");
						cheiaCalculada[6] = cheiaCalculada[6].replaceAll(",", ".");
						if (Double.parseDouble(cheiaCalculada[7]) >= 100) {
							comissao = 0.020 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] +";"+ 2);
						}
						if (Double.parseDouble(cheiaCalculada[7]) >= 97
								&& Double.parseDouble(cheiaCalculada[7]) <= 99.999) {
							comissao = 0.016 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] +";"+ 1.6);
						}
						if (Double.parseDouble(cheiaCalculada[7]) >= 95
								&& Double.parseDouble(cheiaCalculada[7]) <= 96.999) {
							comissao = 0.012 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] +";"+ 1.2);
						}
						if (Double.parseDouble(cheiaCalculada[7]) <= 94.999) {
							comissao = 0.010 * Double.parseDouble(cheiaCalculada[6]);
							// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
							// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
							// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
							// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
							comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
									+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
									+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";"
									+ String.format("%.2f", comissao) + ";" + linhasProposta[11] +";"+ 1);
						}
					}

				}
				if ((consultaVendedor(linhasProposta[9]).contentEquals("null")) && !linhasProposta[9].contentEquals("RONDO MOTOS LTDA")) {

					for (String propostaSegunda : tratar_proposta.temp_proposta) {
						String[] linhasPropostaSegunda = propostaSegunda.split(";");

						if (linhasProposta[3].equals(linhasPropostaSegunda[3])) {
							linhasPropostaSegunda[6] = linhasPropostaSegunda[6].replaceAll(",", ".");
							valor = Double.parseDouble(linhasPropostaSegunda[6]) + valor;

						}

					}
					linhasProposta[8] = linhasProposta[8].replaceAll(",", ".");

					cheia = linhasProposta[3] + ";" + linhasProposta[1] + ";" + linhasProposta[10] + ";"
							+ linhasProposta[4] + ";" + linhasProposta[9] + ";" + linhasProposta[8] + ";" + valor + ";"
							+ (valor / Double.parseDouble(linhasProposta[8])) * 100;
					Double comissao = 0.0;
					String[] cheiaCalculada = cheia.split(";");
					cheiaCalculada[6] = cheiaCalculada[6].replaceAll(",", ".");

					if (Double.parseDouble(cheiaCalculada[7]) >= 97) {
						comissao = 0.012 * Double.parseDouble(cheiaCalculada[6]);
						// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
						// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
						// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
						// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
						comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
								+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
								+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" + String.format("%.2f", comissao)
								+ ";" + linhasProposta[11] + ";" + 1.2 );
					}
					if (Double.parseDouble(cheiaCalculada[7]) <= 96.999) {
						comissao = 0.008 * Double.parseDouble(cheiaCalculada[6]);
						// System.out.println(cheiaCalculada[0] +";" + cheiaCalculada[1] + ";" +
						// cheiaCalculada[2] + ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" +
						// cheiaCalculada[5] + ";" + cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" +
						// String.format("%.2f",comissao) + ";" + linhasProposta[11]);
						comissaoDuplicada.add(cheiaCalculada[0] + ";" + cheiaCalculada[1] + ";" + cheiaCalculada[2]
								+ ";" + cheiaCalculada[3] + ";" + cheiaCalculada[4] + ";" + cheiaCalculada[5] + ";"
								+ cheiaCalculada[6] + ";" + cheiaCalculada[7] + ";" + String.format("%.2f", comissao)
								+ ";" + linhasProposta[11] + ";" + 0.8);
					}

				}
				valor = 0.0;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String consultaVendedor(String vendedor) {

		String tip = "";
		for (String roda : Comissao.arrayListaVendedorTratados) {
			String[] linhasVendedor = roda.split(";");
			if (vendedor.contentEquals(linhasVendedor[0])) {
				tip = linhasVendedor[1];

			}

		}
		return tip;

	}

}
