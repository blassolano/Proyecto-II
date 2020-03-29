package model;

import java.util.Date;

public class User {

	protected long id;
	protected String DNI;
	protected String nombre;
	protected String apellidos;
	protected String correo;
	protected String contrasena;
	protected Date fecha;
	protected Role role;
	protected String telefono;

	public User() {
	}

	public User(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha, Role role,
			String telefono) {

		this.nombre = nombre;
		this.apellidos = apellidos;
		DNI = dNI;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fecha = fecha;
		this.role = role;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", DNI=" + DNI + ", nombre=" + nombre + ", apellidos=" + apellidos + ", correo="
				+ correo + ", contrasena=" + contrasena + ", fecha=" + fecha + ", role=" + role + ", telefono="
				+ telefono + "]\n";
	}

}