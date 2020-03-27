package controllers;

import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.Administrador;
import model.Persona;


public class ControlerAdministrador implements Initializable{
	public static Stage VentanaAdministrador = null;



    @FXML
    private MenuButton Nombre;

    @FXML
    private TextField TextoCorreoPaciente;

    @FXML
    private TextField TextoCorreo;

    @FXML
    private ImageView FotoPaciente;

    @FXML
    private MenuItem Perfil;

    @FXML
    private MenuButton CuidadoresDisponibles;

    @FXML
    private ImageView Foto;

    @FXML
    private Text ErrorTransferir;

    @FXML
    private Text ErrorBorrar;

    @FXML
    private Button newAccount;

    @FXML
    private Text FechaNacimiento;

    @FXML
    private Text Correo;

    @FXML
    private Text NombrePaciente;

    @FXML
    private MenuItem LogOut;

    @FXML
    private Button Next;

    @FXML
    private Button Previous;

    @FXML
    private Text Apellido;

    @FXML
    private Text DNI;

    @FXML
    private Text NumTelefono;
    
    @FXML
    private MenuButton PacientesCuidadores;
    
	public void initialize(URL location, ResourceBundle resources) {
		// TODO (don't really need to do anything here).
		//Menu buton principal
		Nombre.setText(ControladorLogin.PERSONA.getNombre() + " " + ControladorLogin.PERSONA.getApellidos());
		try {
			File dest = new File("src/Imagen/FotoPerfil" + ControladorLogin.PERSONA.getCorreo() + ".png");
			String thePath = dest.toURI().toURL().toExternalForm();
		    Image image = new Image(thePath);
		    Foto.setImage(image);
		    Main.imagenPerfilControladorVentana = Foto;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Datos cuidadores
		
					Map<String, String[]> mapC = Main.leerArchivoClase();
					String[] c = mapC.get(ControladorLogin.PERSONA.getCorreo());
					String CorreoAnt = Correo.getText();
					if(!c[2].equals("null")) {
						c[2] = c[2].replace(" ","");
						String[] cuidadores = c[2].split("\\|");

						Map<String, Persona> map = Main.leerArchivo();
						int l = cuidadores.length;
						System.out.println("Pacientes[0] = " + cuidadores [0]);
						System.out.println("Numero pacientes " + l);
						Persona[] Cuidador = new Persona[l];
						for(int i = 0; i<l; i++) {
							System.out.println("Datos paciente " + i + " " + map.get(cuidadores[i]));
							Cuidador[i] = map.get(cuidadores[i]);
						}
						NombrePaciente.setText(Cuidador[0].getNombre());
						Apellido.setText(Cuidador[0].getApellidos());
						NumTelefono.setText(Cuidador[0].getTelefono());
						DNI.setText(Cuidador[0].getDNI());
						Correo.setText(Cuidador[0].getCorreo());
						
						SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha = Cuidador[0].getFecha();
						FechaNacimiento.setText(sf.format(fecha));
						//Carga una lista de pacientes
						String[] p = mapC.get(Cuidador[0].getCorreo());
						if(!p[2].equals("null")) {
							p[2] = p[2].replace(" ","");
							String[] Pacientes = p[2].split("\\|");
						for(int i = 0; i<Pacientes.length; i++) {						
							MenuItem menuItem1 = new MenuItem(Pacientes[i]);
							PacientesCuidadores.getItems().add(menuItem1);
						}
						try {
							String DireccionFotoP = "Imagen/FotoPerfil" + Cuidador[0].getCorreo() + ".png";
							System.out.println(DireccionFotoP);
						    Image image = new Image(DireccionFotoP);
						    FotoPaciente.setImage(image);
						}catch(Exception e){
							e.printStackTrace();
						}
				}
		}
	}


	@FXML
	void on_newAccount_Clicked(ActionEvent event){
		try {
			Stage stageA = (Stage) newAccount.getScene().getWindow();
			VentanaAdministrador = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Administrador/RegisterCuidador.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static Administrador DatosAdministrador(String correo) {
		System.out.print("Entro en DatosPaciente");

		Map<String, String[]> map = Main.leerArchivoClase();
//		String[] Datos = map.get(correo);
//		Administrador administrador = null;
//		try {
//			administrador = new Administrador(Datos[1], Datos[2], Datos[3]);
//			System.out.println("\tCuidador: " + administrador.getCuidador());
//			System.out.println("\tFamiliar: " + administrador.getFamiliar());
//			System.out.println("\tPaciente: " + administrador.getPaciente());
//		}catch(Exception e){
//			System.out.println(e);
//		}


		return null; // administrador;
	}

	@FXML
	void on_LogOut_Clicked(ActionEvent event) {
		try {
			Main.imagenPerfilControladorVentana = null;
			//Main1.Main3.FotoPerfil = null;
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			stageA.close();
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Login.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	void on_Perfil_Clicked(ActionEvent event) {
		try {
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			VentanaAdministrador = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Perfil.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		}catch(Exception e) {
			System.out.print("Error!!!!!!!!!!");
			System.out.println(e);
		}


	}

	@FXML
	void On_Eliminar_Clicked(ActionEvent event){
		CuidadoresDisponibles.getItems().clear();
		String correo = TextoCorreo.getText();
		Map<String, Persona> map = Main.leerArchivo();
		Map<String, String[]> mapC = Main.leerArchivoClase();
		if(map.containsKey(correo) && !correo.equals(ControladorLogin.PERSONA.getCorreo())){
			//ClassPersona Datos = map.get(Key);
			String[] DatosClase = mapC.get(correo);
			String Creador = DatosClase[1];
			String Creado = DatosClase[2];
			Persona Datos = map.get(correo);
			if(!Datos.getRol().equals("Cuidador") || (Datos.getRol().equals("Cuidador") && Creado.equals("null"))) {
				System.out.println(Datos.getRol() + "      " + Creado);
				if(!Creado.equals("null")){
					Creado = Creado.replace(" " , "");
					String[] Pacientes = Creado.split("\\|");
					for(int cont = 0; Pacientes.length > cont; cont++) {

						BorrarCuenta(Pacientes[cont]);
					}
				}
				String[] BB = new String[11];
				Persona DatosCreador = map.get(Creador);
				BB[0] = DatosCreador.getContrasena();
				BB[1] = DatosCreador.getNombre();
				BB[2] = DatosCreador.getApellidos();
				
				SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
				Date fecha = DatosCreador.getFecha();
				BB[3] =sf.format(fecha);
				BB[4] = DatosCreador.getRol();
				BB[5] = DatosCreador.getDNI();
				BB[6] = DatosCreador.getTelefono();

				DatosClase = mapC.get(Creador);

				DatosClase[2] = DatosClase[2].replace(" " , "");
				String[] DD = DatosClase[2].split("\\|");
				DatosClase[2] = null;
				for(int cont = 0; DD.length > cont; cont++) {
					if(!DD[cont].equals(correo)) {
						if(DatosClase[2] != null) {
								DatosClase[2] = DatosClase[2] + "|" + DD[cont];
						}else {
							DatosClase[2] = DD[cont];
						}
					}
				}
				BB[7] =  DatosClase[1];
				BB[8] =  DatosClase[2];
				BB[9] =  DatosClase[3];
				BorrarCuenta(correo);
				//System.out.println("Welcome back " + Creador + " !");
				Main.guardar(Creador, BB);
				ErrorBorrar.setText("Account deleted");
			}else {
				ErrorBorrar.setText("Selected caretaker has patients, \n please remove patients");
			}

		}else {
			ErrorBorrar.setText("Account doesent exist or cannot be deleted");
		}
	}


	private void BorrarCuenta(String correo) {
		// TODO Auto-generated method stub
		Map<String, Persona> map = Main.leerArchivo();
		Map<String, String[]> mapC = Main.leerArchivoClase();
		if(map.containsKey(correo)){
			System.out.println(correo);

			Iterator<String> it = map.keySet().iterator();
			Iterator<String> it2 = mapC.keySet().iterator();

			try {

				File text = new File("Registro.txt");
				FileWriter f = new FileWriter(text);
				while(it.hasNext()) {
					String Key = (String) it.next();
					Persona Datos = map.get(Key);
					String[] DatosClase = mapC.get(Key);
					if(!Key.equals(correo)){

						f.write(Key + "\n");
						f.write(Datos.getContrasena() + "\n" );
						f.write(Datos.getNombre() + "\n" );
						f.write(Datos.getApellidos() + "\n" );
						
						SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha = Datos.getFecha();
						f.write(sf.format(fecha) + "\n" );
						f.write(Datos.getRol() + "\n" );
						f.write(Datos.getDNI() + "\n" );
						f.write(Datos.getTelefono() + "\n" );

						f.write(DatosClase[1] + "\n" );
						f.write(DatosClase[2] + "\n" );
						f.write(DatosClase[3] + "\n" );

					}else {
					File Borrar = new File("src/Imagen/FotoPerfil" + correo + ".png");
					Borrar.delete();
					if(Datos.getRol().equals("Paciente") || Datos.getRol().equals("Familiar")) {
							
							Borrar = new File("chat/" + correo + "-chat.txt");
							Borrar.delete();
						if(Datos.getRol().equals("Paciente")) {
							
							Borrar = new File("historial/" + correo + "-historial.txt");
							Borrar.delete();
							Borrar = new File("sensores/" + correo + "-sensores.txt");
							Borrar.delete();
						}
					}
					}
				}
				f.close();
			}catch(Exception e){
				System.out.println(e);


			}
		}
	}
	@FXML
	void On_Buscar_Clicked(ActionEvent event){
		String correo = TextoCorreoPaciente.getText();
		CuidadoresDisponibles.getItems().clear();
		Map<String, Persona> map = Main.leerArchivo();
		Map<String, String[]> mapC = Main.leerArchivoClase();
		Persona Datos = map.get(correo);
		if(map.containsKey(correo) && Datos.getRol().equals("Paciente")){
			String[] DatosC = mapC.get(correo);
			String CuidadorAct = DatosC[1];
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String Key = (String) it.next();
				Persona Datos3 = map.get(Key);
				if(!Key.equals(CuidadorAct) && Datos3.getRol().equals("Cuidador")) {
					//System.out.println(CuidadorAct);
					MenuItem menuItem1 = new MenuItem(Key + " (" + Datos3.getNombre() + ")");
					menuItem1.setOnAction(new EventHandler<ActionEvent>() {
					    @Override
					    public void handle(ActionEvent event) {
					        System.out.println(Key + " selected");
					        String[] DatosC2 = mapC.get(CuidadorAct);
					        DatosC2[2] = DatosC2[2].replace(" " , "");
							String[] DD = DatosC2[2].split("\\|");
							DatosC2[2] = null;
							for(int cont = 0; DD.length > cont; cont++) {
								if(!DD[cont].equals(correo)) {
									if(DatosC2[2]!=null) {
										DatosC2[2] = DatosC2[2] + "|" + DD[cont];
									}else {
										DatosC2[2] = DD[cont];
									}
								}
							}
							SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
							String[] GruardarDatos = new String[10];
							String[] DatosC3 = mapC.get(Key);
							GruardarDatos[0] = Datos3.getContrasena();
							GruardarDatos[1] = Datos3.getNombre();
							GruardarDatos[2] = Datos3.getApellidos();
							GruardarDatos[3] = sf.format(Datos3.getFecha());
							
							GruardarDatos[4] = Datos3.getRol();
							GruardarDatos[5] = Datos3.getDNI();
							GruardarDatos[6] = Datos3.getTelefono();
							if(!DatosC3[2].equals("null")){
								DatosC3[2] = DatosC3[2] + "|" + correo;
					    	}else {
					    		DatosC3[2] = correo;
					    	}
							GruardarDatos[7] = DatosC3[1];
							GruardarDatos[8] = DatosC3[2];
							GruardarDatos[9] = DatosC3[3];
							Main.guardar(Key, GruardarDatos);
							GruardarDatos[0] = Datos.getContrasena();
							GruardarDatos[1] = Datos.getNombre();
							GruardarDatos[2] = Datos.getApellidos();
							GruardarDatos[3] = sf.format(Datos.getFecha());
							GruardarDatos[4] = Datos.getRol();
							GruardarDatos[5] = Datos.getDNI();
							GruardarDatos[6] = Datos.getTelefono();
							GruardarDatos[7] = Datos3.getCorreo();
							GruardarDatos[8] = DatosC[2];
							GruardarDatos[9] = DatosC[3];
							Main.guardar(correo, GruardarDatos);
							Persona Datos2 = map.get(CuidadorAct);
							GruardarDatos[0] = Datos2.getContrasena();
							GruardarDatos[1] = Datos2.getNombre();
							GruardarDatos[2] = Datos2.getApellidos();
							
							GruardarDatos[3] = sf.format(Datos2.getFecha());
							GruardarDatos[4] = Datos2.getRol();
							GruardarDatos[5] = Datos2.getDNI();
							GruardarDatos[6] = Datos2.getTelefono();
							GruardarDatos[7] = DatosC2[1];
							GruardarDatos[8] = DatosC2[2];
							GruardarDatos[9] = DatosC2[3];
							Main.guardar(CuidadorAct, GruardarDatos);
							ErrorTransferir.setText(correo + "'s caretaker has been modified");
							On_Buscar_Clicked(event);
					    }
					});
					CuidadoresDisponibles.getItems().add(menuItem1);
				}

			}

		}else {
			ErrorTransferir.setText("The patient does not exist");
		}

	}
	@FXML
    void On_Previous_Clicked(){
		
		Map<String, String[]> mapC = Main.leerArchivoClase();
		String[] c = mapC.get(ControladorLogin.PERSONA.getCorreo());
		String CorreoAnt = Correo.getText();
		if(!c[2].equals("null")) {
			c[2] = c[2].replace(" ","");
			String[] Cuidadores = c[2].split("\\|");

			Map<String, Persona> map = Main.leerArchivo();
			int l = Cuidadores.length;
			System.out.println("Pacientes[0] = " + Cuidadores [0]);
			System.out.println("Numero pacientes " + l);
			Persona[] Cuidador = new Persona[l];
			int numPac = 0;
			for(int i = 0; i<l; i++) {
				System.out.println("Datos paciente " + i + " " + map.get(Cuidadores[i]));
				Cuidador[i] = map.get(Cuidadores[i]);
				//System.out.println("Paciente: " + Paciente[i] + " Paciente anterior: " + CorreoAnt);
				if(CorreoAnt.equals(Cuidadores[i])){
					numPac = i-1;
					//System.out.println("Numero Paciente " + numPac);
					if(numPac < 0) {
						numPac = l-1;
						//System.out.println("Numero Paciente " + numPac);
					}
				}
			}
			
			NombrePaciente.setText(Cuidador[numPac].getNombre());
			Apellido.setText(Cuidador[numPac].getApellidos());
			NumTelefono.setText(Cuidador[numPac].getTelefono());
			DNI.setText(Cuidador[numPac].getDNI());
			Correo.setText(Cuidador[numPac].getCorreo());
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			FechaNacimiento.setText(sf.format(Cuidador[numPac].getFecha()));
			PacientesCuidadores.getItems().clear();
			String[] p = mapC.get(Cuidador[numPac].getCorreo());
			if(!p[2].equals("null")) {
				p[2] = p[2].replace(" ","");
				String[] Pacientes = p[2].split("\\|");
				for(int i = 0; i<Pacientes.length; i++) {						
					MenuItem menuItem1 = new MenuItem(Pacientes[i]);
					PacientesCuidadores.getItems().add(menuItem1);
				}
			}
			try {
				String DireccionFotoP = "Imagen/FotoPerfil" + Cuidador[numPac].getCorreo() + ".png";
				System.out.println(DireccionFotoP);
			    Image image = new Image(DireccionFotoP);
			    FotoPaciente.setImage(image);
			}catch(Exception e){
				String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
				System.out.println(DireccionFotoP);
			    Image image = new Image(DireccionFotoP);
			    FotoPaciente.setImage(image);
				e.printStackTrace();
			}
		}
	}
	@FXML
    void On_Next_Clicked(){

		Map<String, String[]> mapC = Main.leerArchivoClase();
		String[] c = mapC.get(ControladorLogin.PERSONA.getCorreo());
		String CorreoAnt = Correo.getText();
		if(!c[2].equals("null")) {
			c[2] = c[2].replace(" ","");
			String[] Cuidadores = c[2].split("\\|");

			Map<String, Persona> map = Main.leerArchivo();
			int l = Cuidadores.length;
			System.out.println("Pacientes[0] = " + Cuidadores [0]);
			System.out.println("Numero pacientes " + l);
			Persona[] Cuidador = new Persona[l];
			int numPac = 0;
			for(int i = 0; i<l; i++) {
				System.out.println("Datos paciente " + i + " " + map.get(Cuidadores[i]));
				Cuidador[i] = map.get(Cuidadores[i]);
				//System.out.println("Paciente: " + Paciente[i] + " Paciente anterior: " + CorreoAnt);
				if(CorreoAnt.equals(Cuidadores[i])){
					numPac = i+1;
					//System.out.println("Numero Paciente " + numPac);
					if(numPac >= l) {
						numPac = 0;
						//System.out.println("Numero Paciente " + numPac);
					}
				}
			}
			
			NombrePaciente.setText(Cuidador[numPac].getNombre());
			Apellido.setText(Cuidador[numPac].getApellidos());
			NumTelefono.setText(Cuidador[numPac].getTelefono());
			DNI.setText(Cuidador[numPac].getDNI());
			Correo.setText(Cuidador[numPac].getCorreo());
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			FechaNacimiento.setText(sf.format(Cuidador[numPac].getFecha()));
			//Carga una lista de pacientes
			PacientesCuidadores.getItems().clear();
			String[] p = mapC.get(Cuidador[numPac].getCorreo());
			if(!p[2].equals("null")) {
				p[2] = p[2].replace(" ","");
				String[] Pacientes = p[2].split("\\|");
				for(int i = 0; i<Pacientes.length; i++) {						
					MenuItem menuItem1 = new MenuItem(Pacientes[i]);
					PacientesCuidadores.getItems().add(menuItem1);
				}
			}
			try {
				String DireccionFotoP = "Imagen/FotoPerfil" + Cuidador[numPac].getCorreo() + ".png";
				System.out.println(DireccionFotoP);
			    Image image = new Image(DireccionFotoP);
			    FotoPaciente.setImage(image);
			}catch(Exception e){
				String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
				System.out.println(DireccionFotoP);
			    Image image = new Image(DireccionFotoP);
			    FotoPaciente.setImage(image);
				e.printStackTrace();
			}
		}
	}
}
