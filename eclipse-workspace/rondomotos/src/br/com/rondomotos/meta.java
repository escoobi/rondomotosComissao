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


@WebServlet("/meta")
public class meta extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<String> listameta;
	public static ArrayList<String> listaLoja;
	public static ArrayList<String> listaModalidade;
	private static String meta = null;
	private static String venda = null;
	private static int ano = 0;
	private static int mes  = 0;
	private static int modalidade  = 0;
	private static int loja  = 0;
	private static String idExcluirmeta = null;
	private static String idExcluir = null;
	private static String altmeta = null;
	private static String idAltmeta = null;
	public meta() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		escrevemeta(resp, false, false, false, false, false);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			resp.setContentType("text/html");
			meta = req.getParameter("txtmeta");
			venda = req.getParameter("txtvenda");
			ano = Integer.parseInt(req.getParameter("txtano"));
			mes = Integer.parseInt(req.getParameter("txtmes"));
			modalidade = Integer.parseInt(req.getParameter("txtmodalidade"));
			loja = Integer.parseInt(req.getParameter("txtloja"));
			idExcluirmeta = req.getParameter("btnExcluir");
			idExcluir = req.getParameter("btnApagarmeta");
			idAltmeta = req.getParameter("btnEditar");
			altmeta = req.getParameter("btnAlt");

			/*-----------------------Selecionar + Excluir------------------------------*/
			if (idExcluirmeta != null) {

				obtermeta(idExcluirmeta);
				escrevemeta(resp, false, false, true, false, false);
				idExcluirmeta = null;
				meta = null;
				

			}

			if (idExcluir != null) {

				deletarmeta(idExcluir);

				idExcluirmeta = null;
				meta = null;
				
				idExcluir = null;
				escrevemeta(resp, false, false, false, false, false);
			}

			/*-----------------------Selecionar + Alterar------------------------------*/

			if (idAltmeta != null) {
				obtermeta(idAltmeta);
				escrevemeta(resp, true, false, false, false, false);
				idExcluirmeta = null;
				meta = null;
				
				idExcluir = null;
			}

			if (altmeta != null) {
				meta = req.getParameter("txtmetaAlt");

				alterarmeta(altmeta);
				idExcluirmeta = null;
				meta = null;
				idExcluir = null;
				escrevemeta(resp, false, false, false, false, false);
			}

			/*-----------------------Incluir + Vazio + Igual------------------------------*/
			if (meta != null) { 

				if (consultameta(meta)) {
					escrevemeta(resp, false, false, false, true, false);

				} else {
					if (meta.length() > 1) {
						inserir(loja, modalidade, ano, mes, meta, venda); 
						escrevemeta(resp, false, true, false, false, false);
						idExcluirmeta = null;
						meta = null;
						loja = 0;
						modalidade = 0;
						ano = 0;
						mes = 0;
						venda = null;
						idExcluir = null;
					} else {
						escrevemeta(resp, false, false, false, false, true);
						idExcluirmeta = null;
						meta = null;
						idExcluir = null;
					}
				}
			}

			/*----------------------------------------------------------------------------*/

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<String> allmetas() {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from metas";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			listameta = new ArrayList<String>();
			while (rs.next()) {
				listameta.add("<tbody>" + "<tr>" + "<th scope=\"row\">" + rs.getString("idmeta") + "</th>"
						+ "<td>" + rs.getString("meta") + "</td>" + "<td>" + "<button name=\"btnEditar\" value="
						+ rs.getString("idmeta")
						+ " type=\"submit\" class=\"btn btn-secondary btn-sm\" data-toggle=\"modal\" data-target=\"#modalAlt\">Editar"
						+ "</button>" + "</td>" + "<td>" + "<button name=\"btnExcluir\" value="
						+ rs.getString("idmeta")
						+ " type=\"submit\" class=\"btn btn-primary btn-sm\" data-toggle=\"modal\" data-target=\"#modalExcluir\">Excluir"
						+ "</button>" + "</td>" + "</tr>" + "</tbody>");

			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listameta;

	}

	public void inserir(int loja_id, int modalidade_id, int ano, int mes, String meta, String venda) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into metas(idloja, idmodalidade, ano, mes, meta, venda) VALUES ('" + loja_id + "', '" + modalidade_id + "', '" + ano + "','" + mes + "','" + meta + "', '" + venda + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultameta(String nomemeta) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from metas where meta='" + nomemeta + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("meta").contentEquals(nomemeta)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	public void deletarmeta(String idmeta) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "delete from metas where idmeta='" + idmeta + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String obtermeta(String idmeta) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select * from metas where idmeta ='" + idmeta + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);
			while (rs.next()) {
				meta = rs.getString("meta");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return meta;

	}

	public void alterarmeta(String id) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "update metas set meta = '" + meta + "' where idmeta = '" + id + "';";
			stm.executeUpdate(sql);

			con.close();
		} catch (Exception e) {
			// TODO: handle exception
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
				listaLoja.add("<option value="+rs.getString("idloja")+">"+rs.getString("loja")+"</option>");
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaLoja;

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
				listaModalidade.add("<option value="+rs.getString("idmodalidade")+">"+rs.getString("modalidade")+"</option>");

			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaModalidade;

	}

	public void escrevemeta(HttpServletResponse resp, boolean alterar, boolean inserir, boolean excluir,
			boolean jatem, boolean vazio) {
		try {

			allmetas();
			allLojas();
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
						"<div class=\"alert alert-danger\" role=\"alert\">Campo (<b>NOME meta</b>) não informado!</div>");
			}

			if (inserir == true) {
				out.println("<div class=\"alert alert-success\" role=\"alert\">Cadastro realizado com sucesso!</div>");
			}

			if (jatem == true) {

				out.println("<div class=\"alert alert-warning\" role=\"alert\">Cadastro já realizado!</div>");
			}

			/*------------Alterar-------------------*/

			if (alterar == true) {

				out.println("<form method=\"POST\" action=\"meta\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja alterar o registro!</h4>");
				out.println("<p>" + meta + "</p>");

				out.println("<div class=\"form-group\">");
				out.println(
						"<input type=\"text\" class=\"form-control\" id=\"txtmetaAlt\" aria-describedby=\"txtmeta\" placeholder=\"nome meta\" name=\"txtmetaAlt\">");
				out.println(
						"<small id=\"smlmeta\" class=\"form-text text-muted\">Informe o nome da meta para cadastro.</small>");
				out.println("</div>");

				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnAlt\" value=" + idAltmeta
						+ " type=\"submit\" class=\"btn btn-primary\">Alterar</button>");
				out.println("</div>");
				out.println("</form>");
			}

			/*--------------Excluir-----------------*/

			if (excluir == true) {

				out.println("<form method=\"POST\" action=\"meta\" accept-charset=\"ISO-8859-1\">");
				out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
				out.println("<h4 class=\"alert-heading\">Deseja excluir o registro!</h4>");
				out.println("<p>" + meta + "</p>");
				out.println("<hr>");
				out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
				out.println("<span aria-hidden=\"true\">&times;</span>");
				out.println("</button>");
				out.println(
						"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"alert\">Fechar</button>");
				out.println("<button name=\"btnApagarmeta\" value=" + idExcluirmeta
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

			out.println("<form method=\"POST\" action=\"meta\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"form-group\">");
			out.println("<h1 class=\"display-8\">Cadastro meta</h1>");
			
			
			
			/*txtloja*/
			out.println("<div class=\"input-group mb-3\">");
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\" for=\"inputGroupSelect01\">Loja</label>");
			out.println("</div>");
			out.println("<select name=\"txtloja\"  class=\"custom-select\" id=\"inputGroupSelect01\">");
			out.println("<option selected>Escolher...</option>");
			for (String loja : listaLoja) {
				out.println(loja);
			}
			
			out.println("</select>");
			out.println("</div>");
			
			
			
			/*txtmodalidade*/
			out.println("<div class=\"input-group mb-3\">");
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\" for=\"inputGroupSelect02\">Modalidade</label>");
			out.println("</div>");
			out.println("<select name=\"txtmodalidade\" class=\"custom-select\" id=\"inputGroupSelect02\">");
			out.println("<option selected>Escolher...</option>");
			for (String modalidade : listaModalidade) {
				out.println(modalidade);
			}
			
			out.println("</select>");
			out.println("</div>");
			
			/*txtmes*/
			out.println("<div class=\"input-group mb-3\">");
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\" for=\"inputGroupSelect03\">Mês</label>");
			out.println("</div>");
			out.println("<select  name=\"txtmes\" class=\"custom-select\" id=\"inputGroupSelect03\">");
			out.println("<option selected>Escolher...</option>");
			out.println("<option value=\"1\">Jan</option>");
			out.println("<option value=\"2\">Fev</option>");
			out.println("<option value=\"3\">Mar</option>");
			out.println("<option value=\"4\">Abr</option>");
			out.println("<option value=\"5\">Mai</option>");
			out.println("<option value=\"6\">Jun</option>");
			out.println("<option value=\"7\">Jul</option>");
			out.println("<option value=\"8\">Ago</option>");
			out.println("<option value=\"9\">Set</option>");
			out.println("<option value=\"10\">Out</option>");
			out.println("<option value=\"11\">Nov</option>");
			out.println("<option value=\"12\">Dez</option>");
			
			
			out.println("</select>");
			
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\">Ano</label>");
			out.println("</div>");
			/*txtano*/
			out.println(
					"<input type=\"text\" maxlength=\"4\" onkeypress=\"return event.charCode class=\"form-control\" id=\"txtano\" aria-describedby=\"txtano\" placeholder=\"Informar...\" name=\"txtano\">");
			
			out.println("</div>");
			
			
		
			
			out.println("<div class=\"input-group mb-3\">");
			
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\" for=\"inputGroupSelect04\">Meta</label>");
			out.println("</div>");
			/*txtmeta*/
			out.println(
					"<input type=\"number\" placeholder=\"0,00\" required name=\"txtmeta\" min=\"0\" step=\"0.01\" title=\"Currency\" pattern=\"^\\d+(?:\\.\\d{1,2})?$\" onblur=\"\r\n" + 
					"this.parentNode.parentNode.style.backgroundColor=/^\\d+(?:\\.\\d{1,2})?$/.test(this.value)?'inherit':'red'\r\n" + 
					"\" class=\"form-control\" id=\"txtmeta\" aria-describedby=\"txtmeta\" placeholder=\"Informar...\">");
			
			
			
			out.println("<div class=\"input-group-prepend\">");
			out.println("<label class=\"input-group-text\" for=\"inputGroupSelect05\">Venda</label>");
			out.println("</div>");
			/*txtvenda*/
			out.println(
					"<input type=\"number\" placeholder=\"0,00\" required name=\"txtvenda\" min=\"0\"  step=\"0.01\" title=\"Currency\" onblur=\"\r\n" + 
					"this.parentNode.parentNode.style.backgroundColor=/^\\d+(?:\\.\\d{1,2})?$/.test(this.value)?'inherit':'red'\r\n" + 
					"\" pattern=\"^\\d*(\\.\\d{0,2})?$\"  maxlength=\"12\"  class=\"form-control\" id=\"txtvenda\" aria-describedby=\"txtvenda\" placeholder=\"Informar...\">");
			out.println("</div>");
			
			out.println("</div>");
			out.println(
					"<button type=\"submit\" class=\"btn btn-primary\"data-toggle=\"modal\" data-target=\"#modalExemplo\">Salvar</button>");
			out.println("</form>");

			/*--------------Tabela--------------------------------*/

			out.println("<form method=\"POST\" action=\"meta\" accept-charset=\"ISO-8859-1\">");
			out.println("<div class=\"table-responsive\">");
			out.println("<table id=\"tabela\" class=\"table table-striped\">");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th scope=\"col\">#</th>");
			out.println("<th scope=\"col\">Meta</th>");
			out.println("<th scope=\"col\">Mês</th>");
			out.println("<th scope=\"col\">Realizado</th>");
			out.println("<th scope=\"col\"></th>");
			out.println("<th scope=\"col\"></th>");
			out.println("</tr>");
			out.println("</thead>");

			for (String linhameta : listameta) {
				out.println(linhameta);
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
