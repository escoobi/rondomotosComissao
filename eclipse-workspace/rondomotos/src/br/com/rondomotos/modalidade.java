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

/**
 * Servlet implementation class modalidade
 */
@WebServlet("/modalidade")
public class modalidade extends HttpServlet {

	/**
	 * 
	 */

	public static ArrayList<String> listaModalidade;
	private static String modalidade = null;
	private static String idExcluirModalidade = null;
	private static String idExcluir = null;
	private static String altModalidade = null;
	private static String idAltModalidade = null;

	private static final long serialVersionUID = 1L;

	public modalidade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			resp.setContentType("text/html");
			modalidade = req.getParameter("txtmodalidade");
			idExcluirModalidade = req.getParameter("btnExcluir");
			idExcluir = req.getParameter("btnApagarmodalidade");
			idAltModalidade = req.getParameter("btnEditar");
			altModalidade = req.getParameter("btnAlt");

			/*-----------------------Selecionar + Excluir------------------------------*/
			if (idExcluirModalidade != null) {

				obterModalidade(idExcluirModalidade);
				escreveModalidade(resp, false, false, true, false, false);
				idExcluirModalidade = null;
				modalidade = null;
				

			}

			if (idExcluir != null) {

				deletarModalidade(idExcluir);

				idExcluirModalidade = null;
				modalidade = null;
				
				idExcluir = null;
				escreveModalidade(resp, false, false, false, false, false);
			}

			/*-----------------------Selecionar + Alterar------------------------------*/

			if (idAltModalidade != null) {
				obterModalidade(idAltModalidade);
				escreveModalidade(resp, true, false, false, false, false);
				idExcluirModalidade = null;
				modalidade = null;
				
				idExcluir = null;
			}

			if (altModalidade != null) {
				modalidade = req.getParameter("txtmodalidadeAlt");

				alterarModalidade(altModalidade);
				idExcluirModalidade = null;
				modalidade = null;
				idExcluir = null;
				escreveModalidade(resp, false, false, false, false, false);
			}

			/*-----------------------Incluir + Vazio + Igual------------------------------*/
			if (modalidade != null) {

				if (consultaModalidade(modalidade)) {
					escreveModalidade(resp, false, false, false, true, false);

				} else {
					if (modalidade.length() > 1) {
						inserir(modalidade);
						escreveModalidade(resp, false, true, false, false, false);
						idExcluirModalidade = null;
						modalidade = null;
						idExcluir = null;
					} else {
						escreveModalidade(resp, false, false, false, false, true);
						idExcluirModalidade = null;
						modalidade = null;
						idExcluir = null;
					}
				}
			}

			/*----------------------------------------------------------------------------*/

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		escreveModalidade(resp, false, false, false, false, false);

	}

	public ArrayList<String> allModalidades() {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from modalidades";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			listaModalidade = new ArrayList<String>();
			while (rs.next()) {
				listaModalidade.add("<tbody>" + "<tr>" + "<th scope=\"row\">" + rs.getString("idmodalidade") + "</th>"
						+ "<td>" + rs.getString("modalidade") + "</td>" + "<td>" + "<button name=\"btnEditar\" value="
						+ rs.getString("idmodalidade")
						+ " type=\"submit\" class=\"btn btn-secondary btn-sm\" data-toggle=\"modal\" data-target=\"#modalAlt\">Editar"
						+ "</button>" + "</td>" + "<td>" + "<button name=\"btnExcluir\" value="
						+ rs.getString("idmodalidade")
						+ " type=\"submit\" class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#modalExcluir\">Excluir"
						+ "</button>" + "</td>" + "</tr>" + "</tbody>");

			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaModalidade;

	}

	public void inserir(String nomeModalidade) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into modalidades(modalidade) VALUES ('" + nomeModalidade + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultaModalidade(String nomeModalidade) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from modalidades where modalidade='" + nomeModalidade + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("modalidade").contentEquals(nomeModalidade)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public void deletarModalidade(String idmodalidade) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "delete from modalidades where idmodalidade='" + idmodalidade + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String obterModalidade(String idmodalidade) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from modalidades where idmodalidade ='" + idmodalidade + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			while (rs.next()) {
				modalidade = rs.getString("modalidade");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return modalidade;

	}

	public void alterarModalidade(String id) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "update modalidades set modalidade = '" + modalidade + "' where idmodalidade = '" + id + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void escreveModalidade(HttpServletResponse resp, boolean alterar, boolean inserir, boolean excluir,
			boolean jatem, boolean vazio) {
		try {

			allModalidades();
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
						"<div class=\"alert alert-danger\" role=\"alert\">Campo (<b>NOME modalidade</b>) não informado!</div>");
			}

			if (inserir == true) {
				out.println("<div class=\"alert alert-success\" role=\"alert\">Cadastro realizado com sucesso!</div>");
			}

			if (jatem == true) {

				out.println("<div class=\"alert alert-warning\" role=\"alert\">Cadastro já realizado!</div>");
			}

			/*------------Alterar-------------------*/

			if (alterar == true) {

				out.println("<form method=\"POST\" action=\"modalidade\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja alterar o registro!</h4>");
				out.println("<p>" + modalidade + "</p>");

				out.println("<div class=\"form-group\">");
				out.println(
						"<input type=\"text\" class=\"form-control\" id=\"txtmodalidadeAlt\" aria-describedby=\"txtmodalidade\" placeholder=\"nome modalidade\" name=\"txtmodalidadeAlt\">");
				out.println(
						"<small id=\"smlmodalidade\" class=\"form-text text-muted\">Informe o nome da modalidade para cadastro.</small>");
				out.println("</div>");

				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnAlt\" value=" + idAltModalidade
						+ " type=\"submit\" class=\"btn btn-primary\">Alterar</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*--------------Excluir-----------------*/

			if (excluir == true) {

				out.println("<form method=\"POST\" action=\"modalidade\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja excluir o registro!</h4>");
				out.println("<p>" + modalidade + "</p>");
				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnApagarmodalidade\" value=" + idExcluirModalidade
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

			out.println("<form method=\"POST\" action=\"modalidade\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Cadastro modalidade</h1>");
			out.println(
					"<input type=\"text\" class=\"form-control\" id=\"txtmodalidade\" aria-describedby=\"txtmodalidade\" placeholder=\"nome modalidade\" name=\"txtmodalidade\">");
			out.println(
					"<small id=\"smlmodalidade\" class=\"form-text text-muted\">Informe o nome da modalidade para cadastro.</small>");
			out.println("</div>");
			out.println(
					"<button type=\"submit\" class=\"btn btn-primary\"data-toggle=\"modal\" data-target=\"#modalExemplo\">Salvar</button>");
			out.println("</form>");

			/*--------------Tabela--------------------------------*/

			out.println("<form method=\"POST\" action=\"modalidade\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"table-responsive\">");
			out.println("<table id=\"tabela\" class=\"table table-striped\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th scope=\"col\">#</th>");
			out.println("<th scope=\"col\">Modalidade</th>");
			out.println("<th scope=\"col\"></th>");
			out.println("<th scope=\"col\"></th>");
			out.println("</tr>");
			out.println("</thead>");

			for (String linhaModalidade : listaModalidade) {
				out.println(linhaModalidade);
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
