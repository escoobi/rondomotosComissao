package br.com.rondomotos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Arquivo {

	// TODO Auto-generated constructor stub

	public void upload(String pasta, String nomeDoArquivo, InputStream arquivoCarregado, String ope) {
		try {
			config_localidade.configLocalidade();
			String caminhoArquivo = pasta + "/" + nomeDoArquivo;
			File novoArq = new File(caminhoArquivo);

			FileOutputStream saida = new FileOutputStream(novoArq);
			copiar(arquivoCarregado, saida);

			
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void copiar(InputStream origem, OutputStream destino) {

		int bite = 0;
		byte[] tamanhhoMaximo = new byte[1024 * 200];
		try {
			while ((bite = origem.read(tamanhhoMaximo)) >= 0) {
				destino.write(tamanhhoMaximo, 0, bite);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
