package controllers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;


public class ControlerCambioPerfil implements Initializable{
	@FXML
    private Text TextError;

    @FXML
    private ImageView Foto;

    @FXML
    private VBox xBoxOne;
    
    @FXML
    private Component HBox;

    @FXML
    private Button DoneButton;
    
    @FXML
    private TextField registerName;
    
    @FXML
    private TextField registerSurname;
    
    @FXML
    private TextField registerPhone;

		    
	public void initialize(URL location, ResourceBundle resources) {
		/*try {
			if(Main1.Main3.FotoPerfil != null) {
				 Image image = new Image(Main1.Main3.FotoPerfil);
				 Foto.setImage(image);
			}else {
				String DireccionFoto = "Imagen/FotoPerfil" + Main1.controler1.PERSONA.getCorreo() + ".png";
			    Image image = new Image(DireccionFoto);
			    Foto.setImage(image);
			    Main3.imagenPerfilControlado1 = Foto;
			}
		}catch(Exception e){
			System.out.println(e);
		}*/
		
		try {
			File dest = new File("src/Imagen/FotoPerfil" + ControladorLogin.PERSONA.getCorreo() + ".png");
			String thePath = dest.toURI().toURL().toExternalForm();
		    Image image = new Image(thePath);
		    Foto.setImage(image);
		    Main.imagenPerfilControlado1 = Foto;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		registerName.setPromptText( ControladorLogin.PERSONA.getNombre());
		registerSurname.setPromptText( ControladorLogin.PERSONA.getApellidos());
		registerPhone.setPromptText( ControladorLogin.PERSONA.getTelefono());
	    //Stage stageA = (Stage) DoneButton.getScene().getWindow();
	   
	}
	@FXML
    void  On_Back_Clicked(ActionEvent event) {
    	try {
    		Main.imagenPerfilControlado1 = null;
			ControlerPerfil.VentanaPerfil.show();
			Stage stageA = (Stage) DoneButton.getScene().getWindow();
		    stageA.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	@FXML
    void On_ChangePicture_Clicked(ActionEvent event) {
		String DireccionFoto = "Imagen/FotoPerfil" + ControladorLogin.PERSONA.getCorreo() + ".png";
	    File f = new File(DireccionFoto);
		JFileChooser file=new JFileChooser();  
		//file.addChoosableFileFilter(new FileFilter("PNG", "*.png"));
		System.out.println("elijiendo fichero");
		file.showOpenDialog(HBox);
		
		File abre=file.getSelectedFile();
		
		if (abre == null) {
			System.out.println("You cancelled the choice");
		}
		else {
			System.out.println("You chose " + abre);

		}
		//File source = new File("/Users/pankaj/tmp/sourceApache.avi");
		File dest = new File("src/Imagen/FotoPerfil" + ControladorLogin.PERSONA.getCorreo() + ".png");
		try {
			copyFileUsingJava7Files(abre, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Main.changePerfil(dest);
	}
	
	public static void copyFileUsingJava7Files(File source, File dest) throws IOException {
		Path from = Paths.get(source.toURI());
		Path to = Paths.get(dest.toURI());
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES };
	    Files.copy(from, to, options);
	}
	
	@FXML
    void On_Done_Clicked(ActionEvent event) {
		String[] Datos = new String[10];
		String nombre = registerName.getText();
		String apellido = registerSurname.getText();
		String telefono = registerPhone.getText();

		if(!Pattern.matches("([6][0-9]{8})", telefono) && !telefono.equals("")){
			TextError.setText("Phone number not valid");
			
		}else {
			//ClassPaciente D = ControlerPaciente.DatosPaciente(correo);
			Datos[0] = ControladorLogin.PERSONA.getContrasena();
			if(nombre.equals("")) {
				Datos[1] = ControladorLogin.PERSONA.getNombre();
			}else {
				Datos[1] = nombre;
			}
			if(apellido.equals("")) {
				Datos[2] = ControladorLogin.PERSONA.getApellidos();
			}else {
				Datos[2] = apellido;
			}
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			Datos[3] = sf.format(ControladorLogin.PERSONA.getFecha());
			Datos[4] = "Paciente";
			Datos[5] = ControladorLogin.PERSONA.getDNI();
			if(telefono.equals("")) {
				Datos[6] = ControladorLogin.PERSONA.getTelefono();
			}else {
				Datos[6] = telefono;
			}
//			if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Administrador")) {
//				Datos[7] = ControladorLogin.ADMINISTRADOR.getCuidador();
//				Datos[8] = ControladorLogin.ADMINISTRADOR.getPaciente();
//				Datos[9] = ControladorLogin.ADMINISTRADOR.getFamiliar();
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Cuidador")) {
//				Datos[7] = ControladorLogin.CUIDADOR.getAdministrador();
//				Datos[8] = ControladorLogin.CUIDADOR.getPaciente();
//				Datos[9] = ControladorLogin.CUIDADOR.getespecificaciones();
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Paciente")) {
//				Datos[7] = ControladorLogin.PACIENTE.getCuidador();
//				Datos[8] = ControladorLogin.PACIENTE.getFamiliar();
//				Datos[9] = ControladorLogin.PACIENTE.getHistorial();
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Familiar")) {
//				Datos[7] = ControladorLogin.FAMILIAR.getPaciente();
//				Datos[8] = ControladorLogin.FAMILIAR.getGradoControl();
//				Datos[9] = null;
//			}

			//System.out.println(correo + Datos[0] + Datos[1] + Datos[2] + Datos[3] + Datos[4] + Datos[5]+ Datos[6]);
			Main.guardar(ControladorLogin.PERSONA.getCorreo(), Datos);
			On_Back_Clicked(null);
		}
	}
	
	    
}