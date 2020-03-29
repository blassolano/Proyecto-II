package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import constant.Constant;
import dao.PacienteDAO;
import dao.RolDAO;
import dao.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

public class Main extends Application {
	
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
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
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
		System.out.println("crear bd");
		File fdb = new File(Constant.URL_DB);
		fdb.delete();
		RolDAO rolDAO= new RolDAO();
		rolDAO.createTable();
		rolDAO.insert();
		
		UserDAO userDAO = new UserDAO();
		userDAO.createTable();
		userDAO.insert();
		
		PacienteDAO pacienteDAO =  new PacienteDAO();
		pacienteDAO.createTable();
		pacienteDAO.insert();
		System.out.println("fin crear bd");
		
//		Scanner in = new Scanner(System.in);
		launch(args);
		/*System.out.println("1-Introduce tu nombre de usuario\n2-Cambiar contrase�a\n3-Crear usuario\n");
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

	public static void guardar(String correo, String[] Datosg) {
		// TODO Auto-generated method stub
		User Datos;
		String[] DatosClase;
		int cont = 0;
		try {
			Map<String, User> map = leerArchivo();
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
						f.write(Datos.getRole() + "\n");
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
		Map<String, User> map = new LinkedHashMap<String, User>();
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
		   User Datos2;
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
		    	User p = new User();
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
