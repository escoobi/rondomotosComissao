package br.com.rondomotos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class admPainel
 */
@WebServlet("/painelAdm")
public class painelAdm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	public static String senha = null;
	public static String usuario = null;
	public static int k = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public painelAdm() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);

		senha = req.getParameter("txtsenha");
		usuario = req.getParameter("txtusuario");
		k = tipoUser(usuario, senha);
		resp.setContentType("text/html");
		

		Object asdsa = req.getAttribute("toma");
		
		
		if (k == 1 || asdsa == "eita")

		{
		escreveAdmPainel(resp);

		} else {
			if (k == 2) {
			

			} else {
				if (k == 0) {



				}

			}

		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");	
		escreveAdmPainel(resp);
	}

	public int tipoUser(String usuario, String senha) {

		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select tipo from usuarios where usuario='" + usuario + "' and senha='" + senha + "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("tipo").contentEquals("2")) {
					return 2;
				} else {
					if (rs.getString("tipo").contentEquals("1")) {
						return 1;
					}
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	public void adm() {

	}

	
	
	public void escreveAdmPainel(HttpServletResponse resp) {
		
		try {
			PrintWriter out = resp.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"pt-br\">");
			out.println("<head>");
			out.println("<meta charset=\"ISO-8859-1\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
			out.println("<link rel=\"icon\" href=\"http://www.rondomotos.com.br/images/favicons/favicon-honda-2w.ico\" />");
			out.println("<title>Rondo Motos - App v1</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"container\">");
			
			
			out.println("<div class=\"media\">");
			out.println("<img src=\"rocket-outline.svg\" width=\"64\" height=\"64\" class=\"mr-3\" alt=\"Grupo Rondo Motos\"/></a>");
			out.println("<div class=\"media-body\">");
			out.println("<h5 class=\"mt-3 \">Rondo Motos - App v1</h5>");
			out.println("</div>");
			out.println("</div>");
			
			

			out.println("<div class=\"list-group \">");
			
			
			
			out.println("<a class=\"list-group-item list-group-item-action\"\r\n" + 
					"				href=\"#\"><img src=\"home-outline.svg\" width=\"30\" height=\"30\"\r\n" + 
					"				class=\"d-inline-block align-top\" alt=\"\"> Incio</a>\r\n" + 
					"");
			
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"meta\"><img src=\"graph.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Meta</a>");
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"loja\"><img src=\"icons8-loja-48.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Loja</a>");
		
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"modalidade\"><img src=\"quote.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Modalidade</a>");
		
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"modelo\"><img src=\"bicycle-outline.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Modelo</a>");
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"cor\"><img src=\"color-palette-outline.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Cor</a>");
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"consorciado\"><img src=\"fire.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Consorciado</a>");
			
			out.println(
					"<a class=\"list-group-item list-group-item-action\" href=\"#\"><img src=\"people-outline.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\">	Usuário</a>");
			out.println("</div");

			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
