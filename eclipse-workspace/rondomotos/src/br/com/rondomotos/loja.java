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
 * Servlet implementation class loja
 */
@WebServlet("/loja")

public class loja extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public static ArrayList<String> listaLoja;
	private static final long serialVersionUID = 1L;
	private static String nomeLoja = "";
	private static String idLoja = "";
	private static String ope = "";
	private static String edt = "";
	private static String alt = "";

	public loja() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			nomeLoja = req.getParameter("txtloja");
			idLoja = req.getParameter("btnExcluir");
			ope = req.getParameter("btnApagarLoja");
			edt = req.getParameter("btnEditar");
			alt = req.getParameter("btnAlt");
			resp.setContentType("text/html");
			allLojas();

			if (alt != null) {
				nomeLoja = req.getParameter("txtlojaAlt");
				alterarLoja(alt);
				escrevePg(resp, false, false, false, false, false);
				nomeLoja = null;
				idLoja = null;
				ope = null;
				edt = null;
				alt = null;
			}

			if (edt != null) {
				nomeLoja = obterNomeLoja(edt);
				escrevePg(resp, false, false, false, false, true);
				nomeLoja = null;
				idLoja = null;
				ope = null;
				// edt = null;
				// alt = null;
			}

			if (idLoja != null) {
				nomeLoja = obterNomeLoja(idLoja);
				escrevePg(resp, false, false, false, true, false);
				nomeLoja = null;
				idLoja = null;
				ope = null;
				edt = null;
				alt = null;
			}

			if (ope != null) {
				deletarLoja(ope);

				nomeLoja = null;
				idLoja = null;
				ope = null;
				edt = null;
				alt = null;
				escrevePg(resp, false, false, false, false, false);

			}
			if (nomeLoja != null) {

				idLoja = null;
				ope = null;
				edt = null;
				alt = null;
				if (consultaLoja(nomeLoja)) {

					escrevePg(resp, false, true, false, false, false);

				} else {
					if (nomeLoja.length() > 1) {
						inserir(nomeLoja);
						escrevePg(resp, false, false, true, false, false);
					}
					else {
						escrevePg(resp, true, false, false, false, false);
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		resp.setContentType("text/html");
		escrevePg(resp, false, false, false, false, false);

	}

	public void inserir(String nomeLoja) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into lojas(loja) VALUES ('" + nomeLoja + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultaLoja(String nomeLoja) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select loja from lojas where loja='" + nomeLoja + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("loja").contentEquals(nomeLoja)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public void escrevePg(HttpServletResponse resp, boolean vazioNome, boolean alteraNome, boolean cadOk,
			boolean excluirLoja, boolean edtLoja) {
		try {

			allLojas();
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

			if (vazioNome == true) {
				out.println(
						"<div class=\"alert alert-danger\" role=\"alert\">Campo (<b>NOME LOJA</b>) não informado!</div>");
			}

			if (cadOk == true) {
				out.println("<div class=\"alert alert-success\" role=\"alert\">Cadastro realizado com sucesso!</div>");
			}

			if (alteraNome == true) {

				/*------------ModalFader Alterar-------------------*/
				out.println("<div class=\"alert alert-warning\" role=\"alert\">Cadastro já realizado!</div>");
			}

			/*------------Alterar-------------------*/

			if (edtLoja == true) {

				out.println("<form method=\"POST\" action=\"loja\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja alterar o registro!</h4>");
				out.println("<p>" + nomeLoja + "</p>");

				out.println("<div class=\"form-group\">");
				out.println(
						"<input type=\"text\" class=\"form-control\" id=\"txtlojaAlt\" aria-describedby=\"txtloja\" placeholder=\"nome loja\" name=\"txtlojaAlt\">");
				out.println(
						"<small id=\"smlLoja\" class=\"form-text text-muted\">Informe o nome da loja para cadastro.</small>");
				out.println("</div>");

				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnAlt\" value=" + edt
						+ " type=\"submit\" class=\"btn btn-primary\">Alterar</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*--------------Excluir-----------------*/

			if (excluirLoja == true) {

				out.println("<form method=\"POST\" action=\"loja\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja excluir o registro!</h4>");
				out.println("<p>" + nomeLoja + "</p>");
				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnApagarLoja\" value=" + idLoja
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

			out.println("<form method=\"POST\" action=\"loja\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Cadastro loja</h1>");
			out.println(
					"<input type=\"text\" class=\"form-control\" id=\"txtloja\" aria-describedby=\"txtloja\" placeholder=\"nome loja\" name=\"txtloja\">");
			out.println(
					"<small id=\"smlLoja\" class=\"form-text text-muted\">Informe o nome da loja para cadastro.</small>");
			out.println("</div>");
			out.println(
					"<button type=\"submit\" class=\"btn btn-primary\"data-toggle=\"modal\" data-target=\"#modalExemplo\">Salvar</button>");
			out.println("</form>");

			/*--------------Tabela--------------------------------*/

			out.println("<form method=\"POST\" action=\"loja\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"table-responsive\">");
			out.println("<table id=\"tabela\" class=\"table table-striped\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th scope=\"col\">#</th>");
			out.println("<th scope=\"col\">Loja</th>");
			out.println("<th scope=\"col\"></th>");
			out.println("<th scope=\"col\"></th>");
			out.println("</tr>");
			out.println("</thead>");

			for (String linhaLoja : listaLoja) {
				out.println(linhaLoja);
			}

			out.println("</table>");
			out.println("</div>");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> allLojas() {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from lojas";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			listaLoja = new ArrayList<String>();
			while (rs.next()) {
				listaLoja.add("<tbody>" + "<tr>" + "<th scope=\"row\">" + rs.getString("idloja") + "</th>" + "<td>"
						+ rs.getString("loja") + "</td>" + "<td>" + "<button name=\"btnEditar\" value="
						+ rs.getString("idloja")
						+ " type=\"submit\" class=\"btn btn-secondary btn-sm\" data-toggle=\"modal\" data-target=\"#modalAlt\">Editar</button>"
						+ "</td>" + "<td>" + "<button name=\"btnExcluir\" value=" + rs.getString("idloja")
						+ " type=\"submit\" class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#modalExcluir\">Excluir</button>"
						+ "</td>" + "</tr>" + "</tbody>");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaLoja;

	}

	public String obterNomeLoja(String idLoja) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from lojas where idloja ='" + idLoja + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			while (rs.next()) {
				nomeLoja = rs.getString("loja");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return nomeLoja;

	}

	public void deletarLoja(String idLoja) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "delete from lojas where idloja='" + idLoja + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void alterarLoja(String id) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "update lojas set loja = '" + loja.nomeLoja + "' where idloja = '" + id + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
