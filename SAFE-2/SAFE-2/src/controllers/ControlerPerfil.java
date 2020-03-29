package controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import main.Main;
import model.User;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlerPerfil implements Initializable {
	public static Stage VentanaPerfil = null;

	    @FXML
	    private Text Nombre;

	    @FXML
	    private Text Correo;

	    @FXML
	    private Text TextError;

	    @FXML
	    private Text Numero;

	    @FXML
	    private Text Fecha;

	    @FXML
	    private Text Apellido;

	    @FXML
	    private VBox xBoxOne;

	    @FXML
	    private Button ChangerButton;

	    @FXML
	    private Button addMail;

	    @FXML
	    private Text DNI;

	    @FXML
	    private ImageView Foto;
	    
	    private User user;

		public void initData(User user) {
			this.user = user;
		}

	    public void initialize(URL location, ResourceBundle resources) {
		       // TODO (don't really need to do anything here).
	    	/*try{
	    		if(Main1.Main3.FotoPerfil != null) {
					 Image image = new Image(Main1.Main3.FotoPerfil);
					 Foto.setImage(image);
				}else {
					String DireccionFoto = "Imagen/FotoPerfil" + Main1.controler1.PERSONA.getCorreo() + ".png";
				    Image image = new Image(DireccionFoto);
				    Foto.setImage(image);
				    Main3.imagenPerfilControladorPerfil = Foto;
				}
	    	}catch(Exception e){
	    		System.out.println(e);
	    	}*/
			try {
				File dest = new File("src/Imagen/FotoPerfil" + user.getCorreo() + ".png");
				String thePath = dest.toURI().toURL().toExternalForm();
			    Image image = new Image(thePath);
			    Foto.setImage(image);
			    Main.imagenPerfilControladorPerfil = Foto;
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


	    	Nombre.setText(user.getNombre());
	    	Apellido.setText(user.getApellidos());
	    	Correo.setText(user.getCorreo());
	    	DNI.setText(user.getDNI());
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	    	Fecha.setText(sf.format((user.getFecha())));
	    	Numero.setText(user.getTelefono());

		    Main.imagenPerfilControladorPerfil = Foto;
		}


	    @FXML
	    void On_Change_Clicked(ActionEvent event) {
	    	try {
				Stage stageA = (Stage) ChangerButton.getScene().getWindow();
				VentanaPerfil = stageA;
				stageA.hide();
				Parent root = FXMLLoader.load(getClass().getResource("/Main1/CambioPerfil.fxml"));
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
			} catch(Exception e) {
				e.printStackTrace();
			}
	    }
	   @FXML
	    void On_Back_Clicked(ActionEvent event) {
		   Main.imagenPerfilControladorPerfil = null;
//		   if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Administrador")) {
//				controllers.ControlerAdministrador.VentanaAdministrador.show();
//
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Cuidador")) {
//				controllers.ControlerCuidador.VentanaCuidador.show();
//
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Paciente")) {
//				controllers.ControlerPaciente.VentanaPaciente.show();
//
//			}else if(ControladorLogin.PERSONA.getCuidadoroPaciente().equals("Familiar")) {
//				controllers.ControlerFamiliar.VentanaFamiliar.show();
//
//			}
		   Stage stageA = (Stage) DNI.getScene().getWindow();
		   stageA.close();


	    }
	   @FXML
	    void On_addMail_Clicked(ActionEvent event) {

	   }
}
