package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;
import model.User;

public class PacienteDAO {
	private UserDAO userDAO;

	public PacienteDAO() {
		this.userDAO = new UserDAO();
	}

	public void createTable() {
		// @formatter:off
		String PACIENTE_TABLE = "CREATE TABLE IF NOT EXISTS `paciente` ("
				 +" `id` int(20) NOT NULL,"
				 +" `medicoId` bigint(20) NOT NULL DEFAULT '0',"
				 +" `userId` bigint(20) NOT NULL DEFAULT '0',"
				 +" `familiarId` bigint(20) NOT NULL DEFAULT '0',"
				 +" `historial` varchar(1024) DEFAULT NULL,"
				 +" PRIMARY KEY (`id`),"
				 +" FOREIGN KEY(userId) REFERENCES user(id)"
				 +" FOREIGN KEY(medicoId) REFERENCES user(id)"
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
		// @formatter:off
		String PACIENTE_INSERT = "INSERT INTO `paciente` (`id`, `medicoId`, `userId`,  `familiarId`, `historial`) VALUES\r\n"
				+ "	(1,'1', 4, 2, 'hi1'),"
				+ "	(2,'1', 5, 2, 'hi2'),"
				+ "	(3,'1', 6, 2, 'hi3');";
				
		// @formatter:on
		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(PACIENTE_INSERT);
			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public List<Paciente> buscarPacientes(long medicoId) {
		Connection connection = Conexion.getConnection();
		List<Paciente> lstPaciente = new ArrayList<Paciente>();

		try {
			PreparedStatement st = connection.prepareStatement("select * from paciente where medicoId = ?");
			st.setLong(1, medicoId);

			ResultSet result = st.executeQuery();
			while (result.next()) {
				String historial = result.getString("historial");
				long pacienteid = result.getLong("userId");
				User user = userDAO.buscarUserId(pacienteid);
				Paciente paciente = new Paciente(user);
				paciente.setId(pacienteid);
				paciente.setHistorial(historial);
				
				lstPaciente.add(paciente);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return lstPaciente;
	}
	
	public List<Paciente> pacientesDelFamiliar(long idFamiliar){
		Connection connection = Conexion.getConnection();
		List<Paciente> lstPaciente = new ArrayList<Paciente>();

		try {
			PreparedStatement st = connection.prepareStatement("select * from paciente where familiarId = ?");
			st.setLong(1, idFamiliar);

			ResultSet result = st.executeQuery();
			while (result.next()) {
				String historial = result.getString("historial");
				long pacienteid = result.getLong("userId");
				User user = userDAO.buscarUserId(pacienteid);
				Paciente paciente = new Paciente(user);
				paciente.setId(pacienteid);
				paciente.setHistorial(historial);
				
				lstPaciente.add(paciente);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return lstPaciente;
	}
}
