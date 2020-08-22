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

@WebServlet("/cor")
public class cor extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> listacor;
	private static String cor = null;
	private static String idExcluircor = null;
	private static String idExcluir = null;
	private static String altcor = null;
	private static String idAltcor = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		escrevecor(resp, false, false, false, false, false);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			resp.setContentType("text/html");
			cor = req.getParameter("txtcor");
			idExcluircor = req.getParameter("btnExcluir");
			idExcluir = req.getParameter("btnApagarcor");
			idAltcor = req.getParameter("btnEditar");
			altcor = req.getParameter("btnAlt");

			/*-----------------------Selecionar + Excluir------------------------------*/
			if (idExcluircor != null) {

				obtercor(idExcluircor);
				escrevecor(resp, false, false, true, false, false);
				idExcluircor = null;
				cor = null;
				

			}

			if (idExcluir != null) {

				deletarcor(idExcluir);

				idExcluircor = null;
				cor = null;
				
				idExcluir = null;
				escrevecor(resp, false, false, false, false, false);
			}

			/*-----------------------Selecionar + Alterar------------------------------*/

			if (idAltcor != null) {
				obtercor(idAltcor);
				escrevecor(resp, true, false, false, false, false);
				idExcluircor = null;
				cor = null;
				
				idExcluir = null;
			}

			if (altcor != null) {
				cor = req.getParameter("txtcorAlt");

				alterarcor(altcor);
				idExcluircor = null;
				cor = null;
				idExcluir = null;
				escrevecor(resp, false, false, false, false, false);
			}

			/*-----------------------Incluir + Vazio + Igual------------------------------*/
			if (cor != null) {

				if (consultacor(cor)) {
					escrevecor(resp, false, false, false, true, false);

				} else {
					if (cor.length() > 1) {
						inserir(cor);
						escrevecor(resp, false, true, false, false, false);
						idExcluircor = null;
						cor = null;
						idExcluir = null;
					} else {
						escrevecor(resp, false, false, false, false, true);
						idExcluircor = null;
						cor = null;
						idExcluir = null;
					}
				}
			}

			/*----------------------------------------------------------------------------*/

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public cor() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> allcors() {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from cores";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			listacor = new ArrayList<String>();
			while (rs.next()) {
				listacor.add("<tbody>" + "<tr>" + "<th scope=\"row\">" + rs.getString("idcor") + "</th>"
						+ "<td>" + rs.getString("cor") + "</td>" + "<td>" + "<button name=\"btnEditar\" value="
						+ rs.getString("idcor")
						+ " type=\"submit\" class=\"btn btn-secondary btn-sm\" data-toggle=\"modal\" data-target=\"#modalAlt\">Editar"
						+ "</button>" + "</td>" + "<td>" + "<button name=\"btnExcluir\" value="
						+ rs.getString("idcor")
						+ " type=\"submit\" class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#modalExcluir\">Excluir"
						+ "</button>" + "</td>" + "</tr>" + "</tbody>");

			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listacor;

	}

	public void inserir(String nomecor) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into cores(cor) VALUES ('" + nomecor + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultacor(String nomecor) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from cores where cor='" + nomecor + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("cor").contentEquals(nomecor)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public void deletarcor(String idcor) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "delete from cores where idcor='" + idcor + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String obtercor(String idcor) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from cores where idcor ='" + idcor + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			while (rs.next()) {
				cor = rs.getString("cor");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cor;

	}

	public void alterarcor(String id) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "update cores set cor = '" + cor + "' where idcor = '" + id + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void escrevecor(HttpServletResponse resp, boolean alterar, boolean inserir, boolean excluir,
			boolean jatem, boolean vazio) {
		try {

			allcors();
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
						"<div class=\"alert alert-danger\" role=\"alert\">Campo (<b>NOME cor</b>) não informado!</div>");
			}

			if (inserir == true) {
				out.println("<div class=\"alert alert-success\" role=\"alert\">Cadastro realizado com sucesso!</div>");
			}

			if (jatem == true) {

				out.println("<div class=\"alert alert-warning\" role=\"alert\">Cadastro já realizado!</div>");
			}

			/*------------Alterar-------------------*/

			if (alterar == true) {

				out.println("<form method=\"POST\" action=\"cor\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja alterar o registro!</h4>");
				out.println("<p>" + cor + "</p>");

				out.println("<div class=\"form-group\">");
				out.println(
						"<input type=\"text\" class=\"form-control\" id=\"txtcorAlt\" aria-describedby=\"txtcor\" placeholder=\"nome cor\" name=\"txtcorAlt\">");
				out.println(
						"<small id=\"smlcor\" class=\"form-text text-muted\">Informe o nome da cor para cadastro.</small>");
				out.println("</div>");

				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnAlt\" value=" + idAltcor
						+ " type=\"submit\" class=\"btn btn-primary\">Alterar</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*--------------Excluir-----------------*/

			if (excluir == true) {

				out.println("<form method=\"POST\" action=\"cor\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja excluir o registro!</h4>");
				out.println("<p>" + cor + "</p>");
				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnApagarcor\" value=" + idExcluircor
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

			out.println("<form method=\"POST\" action=\"cor\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Cadastro cor</h1>");
			out.println(
					"<input type=\"text\" class=\"form-control\" id=\"txtcor\" aria-describedby=\"txtcor\" placeholder=\"nome cor\" name=\"txtcor\">");
			out.println(
					"<small id=\"smlcor\" class=\"form-text text-muted\">Informe o nome da cor para cadastro.</small>");
			out.println("</div>");
			out.println(
					"<button type=\"submit\" class=\"btn btn-primary\"data-toggle=\"modal\" data-target=\"#modalExemplo\">Salvar</button>");
			out.println("</form>");

			/*--------------Tabela--------------------------------*/

			out.println("<form method=\"POST\" action=\"cor\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"table-responsive\">");
			out.println("<table id=\"tabela\" class=\"table table-striped\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th scope=\"col\">#</th>");
			out.println("<th scope=\"col\">Cor</th>");
			out.println("<th scope=\"col\"></th>");
			out.println("<th scope=\"col\"></th>");
			out.println("</tr>");
			out.println("</thead>");

			for (String linhacor : listacor) {
				out.println(linhacor);
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
