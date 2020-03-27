package model;

import java.util.Date;

public class Persona {

	private String nombre;
	private String apellidos;
	private String DNI;
	private String correo;
	private String contrasena;
	private Date fecha;
	private String rol;
	private String telefono;

	
	public Persona() {}
	
	public Persona(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha,
			String rol, String telefono) {
	
		this.nombre = nombre;
		this.apellidos = apellidos;
		DNI = dNI;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fecha = fecha;
		this.rol = rol;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}