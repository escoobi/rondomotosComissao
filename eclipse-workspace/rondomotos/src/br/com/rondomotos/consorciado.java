package br.com.rondomotos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/consorciado")
public class consorciado extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> listaconsorciado;
	private static String consorciado = null;
	private static String cpf = null;
	private static String contato = null;
	private static String idExcluirconsorciado = null;
	private static String idExcluir = null;
	private static String altconsorciado = null;
	private static String idAltconsorciado = null;

	public consorciado() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		escreveconsorciado(resp, false, false, false, false, false);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			resp.setContentType("text/html");
			consorciado = req.getParameter("txtconsorciado");
			cpf = req.getParameter("txtcpf");
			contato = req.getParameter("txtcontato");
			idExcluirconsorciado = req.getParameter("btnExcluir");
			idExcluir = req.getParameter("btnApagarconsorciado");
			idAltconsorciado = req.getParameter("btnEditar");
			altconsorciado = req.getParameter("btnAlt");

			/*-----------------------Selecionar + Excluir------------------------------*/
			if (idExcluirconsorciado != null) {

				obterconsorciado(idExcluirconsorciado);
				escreveconsorciado(resp, false, false, true, false, false);
				idExcluirconsorciado = null;
				consorciado = null;
				cpf = null;
				contato = null;
				

			}

			if (idExcluir != null) {

				deletarconsorciado(idExcluir);

				idExcluirconsorciado = null;
				consorciado = null;
				cpf = null;
				contato = null;
				idExcluir = null;
				escreveconsorciado(resp, false, false, false, false, false);
			}

			/*-----------------------Selecionar + Alterar------------------------------*/

			if (idAltconsorciado != null) {
				obterconsorciado(idAltconsorciado);
				escreveconsorciado(resp, true, false, false, false, false);
				idExcluirconsorciado = null;
				consorciado = null;
				cpf = null;
				contato = null;
				idExcluir = null;
			}

			if (altconsorciado != null) {
				contato = req.getParameter("txtcontatoAlt");

				alterarconsorciado(altconsorciado);
				idExcluirconsorciado = null;
				consorciado = null;
				idExcluir = null;
				cpf = null;
				contato = null;
				escreveconsorciado(resp, false, false, false, false, false);
			}

			/*-----------------------Incluir + Vazio + Igual------------------------------*/
			if (consorciado != null) {

				if (consultaconsorciado(consorciado)) {
					escreveconsorciado(resp, false, false, false, true, false);

				} else {
					if (consorciado.length() > 1) {
						inserir(consorciado, cpf, contato);
						escreveconsorciado(resp, false, true, false, false, false);
						idExcluirconsorciado = null;
						consorciado = null;
						idExcluir = null;
						cpf = null;
						contato = null;
					} else {
						escreveconsorciado(resp, false, false, false, false, true);
						idExcluirconsorciado = null;
						consorciado = null;
						idExcluir = null;
						cpf = null;
						contato = null;
					}
				}
			}

			/*----------------------------------------------------------------------------*/

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<String> allconsorciados() {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from consorciados";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			listaconsorciado = new ArrayList<String>();
			while (rs.next()) {
				listaconsorciado.add("<tbody>" + "<tr>" + "<th scope=\"row\">" + rs.getString("idconsorciado") + "</th>"
						+ "<td>" + rs.getString("consorciado") + "</td>" +  "<td>" +rs.getString("cpf")+ "</td><td>"+rs.getString("contato")+ "</td>" + "<td><button name=\"btnEditar\" value="
						+ rs.getString("idconsorciado")
						+ " type=\"submit\" class=\"btn btn-secondary btn-sm\" data-toggle=\"modal\" data-target=\"#modalAlt\">Editar"
						+ "</button>" + "</td>" + "<td>" + "<button name=\"btnExcluir\" value="
						+ rs.getString("idconsorciado")
						+ " type=\"submit\" class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#modalExcluir\">Excluir"
						+ "</button>" + "</td>" + "</tr>" + "</tbody>");

			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaconsorciado;

	}

	public void inserir(String nomeconsorciado, String cpf, String contato) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into consorciados(consorciado, cpf, contato) VALUES ('" + nomeconsorciado + "', '" + cpf + "', '" + contato + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultaconsorciado(String nomeconsorciado) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from consorciados where consorciado='" + nomeconsorciado + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("consorciado").contentEquals(nomeconsorciado)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public void deletarconsorciado(String idconsorciado) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "delete from consorciados where idconsorciado='" + idconsorciado + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String obterconsorciado(String idconsorciado) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from consorciados where idconsorciado ='" + idconsorciado + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			while (rs.next()) {
				consorciado = rs.getString("consorciado");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return consorciado;

	}

	public void alterarconsorciado(String id) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "update consorciados set contato = '" + contato + "' where idconsorciado = '" + id + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void escreveconsorciado(HttpServletResponse resp, boolean alterar, boolean inserir, boolean excluir,
			boolean jatem, boolean vazio) {
		try {

			allconsorciados();
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

			if (vazio == true) {
				out.println(
						"<div class=\"alert alert-danger\" role=\"alert\">Campo (<b>NOME consorciado</b>) não informado!</div>");
			}

			if (inserir == true) {
				out.println("<div class=\"alert alert-success\" role=\"alert\">Cadastro realizado com sucesso!</div>");
			}

			if (jatem == true) {

				out.println("<div class=\"alert alert-warning\" role=\"alert\">Cadastro já realizado!</div>");
			}

			/*------------Alterar-------------------*/

			if (alterar == true) {

				out.println("<form method=\"POST\" action=\"consorciado\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja alterar o registro!</h4>");
				out.println("<p>" + consorciado + "</p>");

				out.println("<div class=\"form-group\">");
				out.println(
						"<input type=\"text\" class=\"form-control\"  maxlength=\"11\" onkeypress=\"return event.charCode >= 48 && event.charCode <= 57\" id=\"txtcontadoAlt\" aria-describedby=\"txtcontato\" placeholder=\"contato consorciado\" name=\"txtcontatoAlt\">");
				out.println(
						"<small id=\"smlcontato\" class=\"form-text text-muted\">Informe o contato do consorciado para alterar.</small>");
				out.println("</div>");

				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnAlt\" value=" + idAltconsorciado
						+ " type=\"submit\" class=\"btn btn-primary\">Alterar</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*--------------Excluir-----------------*/

			if (excluir == true) {

				out.println("<form method=\"POST\" action=\"consorciado\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja excluir o registro!</h4>");
				out.println("<p>" + consorciado + "</p>");
				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnApagarconsorciado\" value=" + idExcluirconsorciado
						+ " type=\"submit\" class=\"btn btn-primary\">Excluir</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*-------------Cabeçalho------------------*/

			out.println("<div class=\"media\">");
			out.println(
					"<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\" />");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3\">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");

			out.println("<div class=\"list-group text-center\">");
			out.println("<a class=\"list-group-item list-group-item-action\" href=\"painelAdm\">");
			out.println(
					"<img src=\"return-down-back-outline.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\"/>");
			out.println("</a>");
			out.println("</div>");

			/*----------------Formulario--------------------*/

			out.println("<form method=\"POST\" action=\"consorciado\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Cadastro consorciado</h1>");
			out.println(
					"<input type=\"text\" class=\"form-control\" id=\"txtconsorciado\" aria-describedby=\"txtconsorciado\" placeholder=\"nome consorciado\" name=\"txtconsorciado\">");
			out.println(
					"<small id=\"smlconsorciado\" class=\"form-text text-muted\">Informe o nome do consorciado para cadastro.</small>");
			
			out.println("<div class=\"form-row\">");
			out.println("<div class=\"col\">");
			out.println(
					"<input type=\"text\" maxlength=\"11\" onkeypress=\"return event.charCode >= 48 && event.charCode <= 57\" class=\"form-control\" id=\"txtcpf\" aria-describedby=\"txtcpf\" placeholder=\"cpf consorciado\" name=\"txtcpf\">");
			out.println(
					"<small id=\"smlcpf\" class=\"form-text text-muted\">Informe o cpf do consorciado para cadastro.</small>");
			out.println("</div>");
			out.println("<div class=\"col\">");
			out.println(
					"<input type=\"text\" maxlength=\"11\" onkeypress=\"return event.charCode >= 48 && event.charCode <= 57\"  class=\"form-control\" id=\"txtcontato\" aria-describedby=\"txtcontato\" placeholder=\"contato consorciado\" name=\"txtcontato\">");
			out.println(
					"<small id=\"smlcontato\" class=\"form-text text-muted\">Informe o contato do consorciado para cadastro.</small>");
			out.println("</div>");
			out.println("</div>");
			out.println("</div>");
			
			out.println(
					"<button type=\"submit\" class=\"btn btn-primary\"data-toggle=\"modal\" data-target=\"#modalExemplo\">Salvar</button>");
			out.println("</form>");

			/*--------------Tabela--------------------------------*/

			out.println("<form method=\"POST\" action=\"consorciado\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"table-responsive\">");
			out.println("<table id=\"tabela\" class=\"table table-striped\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th scope=\"col\">#</th>");
			out.println("<th scope=\"col\">Consorciado</th>");
			out.println("<th scope=\"col\">Cpf</th>");
			out.println("<th scope=\"col\">Contato</th>");
			out.println("<th scope=\"col\"></th>");
			out.println("<th scope=\"col\"></th>");
			out.println("</tr>");
			out.println("</thead>");

			for (String linhaconsorciado : listaconsorciado) {
				out.println(linhaconsorciado);
			}

			out.println("</table>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
}
