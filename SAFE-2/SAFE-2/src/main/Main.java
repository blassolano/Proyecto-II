package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import Conexion.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Persona;

public class Main extends Application {
	//public static String FotoPerfil = null;
	private static final String DB = "resources/safe.db";
	
	public static ImageView imagenPerfilControlado1 = null;
	public static ImageView imagenPerfilControladorPerfil = null;
	public static ImageView imagenPerfilControladorVentana = null;

	public static void changePerfil(File dest) {
		String thePath;
		try {
			thePath = dest.toURI().toURL().toExternalForm();
			System.out.println("You chose " + thePath);
			//FotoPerfil = thePath;
			Image image = new Image(thePath);
			if (imagenPerfilControlado1 != null) {
				imagenPerfilControlado1.setImage(image);
			}

			if (imagenPerfilControladorPerfil != null) {
				imagenPerfilControladorPerfil.setImage(image);
			}
			if (imagenPerfilControladorVentana != null) {
				imagenPerfilControladorVentana.setImage(image);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Login.fxml"));
            Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.getIcons().add(new Image("/Imagen/logoS.png"));
			primaryStage.setResizable(false);
			primaryStage.show();
			}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createTable();
		System.out.println("crear bd");
		Scanner in = new Scanner(System.in);
		launch(args);
		/*System.out.println("1-Introduce tu nombre de usuario\n2-Cambiar contraseï¿½a\n3-Crear usuario\n");
		int respuesta = in.nextInt();
		if(respuesta == 1){
			registro();
		}else if(respuesta == 2){
			cambioContra();
		}else if(respuesta == 3) {
			NuevoUs();
		}System.out.println("Adios");

		//System.out.println("Llega?");*/
	}
	
	public static boolean sentenciaSQL(String sql) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:" + DB);
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}

	
	public static  void createTable() {

			String sentencia_rol;
			String sentencia_usuario;
			String sentencia_persona;
			String sentencia_historial;
			String sentencia_insert;

			sentencia_rol = "CREATE TABLE IF NOT EXISTS ROL"
					+ "( ID         INT     PRIMARY KEY 		UNIQUE 			NOT NULL,"
					+ "  NOMBRE_ROL VARCHAR 									NOT NULL)";

			sentencia_usuario = "CREATE TABLE IF NOT EXISTS USUARIO "
					+ "(   ID         INT          PRIMARY KEY 	UNIQUE 		NOT NULL,"
					+ "	 USUARIO    VARCHAR (20) NOT NULL," + " CONTRASEÑA VARCHAR (30) NOT NULL,"
					+ "    FK_ROL     INT          REFERENCES ROL (ID) )";

			sentencia_persona = "CREATE TABLE IF NOT EXISTS PERSONA "
					+ " (   DNI             INT          PRIMARY KEY UNIQUE NOT NULL,"
					+ "    NOMBRE          VARCHAR (30) NOT NULL," + "    APELLIDOS       VARCHAR (30) NOT NULL,"
					+ "    CORREO          VARCHAR (50) NOT NULL," + "    FECHANACIMIENTO DATE         NOT NULL,"
					+ "    FK_USUARIO                   REFERENCES USUARIO (ID))";

			sentencia_historial = "CREATE TABLE IF NOT EXISTS HISTORIAL"
					+ "(	ID         		INTEGER      	PRIMARY KEY 	AUTOINCREMENT		NOT NULL,"
					+ "	HISTORIA   		VARCHAR (70)," + "   FK_PERSONA 		INT          	REFERENCES PERSONA (DNI))";

			File fdb = new File(DB);
			fdb.delete();

			sentenciaSQL(sentencia_rol);
			sentenciaSQL(sentencia_usuario);
			sentenciaSQL(sentencia_persona);
			sentenciaSQL(sentencia_historial);

			sentencia_insert = "INSERT INTO ROL (ID,NOMBRE_ROL) VALUES ('1','PACIENTE')," + "('2', 'MEDICO'),"
					+ "('3', 'FAMILIAR')";

			sentenciaSQL(sentencia_insert);

			sentencia_insert = "INSERT INTO USUARIO (ID,USUARIO,CONTRASEÑA,FK_ROL)"
					+ "                    VALUES ('1','nicoleav','1234','2'),"
					+ "                           ('2','renzoar','aabb','2'),"
					+ "                           ('3','claudiaso','1998','1'),"
					+ "                           ('4','martast','2020','1'),"
					+ "                           ('5','natalyca','2010','1'),"
					+ "                           ('6','alexasa','1990','1'),"
					+ "                           ('7','yacoes','4875','1'),"
					+ "                           ('8','mariadb','1234','3'),"
					+ "                           ('9','maritzago','1949','3'),"
					+ "                           ('10','jorgeso','1919','3'),"
					+ "                           ('11','rosarioaz','1563','3')";

			sentenciaSQL(sentencia_insert);

			sentencia_insert = "INSERT INTO PERSONA (DNI,NOMBRE,APELLIDOS,CORREO,FECHANACIMIENTO,FK_USUARIO)"
					+ "                    VALUES ('77678961F','NICOLE','AVENDAÑO','NICOAVENDAÑO@GMAIL.COM','02/01/2000','1'),"
					+ "                           ('77678962F','RENZO','ARCOS','RENARCOS@GMAIL.COM','19/09/1997','2'),"
					+ "                           ('77678963F','CLAUDIA','SORIA','JAZMINSORIA191998@GMAIL.COM','19/10/1998','3'),"
					+ "                           ('77678964F','MARTA','STEWART','MARSTEWAR@GMAIL.COM','19/12/1997','4'),"
					+ "                           ('77678965F','NATALY','CASTILLO','NATCASTILLO@GMAIL.COM','12/05/1996','5'),"
					+ "                           ('77678966F','ALEXANDRA','SANTOS','ALESANTOS@GMAIL.COM','19/06/1997','6'),"
					+ "                           ('77678967F','YACO','ESKENAZI','YAEZQUENZI@GMAIL.COM','13/05/1992','7'),"
					+ "                           ('77678968F','MARIA','DB','MARIADB@GMAIL.COM','19/09/1990','8'),"
					+ "                           ('77678969F','MARITZA','GONZALES','MARIGONZALES@GMAIL.COM','29/02/1949','9'),"
					+ "                           ('77678910F','JORGE','SORIA','JORSORIA@GMAIL.COM','25/04/1947','10'),"
					+ "                           ('77678911F','ROSARIO','AZABACHE','ROAZABACHE@GMAIL.COM','20/05/1968','11')";

		sentenciaSQL(sentencia_insert);

			sentencia_insert = "INSERT INTO HISTORIAL (HISTORIA,FK_PERSONA)"
					+ "                      VALUES ('TIENE DOLOR DE PECHO','77678965F'),"
					+ "                             ('RECIBIÓ TRATAMIENTO, CONTINÚA CON MALESTAR','77678965F'),"
					+ "                             ('TIENE DOLOR DE GARGANTA Y TOS','77678967F'),"
					+ "                             ('CONTINUA CON LA FARINGITIS, NO TIENE VOS','77678967F')";

		sentenciaSQL(sentencia_insert);

			
			

	}

	public static void guardar(String correo, String[] Datosg) {
		// TODO Auto-generated method stub
		Persona Datos;
		String[] DatosClase;
		int cont = 0;
		try {
			Map<String, Persona> map = leerArchivo();
			Map<String, String[]> mapC = leerArchivoClase();
			File text = new File("Registro.txt");
			FileWriter f = new FileWriter(text);
			String linea = null;
			Iterator<String> itt = map.keySet().iterator();
		    while(itt.hasNext()){
				String key = (String) itt.next();
				//System.out.println("Memoria: \n Nombre: " + key);
				Datos = map.get(key);
				//System.out.println("Datos: " + Datos.getCorreo() + Datos.getNombre() + Datos.getApellidos() + Datos.getDNI() + Datos.getEdad() + Datos.getClass() + Datos.getContrasena());
		    }
		    Iterator<String> it = map.keySet().iterator();
		    while(it.hasNext()){
				String key = (String) it.next();
				if(key != "-1"){
					//System.out.println("key != -1");
					Datos = map.get(key);
					if(correo.equals(key)) {
						//System.out.println("correo.equals(key)");
						f.write(key + "\n");
						f.write(Datosg[0] + "\n");
						f.write(Datosg[1] + "\n");
						f.write(Datosg[2] + "\n");
						f.write(Datosg[3] + "\n");
						f.write(Datosg[4] + "\n");
						f.write(Datosg[5] + "\n");
						f.write(Datosg[6] + "\n");
						f.write(Datosg[7] + "\n");
						f.write(Datosg[8] + "\n");
						f.write(Datosg[9] + "\n");
						cont++;
					}
					else {
						//System.out.println("!correo.equals(key)");

						f.write(Datos.getCorreo() + "\n");
						f.write(Datos.getContrasena() + "\n");
						f.write(Datos.getNombre() + "\n");
						f.write(Datos.getApellidos() + "\n");
						
						
						SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
						f.write(sf.format(Datos.getFecha()) + "\n");
						f.write(Datos.getRol() + "\n");
						f.write(Datos.getDNI() + "\n");
						f.write(Datos.getTelefono() + "\n");
						DatosClase = mapC.get(key);
						//System.out.println("DatosClase[1] = " + DatosClase[1] + " DatosClase[1] = " + DatosClase[1] + " DatosClase[1] = " + DatosClase[1]);
						f.write(DatosClase[1] + "\n");
						f.write(DatosClase[2] + "\n");
						f.write(DatosClase[3] + "\n");

					}
				}
			}
			if(cont == 0){
				//System.out.println("cont == 0");
				f.write(correo + "\n");
				f.write(Datosg[0] + "\n");
				f.write(Datosg[1] + "\n");
				f.write(Datosg[2] + "\n");
				f.write(Datosg[3] + "\n");
				f.write(Datosg[4] + "\n");
				f.write(Datosg[5] + "\n");
				f.write(Datosg[6] + "\n");
				f.write(Datosg[7] + "\n");
				f.write(Datosg[8] + "\n");
				f.write(Datosg[9] + "\n");
				/*if(Datosg[4].equals("Paciente")) {
					ClassPaciente paciente;
					paciente = Paciente.ControlerPaciente.DatosPaciente(correo);
					f.write(paciente.getCuidador());
					f.write(paciente.getFamiliar());
					f.write(paciente.getHistorial());
				}else if(Datosg[4].equals("Familiar")) {
					ClassFamiliar familiar;
					familiar = Familiar.ControlerFamiliar.DatosFamiliar(correo);
					f.write(familiar.getPaciente());
					f.write(familiar.getGradoControl());
					f.write("null");
				}else if(Datosg[4].equals("Cuidador")) {
					ClassCuidador cuidador;
					cuidador = Cuidador.ControlerCuidador.DatosCuidador(correo);
					f.write(cuidador.getAdministrador());
					f.write(cuidador.getPaciente());
					f.write(cuidador.getespecificaciones());
				}else if(Datosg[4].equals("Administrador")) {
					//Administrador.ControlerAdministrador.DatosAdministrador(key);
				}*/
			}
			f.close();

		}catch(Exception e) {
			System.out.println(e);

		}

	}


	public static Map leerArchivo() {
		// TODO Auto-generated method stub
		//ArrayList<String> cadena = new ArrayList<String>();
		Map<String, Persona> map = new LinkedHashMap<String, Persona>();
		String correo;
		String[] Datos = new String[7];
		    try {
		    	FileReader entrada = new FileReader("Registro.txt");
		        BufferedReader bf = new BufferedReader(entrada);
		        correo = bf.readLine();
		        while((correo) != null){
		        	Datos[0] = bf.readLine();
		        	Datos[1] = bf.readLine();
		        	Datos[2] = bf.readLine();
		        	Datos[3] = bf.readLine();
		        	Datos[4] = bf.readLine();
		        	Datos[5] = bf.readLine();
		        	Datos[6] = bf.readLine();
		        	bf.readLine();
		        	bf.readLine();
		        	bf.readLine();
		        	// TODO depurar
		        	//map.put(correo, new Persona(correo, Datos[0], Datos[1], Datos[2], Datos[3], Datos[4], Datos[5], Datos[6]));
		        	//map.put(correo, Datos);
		        	//System.out.println("map: " + correo + " " + Datos[0]);
					correo = bf.readLine();
		        }
		        entrada.close();
		    } catch (Exception e) {
		        //JOptionPane.showMessageDialog(null, "Error al leer archivo: "+e.getMessage());
		    	System.out.println(e);
		    }
		    Iterator<String> it = map.keySet().iterator();
		   Persona Datos2;
		    while(it.hasNext()){
				String key = (String) it.next();
				Datos2 = map.get(key);
				//System.out.println("map 3: " + key + " " + Datos2.getCorreo());
			}
		    if(map.isEmpty()) {
		    	correo = "-1";
		    	Datos[0] = "-1";
		    	Datos[1] = "-1";
		    	Datos[2] = "-1";
		    	Datos[3] = "-1";
		    	Datos[4] = "-1";
		    	Datos[5] = "-1";
		    	Datos[6] = "-1";
		    	Persona p = new Persona();
		    	p.setCorreo(correo);
		    	map.put(correo, p);
		    }
		    return map;
	}
	public static Map leerArchivoClase() {
		// TODO Auto-generated method stub
		//ArrayList<String> cadena = new ArrayList<String>();
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		String correo;

		    try {
		    	FileReader entrada = new FileReader("Registro.txt");
		        BufferedReader bf = new BufferedReader(entrada);
		        correo = bf.readLine();
		        //System.out.println("Entro en leerArchivoClase readLine() " + correo);
		        while((correo) != null){
		        	String[] Datos = new String[4];
		        	//System.out.println("Entro en leerArchivoClase en el while");
		        	bf.readLine();
		        	bf.readLine();
		        	bf.readLine();
		        	bf.readLine();
		        	Datos[0] = bf.readLine();
		        	bf.readLine();
		        	bf.readLine();
		        	Datos[1] = bf.readLine();
		        	Datos[2] = bf.readLine();
		        	Datos[3] = bf.readLine();
		        	//System.out.println(correo);
		        	map.put(correo, Datos);
					correo = bf.readLine();

		        }
		        entrada.close();
		    } catch (Exception e) {
		        //JOptionPane.showMessageDialog(null, "Error al leer archivo: "+e.getMessage());
		    	System.out.println(e);
		    }
		    Iterator<String> it = map.keySet().iterator();
		    String[] Datos2;
		    while(it.hasNext()){
				String key = (String) it.next();
				Datos2 = map.get(key);
				//System.out.println("map 3: " + key + " " + Datos2[0] + " " + Datos2[1]+ " " + Datos2[2]);
			}
		    if(map.isEmpty()) {
		    	String[] Datos = new String[4];
		    	correo = "-1";
		    	Datos[0] = "-1";
		    	Datos[1] = "-1";
		    	Datos[2] = "-1";
		    	Datos[3] = "-1";
		    	map.put(correo, Datos);
		    }
		    return map;
	}

}
