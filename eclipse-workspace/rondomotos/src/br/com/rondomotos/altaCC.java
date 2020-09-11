package br.com.rondomotos;

import java.io.BufferedReader;
import java.io.FileReader;

public class altaCC {
	public static String minhaLinha = "";

	public static boolean carregar(String modelo) {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("c:\\comissao\\modelos.csv"));
			while ((minhaLinha = br.readLine()) != null) {
				minhaLinha = minhaLinha.replaceAll("\"", ""); // Utilizado Código Regex para
				// remover aspa dupla.
				String[] linhasLer = minhaLinha.split(";");
				if (linhasLer[0].equals(modelo)) {
					return true;
				}

			}
			br.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public static void lerModelos(String caminho, String ope) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			
			if (ope != null) {
				while ((minhaLinha = br.readLine()) != null) {
					minhaLinha = minhaLinha.replaceAll("\"", ""); // Utilizado Código Regex para
					// remover aspa dupla.
					String[] linhasLer = minhaLinha.split(";");
					Comissao.arrayModelos.add(linhasLer[0]);
					
				}
				br.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception

		}

	}


}
