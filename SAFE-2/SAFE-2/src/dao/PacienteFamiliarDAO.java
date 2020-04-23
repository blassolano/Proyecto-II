package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PacienteFamiliarDAO {

	public void createTable() {
		// @formatter:off
		String PACIENTE_TABLE = "CREATE TABLE IF NOT EXISTS `paciente_familiar` ("
				 +" `id` int(11) NOT NULL AUTO_INCREMENT,"
				 +" `idPaciente` int(11) NOT NULL DEFAULT '0',"
				 +" `idFamiliar` int(11) NOT NULL DEFAULT '0',"
				 +" PRIMARY KEY (`id`),"
				 +" KEY `FK1_paciente` (`idPaciente`),"
				 +" KEY `FK2_familiar` (`idFamiliar`),"
				 +" CONSTRAINT `FK1_paciente` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`userId`),"
				 +" CONSTRAINT `FK2_familiar` FOREIGN KEY (`idFamiliar`) REFERENCES `user` (`id`)"
				+")"; 
		// @formatter:on

		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(PACIENTE_TABLE);
			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public void insert() {
		if (count() == 0) {
			String USER_INSERT = "INSERT INTO `paciente_familiar` (`idPaciente`, `idFamiliar`) VALUES\r\n"
					+ "	('4', '2');";

			Connection connection = Conexion.getConnection();
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(USER_INSERT);
				stmt.close();
				connection.close();
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
	
	public int count() {
		int total = 0;
		String COUNT_USER = "SELECT COUNT(*) as total FROM `paciente_familiar`";
		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(COUNT_USER);
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


		
			
}
