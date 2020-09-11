package br.com.rondomotos;

import java.io.BufferedReader;

import java.io.FileReader;

public class CheckVendedor {
	public static String minhaLinha = "";

	public static void lerVendedorProposta(String caminho, String ope) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));

			if (ope == null) {
				while ((minhaLinha = br.readLine()) != null) {
					minhaLinha = minhaLinha.replaceAll("\"", ""); // Utilizado Código Regex para
					// remover aspa dupla.
					String[] linhasLer = minhaLinha.split(";");
					if (!linhasLer[6].contentEquals("RONDO MOTOS LTDA")) {
						Comissao.arrayListaVendedor.add(linhasLer[6]);
						Comissao.arraySaida.add(minhaLinha);
					}

				}
			} else {
				while ((minhaLinha = br.readLine()) != null) {
					minhaLinha = minhaLinha.replaceAll("\"", ""); // Utilizado Código Regex para
					// remover aspa dupla.
					Comissao.arrayProposta.add(minhaLinha);
				}
				br.close();
			}
		} catch (Exception e) {
			// TODO: handle exception

		}

	}

}
