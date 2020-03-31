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
				 +" `id` int(20) NOT NULL,"
				 +" `dni` varchar(20) NOT NULL,"
				 +" `name` varchar(255) DEFAULT NULL,"
				 +" `surname` varchar(255) DEFAULT NULL,"
				 +" `email` varchar(255) DEFAULT NULL,"
				 +" `password` varchar(255) DEFAULT NULL,"
				 +" `telephone` VARCHAR(20) DEFAULT NULL,"
				 +" `date` date DEFAULT NULL,"
				 +" `rolId` bigint(20) NOT NULL DEFAULT '0',"
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
		String USER_INSERT = "INSERT INTO `user` (`id`, `dni`, `name`, `surname`, `email`, `password`, `telephone`, `date`,  `rolId`) VALUES\r\n"
				+ "	(1,'65342890k', 'blas', 'solano', 'blassolano20@gmail.com', 'blas22', '563241578', datetime('now'), 1),"
				+ "	(2,'54216889P', 'julio', 'serrano', 'julioserrano20@gmail.com', 'julio22', '523698421', datetime('now'), 2),"
				+ "	(4,'21365421L', 'claudia', 'pero', 'claudia20@gmail.com', 'claudia22', '231458264', datetime('now'), 3),"
				+ "	(5,'21365421L',  'pedro', 'oper', 'pedro@gmail.com', 'pedro77', '354268995', datetime('now'), 3),"
				+ "	(6,'21365421L',  'ramon', 'sincro', 'ramon@gmail.com', 'ramon77', '120345887', datetime('now'), 3),"
				+ "	(7, '12985637u', 'solange', 'mendez', 'solange20@gmail.com', 'solange25', '782543015', datetime('now'), 4);";

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
				user.setRole(role);
				System.out.println(user);
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return user;
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
