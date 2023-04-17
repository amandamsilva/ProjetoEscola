package projeto.escola;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAOImpl implements NotaDAO {
	public final static String URL = "jdbc:mariadb://localhost:3307/notas";
	public final static String USER = "******";
	public final static String PASS = "******";
	private Connection con;
	
	public NotaDAOImpl() { 
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);		
		} catch(ClassNotFoundException | SQLException e) { 
			e.printStackTrace();
		}
	}

	@Override
	public void create(Nota n) {
		String sql = "INSERT INTO nota " +
				"(ra, N1, N2) " +
				"VALUES ('" + n.getRa() + 
				"', " + n.getN1() + 
				", '" + n.getN2() + "')";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
	}

	@Override
	public List<Nota> pesquisarPorRA(String ra) {
		List<Nota> lista = new ArrayList<>();
		String sql = "SELECT * FROM nota WHERE ra LIKE '%" + ra + "%'";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Nota n = new Nota();
				n.setRa(rs.getString("RA"));
				n.setN1(rs.getDouble("N1"));
				n.setN2(rs.getDouble("N2"));
				lista.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void apagar(Nota n) {
		String sql = "DELETE FROM nota " +
				"WHERE ra = '" + n.getRa() + "'";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
	}

	@Override
	public void atualizar(String raAntigo, Nota n) {
		String sql = "UPDATE nota SET n1 = '" + n.getN1() + 
				"', n2 = '" + n.getN2() + 
				"' WHERE ra = '" + raAntigo + "'";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
}
