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
 * Servlet implementation class Cnh
 */
@WebServlet("/Cnh")
public class cnh extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public cnh() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);

		String senha = req.getParameter("txtsenha");
		String usuario = req.getParameter("txtusuario");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"pt-br\">");
		out.println(" <head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
		out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
		out.println("<link href=\"css/login.css\" rel=\"stylesheet\">");
		out.println("<title>Rondo Motos - Dealer</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("");
		out.println("");
		out.println("");
		
		
		
		
		
		int k = tipoUser(usuario, senha);
		if (k == 1) {
			out.println("Vc é ADM<h3>" + usuario + "</h3>");
			out.println("<ul class=\"list-group\">");
			out.println("<li class=\"list-group-item\">Cras justo odio</li>");
			out.println("<li class=\"list-group-item\">Dapibus ac facilisis in</li>");
			out.println("<li class=\"list-group-item\">Morbi leo risus</li>");
			out.println("<li class=\"list-group-item\">Morbi leo risus</li>");
			out.println("<li class=\"list-group-item\">Vestibulum at eros</li>");
			out.println("</ul>");
			
			out.close();
		} else {
			if (k == 2) {
				out.println("Vc é REL<h3>" + usuario + "</h3>");
				out.close();
			} else {
				out.println("Bem Vindo<h3>" + usuario + "</h3>");
				out.close();
			}

		}

		
		out.println(" <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
		out.println("   <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>");
		out.println(" <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
		out.println(" </body>");
		out.println("</html>");
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

	public void rel() {
		
	}
	
}
