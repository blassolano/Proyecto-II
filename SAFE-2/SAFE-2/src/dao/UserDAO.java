package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Role;
import model.User;

public class UserDAO {
	private RolDAO rolDAO;

	public UserDAO() {
		this.rolDAO = new RolDAO();
	}

	public void createTable() {
		// @formatter:off
		String USER_TABLE = "CREATE TABLE IF NOT EXISTS `user` ("
				 +" `id` int(20) NOT NULL AUTO_INCREMENT,"
				 +" `dni` varchar(20) NOT NULL,"
				 +" `name` varchar(255) DEFAULT NULL,"
				 +" `surname` varchar(255) DEFAULT NULL,"
				 +" `email` varchar(255) DEFAULT NULL,"
				 +" `password` varchar(255) DEFAULT NULL,"
				 +" `telephone` VARCHAR(20) DEFAULT NULL,"
				 +" `date` date DEFAULT NULL,"
				 +" `rolId` int(20) NOT NULL DEFAULT '0',"
				 +" PRIMARY KEY (`id`),"
				 +" FOREIGN KEY(rolId) REFERENCES role(id)"
				+")"; 
		// @formatter:on

		Connection connection = Conexion.getConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(USER_TABLE);
			stmt.close();
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void insert() {
		if (count() == 0) {
			String USER_INSERT = "INSERT INTO `user` (`dni`, `name`, `surname`, `email`, `password`, `telephone`, `date`,  `rolId`) VALUES\r\n"
					+ "	('65342890k', 'blas', 'solano', 'blassolano20@gmail.com', 'blas22', '563241578', NOW(), 1),"
					+ "	('54216889P', 'julio', 'serrano', 'julioserrano20@gmail.com', 'julio22', '523698421', NOW(), 2),"
					+ "	('21365421L', 'claudia', 'pero', 'claudia20@gmail.com', 'claudia22', '231458264', NOW(), 3),"
					+ "	('21365421L',  'pedro', 'oper', 'pedro@gmail.com', 'pedro77', '354268995', NOW(), 3),"
					+ "	('21365421L',  'ramon', 'sincro', 'ramon@gmail.com', 'ramon77', '120345887', NOW(), 3),"
					+ "	('12985637u', 'solange', 'mendez', 'solange20@gmail.com', 'solange25', '782543015', NOW(), 4);";

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
		String COUNT_USER = "SELECT COUNT(*) as total FROM `user`";
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

	public int crearUsuario(User user) {
		int userId = 0;
		Connection connection = Conexion.getConnection();
		String USER_INSERT = "INSERT INTO `user` (`dni`, `name`, `surname`, `email`, `password`, `telephone`, `date`,  `rolId`) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

		try {
			PreparedStatement st = connection.prepareStatement(USER_INSERT, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getDNI());
			st.setString(2, user.getNombre());
			st.setString(3, user.getApellidos());
			st.setString(4, user.getCorreo());
			st.setString(5, user.getContrasena());
			st.setString(6, user.getTelefono());

			Role role = user.getRole();
			st.setLong(7, role.getId());

			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				userId = rs.getInt(1);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return userId;
	}

	public User buscarUsuarioLogin(String correo, String contrasena) {
		Connection connection = Conexion.getConnection();
		User user = null;

		try {
			PreparedStatement st = connection.prepareStatement("select * from user where email = ? and password = ?");
			st.setString(1, correo);
			st.setString(2, contrasena);

			ResultSet result = st.executeQuery();
			if (result.next()) {
				int id = result.getInt("id");
				String dni = result.getString("dni");
				String name = result.getString("name");
				String surname = result.getString("surname");
				String telefono = result.getString("telephone");

				Date date = null;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = df.parse(result.getString("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int rolId = result.getInt("rolId");
				Role role = rolDAO.findById(rolId);

				user = new User();
				user.setId(id);
				user.setDNI(dni);
				user.setNombre(name);
				user.setApellidos(surname);
				user.setCorreo(correo);
				user.setContrasena(contrasena);
				user.setFecha(date);
				user.setTelefono(telefono);
				user.setRole(role);
				System.out.println(user);
			}

			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
	}

	public int countUser(String email) {
		Connection connection = Conexion.getConnection();
		int numPaciente = 0;
		String COUNT_USER = "SELECT COUNT(*) AS numUser FROM user where email LIKE ?";

		try {
			PreparedStatement st = connection.prepareStatement(COUNT_USER);
			st.setString(1, email);

			ResultSet result = st.executeQuery();
			if (result.next()) {
				numPaciente = result.getInt("numUser");
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return numPaciente;
	}

	public User buscarUserId(long pacienteid) {
		Connection connection = Conexion.getConnection();
		User user = null;

		try {
			PreparedStatement st = connection.prepareStatement("select * from user where id = ?");
			st.setLong(1, pacienteid);

			ResultSet result = st.executeQuery();
			if (result.next()) {
				int id = result.getInt("id");
				String dni = result.getString("dni");
				String name = result.getString("name");
				String surname = result.getString("surname");
				String email = result.getString("email");
				String password = result.getString("password");
				String telephone = result.getString("telephone");

				Date date = null;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = df.parse(result.getString("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int rolId = result.getInt("rolId");
				Role role = rolDAO.findById(rolId);

				user = new User();
				user.setId(id);
				user.setDNI(dni);
				user.setNombre(name);
				user.setApellidos(surname);
				user.setCorreo(email);
				user.setContrasena(password);
				user.setFecha(date);
				user.setRole(role);
				user.setTelefono(telephone);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
	}
}
