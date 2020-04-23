package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Role;

public class RolDAO {

	public void createTable() {
		// @formatter:off
		String ROLE_TABLE = "CREATE TABLE IF NOT EXISTS `role` (" + 
				"  `id` int(20) NOT NULL AUTO_INCREMENT," + 
				"  `role_name` varchar(255) DEFAULT NULL," + 
				"  PRIMARY KEY (`id`)" + 
				")";
		// @formatter:on

		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(ROLE_TABLE);
			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void insert() {
		if (count() == 0) {
		// @formatter:off
		String ROL_INSERT = "INSERT INTO `role` (`role_name`) VALUES\r\n" 
				+ "	('Medico'),"
				+ "	('Familiar')," 
				+ "	('Paciente')," 
				+ "	('Administrador')";
		// @formatter:on
			Connection connection = Conexion.getConnection();
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(ROL_INSERT);
				stmt.close();
				connection.close();
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	public int count() {
		int total = 0;
		String COUNT_ROLE = "SELECT COUNT(*) as total FROM `role`";
		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(COUNT_ROLE);
			if (result.next()) {
				total = result.getInt("total");
			}

			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return total;
	}

	public Role findById(int id) {
		Role role = null;
		Connection connection = Conexion.getConnection();
		try {
			PreparedStatement st = connection.prepareStatement("select * from role where id = ?");
			st.setInt(1, id);
			ResultSet result = st.executeQuery();
			if (result.next()) {
				int idRol = result.getInt("id");
				String roleName = result.getString("role_name");

				role = new Role();
				role.setId(idRol);
				role.setRoleName(roleName);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return role;
	}

	public Role findByName(String name) {
		Role role = null;
		Connection connection = Conexion.getConnection();
		try {
			PreparedStatement st = connection.prepareStatement("select * from role where role_name = ?");
			st.setString(1, name);
			ResultSet result = st.executeQuery();
			if (result.next()) {
				int idRol = result.getInt("id");
				String roleName = result.getString("role_name");

				role = new Role();
				role.setId(idRol);
				role.setRoleName(roleName);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return role;
	}
}
