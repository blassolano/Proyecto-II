package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import constant.Constant;
import model.DatosSensor;
import model.User;

public class DatosSensorDAO {

	public int numerosensores() {
		Connection connection = Conexion.getConnection();
		int numerosensores = 0;

		try {
			PreparedStatement st = connection.prepareStatement("select count(*) as total from datossensores");
			ResultSet result = st.executeQuery();
			if (result.next()) {
				numerosensores = result.getInt("total");
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return numerosensores;
	}

	public void insertarValorSensor(DatosSensor valorsensor) {
		Connection connection = Conexion.getConnection();
		int numerosensores = numerosensores();

		String query = "INSERT into datossensores (id, fecha, dato, sensor_key, dni_paciente) VALUES (?,?,?,?,?);";

		try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, (numerosensores + 1));
			preparedStatement.setString(2, valorsensor.getFecha());
			preparedStatement.setString(3, valorsensor.getDato());
			preparedStatement.setInt(4, valorsensor.getSensores_key());
			preparedStatement.setInt(5, valorsensor.getDni_paciente());

			preparedStatement.executeUpdate();

			System.out.println("Registrado en la tabla datossensores con éxito");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<DatosSensor> recogerSensores(User persona) {
		Connection connection = Conexion.getConnection();
		ResultSet result = null;
		DatosSensor valorSensor;
		List<DatosSensor> valoresSensores = new ArrayList<>();
		String query = "select * from datossensores where dni_paciente = ? ORDER BY id;";

		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, persona.getDNI());

			result = st.executeQuery();
			while (result.next()) {

				Integer id = result.getInt("id");
				String fecha = result.getString("fecha");
				String dato = result.getString("dato");

				Integer sensor_key = result.getInt("sensor_key");
				Integer dni_paciente = result.getInt("dni_paciente");

				valorSensor = new DatosSensor(id, fecha, dato, sensor_key, dni_paciente);
				valoresSensores.add(valorSensor);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());

		}

		return valoresSensores;
	}
}
