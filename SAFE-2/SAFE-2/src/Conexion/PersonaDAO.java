package Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Persona;

public class PersonaDAO {

	public Persona buscarUsuarioLogin(String correo, String contrasena) {
		Connection connection = Conexion.getConnection();
		Persona persona = null;

		try {
			PreparedStatement st = connection.prepareStatement("select * from persona where CORREO = ? and CONTRASEÑA = ?");
			st.setString(1, correo);
			st.setString(2, contrasena);

			ResultSet result = st.executeQuery();
			if (result.next()) {
				String nombre = result.getString("Nombre");
				String apellidos = result.getString("Apellidos");
				Integer dni = result.getInt("DNI");
				Date fecha = result.getDate("Nacimiento");
				String rol = result.getString("Rol");
				persona = new Persona();
				persona.setNombre(nombre);
				persona.setApellidos(apellidos);
				persona.setDNI(String.valueOf(dni));
				persona.setCorreo(correo);
				persona.setContrasena(contrasena);
				persona.setRol(rol);
				persona.setFecha(new Date(fecha.getTime()));
				System.out.println(persona.toString());
			}
			connection.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return persona;
	}
}
