package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

import constant.Constant;

public class SupervisadoDAO {
	public void registroSupervisado(String nombre, String apellidos, String correo, String pass, String telefono,
			String direccion, String codigoEnlace) {
		
		Connection connection = Conexion.getConnection();
		String query = "INSERT INTO SUPERVISADO"
				+ "(NOMBRE, APELLIDO, CORREO, CONTRASENA, NUM_TELEFONO, DIRECCION, ENLACE, APROBADO) VALUES"
				+ "(?,?,?,?,?,?,?,?);";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, apellidos);
			preparedStatement.setString(3, correo);
			preparedStatement.setString(4, pass);
			preparedStatement.setString(5, telefono);
			preparedStatement.setString(6, direccion);
			preparedStatement.setString(7, codigoEnlace);
			preparedStatement.setString(8, "no");

			// execute insert SQL statement
			preparedStatement.executeUpdate();

			System.out.println("Registrado en la tabla supervisado con exito");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
