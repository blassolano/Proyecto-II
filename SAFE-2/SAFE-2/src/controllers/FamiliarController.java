package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dao.PacienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.Familiar;
import model.Paciente;
import model.User;

public class FamiliarController implements Initializable {
	public static Stage VentanaFamiliar = null;
	public static User[] Paciente = null;
	
	@FXML
    private MenuButton Nombre;

    @FXML
    private CheckBox OnOfPulsometro;

    @FXML
    private ImageView FotoPaciente;

    @FXML
    private MenuItem Perfil;

    @FXML
    private ImageView Foto;

    @FXML
    private CheckBox DetectorCaidas1;

    @FXML
    private VBox DetectorCaidas2;

    @FXML
    private MenuButton menuChat;

    @FXML
    private TextArea chatStreaming;

    @FXML
    private BorderPane bp;

    @FXML
    private Text FechaNacimiento;

    @FXML
    private Text Correo;

    @FXML
    private HBox infoPulsometro;

    @FXML
    private Text NombrePaciente;

    @FXML
    private Button enviar;

    @FXML
    private Circle CirculoFall;

    @FXML
    private MenuItem LogOut;

    @FXML
    private Text Apellido;

    @FXML
    private TextArea historialStreaming;

    @FXML
    private Text DNI;

    @FXML
    private TextField mensaje1;

    @FXML
    private Text NumTelefono;
    
    @FXML
    private Tab TabHistorial;
    
    @FXML
    private TabPane TabPlane;
    
    private User user;
    private PacienteDAO pacienteDAO;


	public void initData(User user) {
		this.user = user;
		this.pacienteDAO = new PacienteDAO();
		postInitData();
	}
	
	private void postInitData() {
		Nombre.setText(user.getNombre() + " " + user.getApellidos());
		String DireccionFoto = "Imagen/FotoPerfil" + user.getCorreo() + ".png";
		System.out.println(DireccionFoto);
		/*try {
		    Image image = new Image(DireccionFoto);
		    Foto.setImage(image);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		try {
			File dest = new File("src/Imagen/FotoPerfil" + user.getCorreo() + ".png");
			String thePath = dest.toURI().toURL().toExternalForm();
		    Image image = new Image(thePath);
		    Foto.setImage(image);
		    Main.imagenPerfilControladorVentana = Foto;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Paciente> lstPaciente = pacienteDAO.pacientesDelFamiliar(user.getId());
		System.out.println(lstPaciente);


		//Datos Paciente

		//System.out.println(Main1.controler1.CUIDADOR.getPaciente());
		/*String p = null; // ControladorLogin.FAMILIAR.getPaciente().replace(" ","");
		String[] Pacientes = p.split("\\|");
		if(Pacientes != null) {
			Map<String, User> map = Main.leerArchivo();
			int l = Pacientes.length;
			System.out.println("Pacientes[0] = " + Pacientes [0]);
			System.out.println("Numero pacientes " + l);
			Paciente = new User[l];
			for(int i = 0; i<l; i++) {
				System.out.println("Datos paciente " + i + " " + map.get(Pacientes[i]));
				Paciente[i] = map.get(Pacientes[i]);
			}
			NombrePaciente.setText(Paciente[0].getNombre());
			Apellido.setText(Paciente[0].getApellidos());
			NumTelefono.setText(Paciente[0].getTelefono());
			DNI.setText(Paciente[0].getDNI());
			Correo.setText(Paciente[0].getCorreo());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			FechaNacimiento.setText(sf.format((Paciente[0].getFecha())));
			try {
				String DireccionFotoP = "Imagen/FotoPerfil" + Paciente[0].getCorreo() + ".png";
				System.out.println(DireccionFotoP);
			    Image image = new Image(DireccionFotoP);
			    FotoPaciente.setImage(image);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//Sensores
		
				try {
					
					//Leo el fichero al reves 
					
					FileReader fr = null; //new FileReader("sensores/" + ControladorLogin.FAMILIAR.getPaciente() + "-sensores.txt");
					BufferedReader bf = new BufferedReader(fr);
					LinkedList<String> list = new LinkedList<String>();			 
					String sCadena;
					while ((sCadena = bf.readLine())!=null) {
					  list.add(sCadena);				
					}
					Iterator<String> it = list.descendingIterator();
					boolean encontrado = false;
					while(it.hasNext() && encontrado == false) {
						String Linea = it.next();
						System.out.println(Linea);
						String[] info = Linea.split("\\|");
						String[] infoC = info[0].split("=");
						if(infoC[0].equals("Caidas")) {
							encontrado = true;
							if(infoC[1].equals("true")) {
								DetectorCaidas1.setSelected(true); 						
							}else {
								DetectorCaidas1.setSelected(false); 
							}
							String[] infoP = info[1].split("=");
							if(infoP[1].equals("true")) {
								OnOfPulsometro.setSelected(true); 						
							}else {
								OnOfPulsometro.setSelected(false); 
							}
						}
						  
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(OnOfPulsometro.isSelected()) {
			    	try {
			    		String path = null; // "sensores/" + ControladorLogin.FAMILIAR.getPaciente() + "-sensores.txt";
			    		//Utilizo una lista para leer el fichero alreves
			    		FileReader fw = new FileReader(path);
			    		BufferedReader bf = new BufferedReader(fw);
			    		LinkedList<String> list = new LinkedList<String>();			 
						String sCadena;
						while ((sCadena = bf.readLine())!=null) {
						  list.add(sCadena);				
						}
						Iterator<String> it = list.descendingIterator();
						//Creo tabla
						
						final NumberAxis xAxis = new NumberAxis();
				        final NumberAxis yAxis = new NumberAxis();
				        xAxis.setLabel("Hours ago");
				        //creating the chart
				        final LineChart<Number,Number> lineChart = 
				                new LineChart<Number,Number>(xAxis,yAxis);
						XYChart.Series series = new XYChart.Series();
						String linea;
						int cont = 0;
						int valor;
						while(it.hasNext() && cont < 15) {
							linea = it.next();
							linea = linea.replace(" ","");
							try {
								valor = Integer.parseInt(linea);
								series.getData().add(new XYChart.Data(-cont, valor));
								cont++;
							}catch(Exception e){
								
							}
							
						}
						bf.close();
						lineChart.getData().add(series);
						infoPulsometro.getChildren().add(lineChart);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	}
				if(DetectorCaidas1.isSelected()) {
			    	try {
			    		String path = null; //"sensores/" + ControladorLogin.FAMILIAR.getPaciente() + "-sensores.txt";
			    		FileReader fw = new FileReader(path);
			    		
			    		//Utilizo una lista para leer el fichero alreves
			
			    		BufferedReader bf = new BufferedReader(fw);
			    		LinkedList<String> list = new LinkedList<String>();			 
						String sCadena;
						while ((sCadena = bf.readLine())!=null) {
						  list.add(sCadena);				
						}
						Iterator<String> it = list.descendingIterator();
						String Valor = "n";
						while(it.hasNext() && !Valor.equals("false") && !Valor.equals("true")) {
							Valor = it.next();
							if(Valor.equals("false")) {
								CirculoFall.setFill(Color.LIGHTGREEN);
							}else if(Valor.equals("true")) {
								CirculoFall.setFill(Color.RED);
							}
						}
						bf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	}
		DetectorCaidas1.setDisable(true);
		OnOfPulsometro.setDisable(true);
		if(LoginController.FAMILIAR.getGradoControl().equals("3")){
			DetectorCaidas1.setDisable(false);
			OnOfPulsometro.setDisable(false);
		}
		if(LoginController.FAMILIAR.getGradoControl().equals("1")){
			//TabPlane.getTabs.remove(TabHistorial);
			TabPlane.getTabs().remove(TabHistorial);
		}
		//chat
		try { 
			String path = null; // "chatFamilia/" + ControladorLogin.FAMILIAR.getPaciente() + "-chat.txt";
			String content;
			content = readFile(path);
			chatStreaming.setText(content);
		}	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Historial medico
		try {
			String path = null; //"historial/" + ControladorLogin.FAMILIAR.getPaciente() + "-historial.txt";
			String content;
			content = readFile(path);
			historialStreaming.setText(content);
		}
		
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}*/
	}

	public void initialize(URL location, ResourceBundle resources) {
	       // TODO (don't really need to do anything here).
		System.out.println("initialize");

		//Nombre y foto MenuButton

		
		
	}
	/*@FXML
	void on_newAccount_Clicked(ActionEvent event){
		try {
			Stage stageA = (Stage) newAccount.getScene().getWindow();
			VentanaFamiliar = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Cuidador/RegisterPaciente.fxml"));
         Scene scene = new Scene(root);
         Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
				stage.setTitle("SAVE");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	@FXML
	void on_Perfil_Clicked(ActionEvent event){
		try {
     	Stage stageA = (Stage) Nombre.getScene().getWindow();
     	VentanaFamiliar = stageA;
 		stageA.hide();
 		Parent root = FXMLLoader.load(getClass().getResource("/Main1/Perfil.fxml"));
         Scene scene = new Scene(root);
         Stage stage = new Stage();
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
	void on_LogOut_Clicked(ActionEvent event){
		try {
	    	Stage stageA = (Stage) Nombre.getScene().getWindow();

			stageA.close();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
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

	public static Familiar DatosFamiliar(String correo) {
		System.out.print("Entro en DatosFamiliar");

		Map<String, String[]> map = Main.leerArchivoClase();
		String[] Datos = map.get(correo);
		Familiar familiar = null;
//		try {
//			familiar = new Familiar(Datos[1], Datos[2]);
//	    	System.out.println("\tPaciente: " + familiar.getPaciente());
//			System.out.println("\tGrado: " + familiar.getGradoControl());
//		}catch(Exception e){
//			System.out.println(e);
//		}
		return familiar;
	}

	private String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}

   @FXML
    void On_caidasActioned_Clicked() {
    	
    	try {
    		CirculoFall.setFill(Color.SNOW);
    		String path = null; // "sensores/" + ControladorLogin.FAMILIAR.getPaciente() + "-sensores.txt";
    		FileWriter fw = new FileWriter(path, true);
    		if(DetectorCaidas1.isSelected()) {
	    		if(OnOfPulsometro.isSelected()) {
	    			fw.write("Caidas=true|Pulsometro=true\n");
	    		}else {
	    			fw.write("Caidas=true|Pulsometro=false\n");
	    		}

				try {
		    		FileReader fr = new FileReader(path);
		    		
		    		//Utilizo una lista para leer el fichero alreves
		
		    		BufferedReader bf = new BufferedReader(fr);
		    		LinkedList<String> list = new LinkedList<String>();			 
					String sCadena;
					while ((sCadena = bf.readLine())!=null) {
					  list.add(sCadena);				
					}
					Iterator<String> it = list.descendingIterator();
					String Valor = "n";
					while(it.hasNext() && !Valor.equals("false") && !Valor.equals("true")) {
						Valor = it.next();
						if(Valor.equals("false")) {
							CirculoFall.setFill(Color.LIGHTGREEN);
						}else if(Valor.equals("true")) {
							CirculoFall.setFill(Color.RED);
						}
					}
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}else {
    			if(OnOfPulsometro.isSelected()) {
	    			fw.write("Caidas=false|Pulsometro=true\n");
	    		}else {
	    			fw.write("Caidas=false|Pulsometro=false\n");
	    		}
    		}
    		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void On_pulsometroActioned_Clicked() {
    	infoPulsometro.getChildren().clear();
    	try {
    		String path = null; //"sensores/" + ControladorLogin.FAMILIAR.getPaciente() + "-sensores.txt";
    		FileWriter fw = new FileWriter(path, true);
    		if(OnOfPulsometro.isSelected()) {
	    		if(DetectorCaidas1.isSelected()) {
	    			fw.write("Caidas=true|Pulsometro=true\n");
	    		}else {
	    			fw.write("Caidas=false|Pulsometro=true\n");
	    		}

				try {
		    		//Utilizo una lista para leer el fichero alreves
		    		FileReader fr = new FileReader(path);
		    		BufferedReader bf = new BufferedReader(fr);
		    		LinkedList<String> list = new LinkedList<String>();			 
					String sCadena;
					while ((sCadena = bf.readLine())!=null) {
					  list.add(sCadena);				
					}
					Iterator<String> it = list.descendingIterator();
					//Creo tabla
					
					final NumberAxis xAxis = new NumberAxis();
			        final NumberAxis yAxis = new NumberAxis();
			        xAxis.setLabel("Hours ago");
			        //creating the chart
			        final LineChart<Number,Number> lineChart = 
			                new LineChart<Number,Number>(xAxis,yAxis);
					XYChart.Series series = new XYChart.Series();
					String linea;
					int cont = 0;
					int valor;
					while(it.hasNext() && cont < 15) {
						linea = it.next();
						linea = linea.replace(" ","");
						try {
							valor = Integer.parseInt(linea);
							series.getData().add(new XYChart.Data(-cont, valor));
							cont++;
						}catch(Exception e){
							
						}
						
					}
					lineChart.getData().add(series);
					infoPulsometro.getChildren().add(lineChart);
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}else {
    			if(DetectorCaidas1.isSelected()) {
	    			fw.write("Caidas=true|Pulsometro=false\n");
	    		}else {
	    			fw.write("Caidas=false|Pulsometro=false\n");
	    		}
    		}
    		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	//chat
    @FXML
    void enviarActioned(ActionEvent event) {
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    	String aux = mensaje1.getText();
    	//par chat con el nombre del cuidador
    	//String path = NombreCuidador + "chat.txt";
    	//para chat con el nombre del paciente
    	//String path = NombrePaciente + "chat.txt";

    	try {
    		String path = "chat/" + user.getCorreo() + "-chat.txt";
			FileWriter fw = new FileWriter(path, true);
			fw.write(System.lineSeparator());
			fw.write(user.getNombre());
			fw.write("\t");
			fw.write(timeStamp);
			fw.write("\t");
			fw.write(aux);
			fw.write(System.lineSeparator());
			fw.flush();
			String content = readFile(path);
			//Text contenido = new Text(content);
			chatStreaming.setText(content);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
