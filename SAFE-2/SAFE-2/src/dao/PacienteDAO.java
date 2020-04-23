package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Familiar;
import model.Medico;
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
				 +" `id` int(20) NOT NULL AUTO_INCREMENT,"
				 +" `medicoId` int(20) NOT NULL,"
				 +" `userId` int(20) NOT NULL,"
				 +" `historial` varchar(1024) DEFAULT NULL,"
				 +" PRIMARY KEY (`id`),"
				 +" FOREIGN KEY(userId) REFERENCES user(id),"
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
		if (count() == 0) {
			// @formatter:off
			String PACIENTE_INSERT = "INSERT INTO `paciente` ( `medicoId`,  `userId`, `historial`) VALUES\r\n"
				+ "	('1', 4, 'hi1'),"
				+ "	('1', 5, 'hi2'),"
				+ "	('1', 6,  'hi3');";
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
	}

	public int count() {
		int total = 0;
		String COUNT_USER = "SELECT COUNT(*) as total FROM `paciente`";
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

	public void crearPaciente(Paciente paciente) {
		Medico medico = paciente.getMedico();
		long medicoId = medico.getId();

		try {
			int userId = userDAO.crearUsuario(paciente);
			String PACIENTE_INSERT = "INSERT INTO paciente ( `medicoId`,  `userId`) VALUES (?,?)";
			Connection connection = Conexion.getConnection();
			PreparedStatement st = connection.prepareStatement(PACIENTE_INSERT, Statement.RETURN_GENERATED_KEYS);
			st.setLong(1, medicoId);
			st.setLong(2, userId);

			st.executeUpdate();
			st.close();
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

	public List<Familiar> familiarPaciente(long idPaciente) {
		Connection connection = Conexion.getConnection();
		List<Familiar> lstFamiliar = new ArrayList<Familiar>();

		try {
			PreparedStatement st = connection.prepareStatement("select * from paciente_familiar where idPaciente = ?");
			st.setLong(1, idPaciente);

			ResultSet result = st.executeQuery();
			while (result.next()) {
				long idUser = result.getLong("idFamiliar");
				User user = userDAO.buscarUserId(idUser);
				Familiar familiar = new Familiar(user);
				lstFamiliar.add(familiar);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return lstFamiliar;
	}
}
