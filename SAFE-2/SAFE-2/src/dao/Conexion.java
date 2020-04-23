package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

	public static Connection getConnection() {
		Connection connect = null;
		try {
			//Class.forName("org.sqlite.JDBC");
			Class.forName("com.mysql.jdbc.Driver");
			//connect = DriverManager.getConnection("jdbc:sqlite:" + Constant.URL_DB);
			// connect = DriverManager.getConnection("jdbc:mysql://esp.uem.es:3306/jserrano", "JulioSerrano", "serrano123");
			 connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/safe?createDatabaseIfNotExist=true&serverTimezone=UTC&useSSL=false", "root", "root");

		} catch (Exception e) {
			System.err.println("No se ha podido conectar a la base de datos\n" + e.getMessage());
		}
		return connect;
	}

}
