package br.com.rondomotos;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comissao")
@MultipartConfig
public class Comissao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String caminhoProposta = null;
	public static String caminhoSaida = null;
	public static ArrayList<String> arrayListaVendedor = null;
	public static ArrayList<String> arrayListaVendedorTratados = null;
	public static ArrayList<String> arrayListaModeloTratados = null;
	public static ArrayList<String> arraySaida = null;
	public static ArrayList<String> arrayProposta = null;
	public static ArrayList<String> arrayModelos = null;

	public static String op1 = null;
	public static String op2 = null;
	public static String op3 = null;
	public static String op4 = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Comissao.arrayListaVendedor = new ArrayList<String>();
		Comissao.arraySaida = new ArrayList<String>();
		Comissao.arrayProposta = new ArrayList<String>();
		Comissao.arrayListaVendedorTratados = new ArrayList<String>();
		Comissao.arrayModelos = new ArrayList<String>();

		escreve(resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=UTF-8");
		Arquivo arquivo = new Arquivo();
		op1 = req.getParameter("btnFormRelSaida");
		op2 = req.getParameter("btnFormRelProposta");
		op3 = req.getParameter("btnFormSeparaVendedor");
		op4 = req.getParameter("btnFormSeparaModelo");

		if (op1 != null) {
			arquivo.upload("/rondomotos", "saida.csv", req.getPart("arqUpload").getInputStream(), null);

			escreve1(resp);
			op1 = null;

		}
		if (op2 != null) {

			arquivo.upload("/rondomotos", "proposta.csv", req.getPart("arqUploadProposta").getInputStream(), "ok");
			CheckVendedor.lerVendedorProposta(req.getContextPath() + "/" + "saida.csv", null);

			escreveSeparaVendedor(resp);

			op2 = null;
		}
		if (op3 != null) {

			for (String vend : arrayListaVendedor) {
				arrayListaVendedorTratados.add(vend + ";" + req.getParameter(vend.toLowerCase().replaceAll(" ", "")));

			}
			altaCC.lerModelos(req.getContextPath() + "/" + "saida.csv", op3);
			caminhoSaida = req.getContextPath() + "/" + "saida.csv";
			caminhoProposta = req.getContextPath() + "/" + "proposta.csv";
			tratar_proposta.eliminaEspaco(req.getContextPath() + "/" + "proposta.csv", op3);
			op3 = null;
			escreveSeparaAltaCC(resp);
		}
		if (op4 != null) {

			op4 = null;
			ComissaoFinal.ComissaoFinal();
			escreverComissaoDuplicada.escrever();
			escreveProposta(resp);
			File f = new File(caminhoProposta);
			f.delete();
			File fo = new File(caminhoSaida);
			fo.delete();

		}
	}

	public Comissao() {
		// TODO Auto-generated constructor stub
	}

	public static void escreve(HttpServletResponse resp) {
		try {

			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");

			out.println(
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"charset=\"ISO-8859-1\"/>");

			out.println(
					"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"	integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\"/>");
			out.println(
					"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");

			out.println("</head>");
			out.println("<body>");

			out.println("<div class=\"container\">");

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			
			
			
			
			out.println("<div class=\"shadow-none p-3 mb-5 bg-light rounded\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Comissão vendas Motocicletas 0KM!</h1>");
			out.println("</div>");
			out.println("</div>");
			/*----------------Passo a Passo 01--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 28rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print1.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Acessa o sistema Microwork Cloud e segue o caminho <b>Menu->Relatórios->Veículos->Saída.</b></p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Passo a Passo 02--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 28rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print2.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Ao lado esquerdo conforme a figura acima encontra-se <b>Meus Leiautes</b> a opção <b>COMISSAO - PROGRAMA EXTERNO TABELA</b>, caso não tenha essa opção solicitar o compartilhamento dessa opção com a equipe de TI local.</p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Passo a Passo 03--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 28rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print3.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Nessa etapa, selecionar a loja ao qual foi faturada as motocicletas 0km <b>Ariquemes ou Jaru</b> na opção <b>1</b>! na opção <b>2</b> informar a data inicial e final do mês ao qual deseja obter as vendas e por fim na etapa<b>3</b> verificar as opções do estado do veículo como <b>NOVO</b> e em <b>Tipo de Movimento</b> selecionar apenas as opções <b>Venda e Devolução de Venda</b> e por fim Processar na opção <b>4</b>.</p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Passo a Passo 04--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 68rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print4.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Nas respectivas ordens as colunas segue como a <b>1 - Modelo</b>, <b>2 - Proposta</b>, <b>3 - Valor Tabela</b>, <b>4 - Valor Total Doc. Fiscal</b>, <b>5 - Doc. Fiscal</b>, <b>6 - Pessoa</b>, <b>7 - Vendedor</b>, <b>8 - Grupo Pessoa</b> e <b>9 - Origem Venda</b> e por fim na etapa <b>10</b> exportar o arquivo para formato <b>CSV</b> e salve em uma pasta para poder realizar o upload do arquivo.</p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Formulario 1--------------------*/

			out.println("<div class=\"shadow p-3 mb-5 bg-white rounded\">");
			out.println(
					"<form method=\"POST\" enctype=\"multipart/form-data\" type=\"post\" action=\"comissao\" accept-charset=\"ISO-8859-1\">");

			/* txtloja */
			out.println(
					"<label for=\"arqUpload\">Realizar o upload do Arquivo com o respectivo nome informado abaixo!");
			out.println("<p class=\"h5\">Saída - SAÍDA - COMISSAO - PROGRAMA EXTERNO TABELA.csv</p>");
			out.println("</label>");
			out.println(
					"<input type=\"file\" class=\"form-control-file is-invalid\" placeholder=\"Required example textarea\" required accept=\".csv\" name=\"arqUpload\" id=\"arqUpload\">");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			out.println("</div>");

			out.println("<div class=\"form-group; shadow p-3 mb-5 bg-white rounded; mx-auto\">");
			out.println(
					"<h1 class=\"display-24\">Agora gerar o relatório de proposta seguindo as orientações na próxima tela.</h1>");

			out.println(
					"<button type=\"submit\" value=\"ok\" name=\"btnFormRelSaida\" class=\"btn btn-primary btn-sm\">Próxima tela</button>");

			out.println("</form>");

			out.println("</div>");

			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void escreve1(HttpServletResponse resp) {
		try {

			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");

			out.println(
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"charset=\"ISO-8859-1\"/>");

			out.println(
					"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"	integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\"/>");
			out.println(
					"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");

			out.println("</head>");
			out.println("<body>");

			out.println("<div class=\"container\">");

			

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			out.println("<div class=\"alert alert-light\" role=\"alert\">");
			out.println("<blockquote class=\"blockquote\">");
			out.println("<p class=\"mb-0\">");
			out.println("<button type=\"button\" onClick=\"window.location.href='/rondomotos/comissao'\" class=\"btn btn-primary\">Voltar</button>");
			out.println("</p>");
			out.println("</blockquote>");
			
			out.println("</div>");
			
			
			
			
			
			
			out.println("<div class=\"shadow-none p-3 mb-5 bg-light rounded\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Realizar as etapas para upload do arquivo de PROPOSTA!</h1>");
			out.println("</div>");
			out.println("</div>");
			/*----------------Passo a Passo 01 Proposta--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 28rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print5.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Acessa o sistema Microwork Cloud e segue o caminho <b>Menu->Relatórios->Veículos->Proposta de Venda.</b></p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Passo a Passo 02 Proposta--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 28rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print6.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Ao lado esquerdo conforme a figura acima encontra-se Meus Leiautes na opção  <b>1 - RELATÓRIO COMISSÃO SISTEMA EXTERNO</b>, caso não tenha essa opção solicitar o compartilhamento dessa opção com a equipe de TI local, na opção <b>2</b> selecionar a loja <b>Ariquemes ou Jaru</b>, na opção <b>3</b> informa o período inicial e final e o estado do Veículo <b>NOVO</b>, por fim na opção <b>4</b> Processar o relatório.</p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Passo a Passo 07 Proposta--------------------*/

			out.println("<div class=\"card; shadow p-3 mb-5 bg-white rounded; mx-auto\" style=\"width: 70rem;\">");
			out.println("<img class=\"card-img-top\" src=\"print7.jpg\" alt=\"Imagem de capa do card\">");
			out.println(" <div class=\"card-body\">");
			out.println(
					"<p class=\"card-text\">Nas respectivas ordens as colunas segue como a <b>1</b> - Pessoa, <b>2</b> - Nº Proposta, <b>3</b> - Doc. Fiscal, <b>4</b> - Forma Recebimento, <b>5</b> - Nº Parcela, <b>6</b> - Modelo, <b>7</b> - Devolvida, <b>8</b> -Valor Total, <b>9</b> - Exportar, por fim arquivo para formato CSV e salve em uma pasta para poder realizar o upload do arquivo..</p>");
			out.println("</div>");
			out.println("</div>");

			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			/*----------------Formulario 2--------------------*/
			out.println("<div class=\"shadow p-3 mb-5 bg-white rounded\">");
			out.println(
					"<form method=\"POST\" enctype=\"multipart/form-data\" action=\"comissao\" accept-charset=\"ISO-8859-1\">");

			/* txtloja */
			out.println(
					"<label for=\"arqUploadProposta\">Realizar o upload do Arquivo com o respectivo nome informado abaixo!");
			out.println(
					"<p class=\"h5\">Proposta de Venda - PROPOSTA DE VENDA - RELATÓRIO COMISSÃO SISTEMA EXTERNO.csv");
			out.println("</label>");
			out.println(
					"<input type=\"file\" class=\"form-control-file is-invalid\" placeholder=\"Required example textarea\" required  name=\"arqUploadProposta\" accept=\".csv\"  id=\"arqUploadProposta\">");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");
			out.println("");

			out.println("</div>");

			out.println("<div class=\"form-group; shadow p-3 mb-5 bg-white rounded; mx-auto\">");
			out.println("<h1 class=\"display-24\">Na próxima tela selecionar os vendedores que são internos.</h1>");
			out.println(
					"<button type=\"submit\" value=\"ok\" id=\"btnFormRelProposta\" name=\"btnFormRelProposta\" class=\"btn btn-primary btn-sm\">Carregar Arquivos</button>");

			out.println("</form>");
			out.println("</div>");

			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void escreveSeparaVendedor(HttpServletResponse resp) {
		try {

			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");

			out.println(
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"charset=\"ISO-8859-1\"/>");

			out.println(
					"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"	integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\"/>");
			out.println(
					"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");

			out.println("</head>");
			out.println("<body>");

			out.println("<div class=\"container\">");

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			out.println("<div class=\"alert alert-light\" role=\"alert\">");
			out.println("<blockquote class=\"blockquote\">");
			out.println("<p class=\"mb-0\">");
			out.println("<button type=\"button\" onClick=\"window.location.href='/rondomotos/comissao'\" class=\"btn btn-primary\">Voltar</button>");
			out.println("</p>");
			out.println("</blockquote>");
			
			out.println("</div>");
			
			
			
			
			
			
			out.println("<div class=\"shadow-none p-3 mb-5 bg-light rounded\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Selecionar apenas os vendedores INTERNOS!</h1>");
			out.println("</div>");
			out.println("</div>");

			/*--------------Tabela--------------------------------*/

			Object[] st = arrayListaVendedor.toArray();
			for (Object s : st) {
				if (arrayListaVendedor.indexOf(s) != arrayListaVendedor.lastIndexOf(s)) {
					arrayListaVendedor.remove(arrayListaVendedor.lastIndexOf(s));
				}
			}

			arrayListaVendedor.remove(0);

			Collections.sort(arrayListaVendedor);
			out.println(
					"<form method=\"POST\" enctype=\"multipart/form-data\" action=\"comissao\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"shadow p-3 mb-5 bg-white rounded\">");

			for (String vendedor : arrayListaVendedor) {
				out.println("<div class=\"form-check\">");
				out.println("<tbody>");
				out.println("<tr><th scope=\"row\">");
				out.println("<input class=\"form-check-input\" type=\"checkbox\" name=\""
						+ vendedor.toLowerCase().replaceAll(" ", "") + "\" id=\"itens\">");
				out.println("</th>");
				out.println("<td>");
				out.println("<label class=\"form-check-label\" for=\"defaultCheck1\">");
				out.println(vendedor);
				out.println("</label>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</tbody>");
				out.println("</div>");

			}
			out.println("</div>");
			out.println(
					"<div class=\"btn-group-lg rounded float-right\" role=\"group\" aria-label=\"Vamos para esquerda!\">");
			out.println(
					"<button type=\"submit\" value=\"okSeparaVendedor\" name=\"btnFormSeparaVendedor\" class=\"btn btn-primary btn-sm\">Próxima tela</button>");
			out.println("</div>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void escreveSeparaAltaCC(HttpServletResponse resp) {
		try {

			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");

			out.println(
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"charset=\"ISO-8859-1\"/>");

			out.println(
					"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"	integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\"/>");
			out.println(
					"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");

			out.println("</head>");
			out.println("<body>");

			out.println("<div class=\"container\">");

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			
			
			out.println("<div class=\"alert alert-light\" role=\"alert\">");
			out.println("<blockquote class=\"blockquote\">");
			out.println("<p class=\"mb-0\">");
			out.println("<button type=\"button\" onClick=\"window.location.href='/rondomotos/comissao'\" class=\"btn btn-primary\">Voltar</button>");
			out.println("</p>");
			out.println("</blockquote>");
			
			out.println("</div>");
			
			
			
			
			
			
			out.println("<div class=\"shadow-none p-3 mb-5 bg-light rounded\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Selecionar Apenas Veículos de Alta CC!</h1>");
			out.println("</div>");
			out.println("</div>");
			
			
			/*--------------Tabela--------------------------------*/

			Object[] st = arrayModelos.toArray();
			for (Object s : st) {
				if (arrayModelos.indexOf(s) != arrayModelos.lastIndexOf(s)) {
					arrayModelos.remove(arrayModelos.lastIndexOf(s));
				}
			}

			arrayModelos.remove(0);

			Collections.sort(arrayModelos);
			out.println(
					"<form method=\"POST\" enctype=\"multipart/form-data\" action=\"comissao\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"shadow p-3 mb-5 bg-white rounded\">");

			for (String modelos : arrayModelos) {
				out.println("<div class=\"form-check\">");
				out.println("<tbody>");
				out.println("<tr><th scope=\"row\">");
				out.println("<input class=\"form-check-input\" type=\"checkbox\" name=\""
						+ modelos.toLowerCase().replaceAll(" ", "") + "\" id=\"itens\">");
				out.println("</th>");
				out.println("<td>");
				out.println("<label class=\"form-check-label\" for=\"defaultCheck1\">");
				out.println(modelos);
				out.println("</label>");
				out.println("</td>");
				out.println("</tr>");
				out.println("</tbody>");
				out.println("</div>");

			}
			out.println("</div>");
			out.println(
					"<div class=\"btn-group-lg rounded float-right\" role=\"group\" aria-label=\"Vamos para esquerda!\">");
			out.println(
					"<button type=\"submit\" value=\"okSeparaModelo\" name=\"btnFormSeparaModelo\" class=\"btn btn-primary btn-sm\">Próxima tela</button>");
			out.println("</div>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void escreveProposta(HttpServletResponse resp) {
		try {

			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");

			out.println(
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"charset=\"ISO-8859-1\"/>");

			out.println(
					"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"	integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\"/>");
			out.println(
					"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			out.println(
					"<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");

			out.println("</head>");
			out.println("<body>");

			out.println("<div class=\"container\">");

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			
			
			
			out.println("<div class=\"alert alert-light\" role=\"alert\">");
			out.println("<blockquote class=\"blockquote\">");
			out.println("<p class=\"mb-0\">");
			out.println("<button type=\"button\" onClick=\"window.location.href='/rondomotos/comissao'\" class=\"btn btn-primary\">Voltar</button>");
			out.println("</p>");
			out.println("</blockquote>");
			
			out.println("</div>");
			
			
			
			
			
			
			out.println("<div class=\"shadow-none p-3 mb-5 bg-light rounded\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Comissão Finalizada!</h1>");
			out.println("</div>");
			out.println("</div>");

			/*--------------Tabela--------------------------------*/

			// out.println("<div class=\"shadow p-3 mb-5 bg-white rounded\">");

			for (String lh : escreverComissaoDuplicada.cidade) {
				
				out.println("<div class=\"alert alert-info\" role=\"alert\">");
				out.println("<blockquote class=\"blockquote text-center\">");
				out.println("<p class=\"mb-0\">");
				out.println(lh);
				out.println("</p>");
				out.println("</blockquote>");
				
				out.println("</div>");
				
				
				out.println("<table class=\"table-bordered-hover\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th scope=\"col\">Proposta</th>");
				out.println("<th scope=\"col\">Cliente</th>");
				out.println("<th scope=\"col\">Modelo</th>");
				out.println("<th scope=\"col\">Vendedor</th>");
				out.println("<th class=\"text-center\" scope=\"col\">Tabela</th>");
				out.println("<th class=\"text-center\" scope=\"col\">Val. Desc</th>");
				out.println("<th class=\"text-center\" scope=\"col\">%</th>");
				out.println("<th class=\"text-center\" scope=\"col\">Comissão</th>");
				out.println("<th class=\"text-center\" scope=\"col\">Margem</th>");
				out.println("<th class=\"text-center\" scope=\"col\">Total</th>");

				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>");

				DecimalFormat df = new DecimalFormat("###,##0.00");

				
				
				for (String propostas : escreverComissaoDuplicada.finalizado) {
					String[] linha = propostas.split(";");
					if(lh.contentEquals(linha[9])) {
						
						out.println("<tr>");
						out.println("<td scope=\"col\">" + linha[0] + "</td>");
						out.println("<td scope=\"col\">" + linha[1] + "</td>");
						out.println("<td scope=\"col\">" + linha[3] + "</td>");
						out.println("<td scope=\"col\">" + linha[4] + "</td>");
						Double db = Double.parseDouble(linha[5]);
						out.println("<td class=\"text-center\" scope=\"col\">" + df.format(db) + "</td>");
						Double db1 = Double.parseDouble(linha[6]);
						out.println("<td class=\"text-center\" scope=\"col\">" + df.format(db1) + "</td>");
						Double db2 = Double.parseDouble(linha[7]);
						out.println("<td class=\"text-center\" scope=\"col\">" + df.format(db2 - 100) + "</td>");
						out.println("<td class=\"text-center\" scope=\"col\">" + linha[8] + "</td>");
						Double db3 = Double.parseDouble(linha[10]);
						out.println("<td class=\"text-center\" scope=\"col\">" + df.format(db3) + "</td>");
						Double db4 = Double.parseDouble(linha[11]);
						out.println("<td class=\"text-center\" scope=\"col\">" + df.format(db4) + "</td>");
						out.println("</tr>");
						
					}
				}

				
				out.println("</tbody>");
				out.println("</table>");
				
				
			}

	
			out.println("</div>");

			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

