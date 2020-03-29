package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Paciente;
import model.User;


public class ControlerCambC implements Initializable{

    @FXML
    private Button Send;
    @FXML
    private Button Change;
    @FXML
    private Hyperlink Back;
    @FXML
    private TextField loginMail;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private TextField pinF;
    @FXML
    private Text MensajeAlerta;


	private String random;
	public void initialize(URL location, ResourceBundle resources){
	       // TODO (don't really need to do anything here).
	}

	@FXML
	void on_Send_Clicked(ActionEvent event){
		String correo = loginMail.getText();
		Map<String, String[]> map;
		map = Main.leerArchivo();
		if(map.containsKey(correo)){
			MensajeAlerta.setText("");
			int rand = (int) (Math.random()*8999 + 1000);
			random = Integer.toString(rand);
			MensajeAlerta.setText(random);
		}else {
			MensajeAlerta.setText("Email not found");
		}
	}
	@FXML
	void on_Change_Clicked(ActionEvent event){
		String contra1 = password1.getText();
		String contra2 = password2.getText();
		String pin = pinF.getText();
		if(pin.equals(random)){
			if(!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$", contra1)){
				MensajeAlerta.setText("The password must contain one upercase letter,\n a lowercase letter and a number");

				if(!Pattern.matches("[a-zA-Z0-9]{6,}", contra1)){
					MensajeAlerta.setText("Password is too short");

				}
			}else if(contra1.equals(contra2)) {
				Map<String, User> map;
				map = Main.leerArchivo();
				String correo = loginMail.getText();
				Paciente D = ControlerPaciente.DatosPaciente(correo);
				String[] Datos = new String[10];
				User Dat = map.get(correo);
				Datos[0] = contra1;
				Datos[1] = Dat.getNombre();
				Datos[2] = Dat.getApellidos();
				SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
				Datos[3] = sf.format(Dat.getFecha());
				Datos[4] = Dat.getDNI();
				Datos[5] = Dat.getTelefono();
//				Datos[6] = String.valueOf(D.getMedicoId());
//				Datos[7] = String.valueOf(D.getFamiliarId());
				Datos[8] = D.getHistorial();

				Main.guardar(correo, Datos);
				on_Back_Clicked(null);
			}else {
				MensajeAlerta.setText("Passwords don't match");
			}
		}else {
			MensajeAlerta.setText("Wrong pin");
		}
	}
	@FXML
	void on_Back_Clicked(ActionEvent event){
		try {
			LoginController.VentanaLogin.show();
			Stage stageA = (Stage) Back.getScene().getWindow();
		    stageA.close();
		    /*Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
				stage.setTitle("SAVE");
			stage.show();*/
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
