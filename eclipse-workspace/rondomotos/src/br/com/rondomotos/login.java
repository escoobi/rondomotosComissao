package br.com.rondomotos;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public login() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");


		if (consultaUsuario(req.getParameter("txtusuario"), req.getParameter("txtsenha"))) {
			RequestDispatcher rd = req.getRequestDispatcher("painelAdm");
			rd.forward(req, resp);
		} else {

		}

	}

	public void inserir(String usuario, String senha) {
		try {
			java.sql.Connection con = null;
			java.sql.Statement stm = null;
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "insert into usuarios(usuario, senha) VALUES ('" + usuario + "', '" + senha + "');";
			stm.executeUpdate(sql);
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public boolean consultaUsuario(String usuario, String senha) {
		try {
			Connection con = null;
			Statement stm = null;

			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection(
					"jdbc:firebirdsql://localhost:3050/C:\\rondomotos\\banco.fdb?lc_ctype=ISO8859_1", "sysdba",
					"masterkey");
			stm = con.createStatement();
			String sql = "select usuario, senha from usuarios where usuario='" + usuario + "' and senha='" + senha
					+ "'";
			java.sql.ResultSet rs = null;

			rs = stm.executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("usuario").contentEquals(usuario) && rs.getString("senha").contentEquals(senha)) {
					return true;
				}
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
