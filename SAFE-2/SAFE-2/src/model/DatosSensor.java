package model;

public class DatosSensor {

	private int id;
	private String fecha, dato;
	private int sensores_key;
	private int dni_paciente;

	public DatosSensor(int id, String fecha, String dato, int sensores_key, int dni_paciente) {
		this.id = id;
		this.fecha = fecha;
		this.dato = dato;
		this.sensores_key = sensores_key;
		this.dni_paciente = dni_paciente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public int getSensores_key() {
		return sensores_key;
	}

	public void setSensores_key(int sensores_key) {
		this.sensores_key = sensores_key;
	}

	public int getDni_paciente() {
		return dni_paciente;
	}

	public void setDni_paciente(int dni_paciente) {
		this.dni_paciente = dni_paciente;
	}

}
