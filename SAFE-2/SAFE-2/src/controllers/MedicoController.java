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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.Medico;
import model.Paciente;
import model.User;

public class MedicoController implements Initializable {
	public static Stage VentanaCuidador = null;
	private static String PacienteSensores = null;
	private static String CorreoChat = null;

	@FXML
	private Text NombrePacSensores;

	@FXML
	private Text correoPacHistorial;

	@FXML
	private MenuButton Nombre;

	@FXML
	private MenuButton menuSensores;

	@FXML
	private ImageView FotoPaciente;

	@FXML
	private MenuItem Perfil;

	@FXML
	private ImageView Foto;

	@FXML
	private ImageView FotoPacHistorial;

	@FXML
	private ImageView FotoPacChat;

	@FXML
	private ImageView FotoPacSensores;

	@FXML
	private Button newAccount;

	@FXML
	private Text FechaNacimiento;

	@FXML
	private Text Correo;

	@FXML
	private RadioButton DetectorCaidas;

	@FXML
	private CheckBox OnOfPulsometro;

	@FXML
	private MenuItem LogOut;

	@FXML
	private Button Next;

	@FXML
	private Button Chat;

	@FXML
	private Text PulsacionesMin;

	@FXML
	private Button Previous;

	@FXML
	private Text Apellido;

	@FXML
	private Button OnOff;

	@FXML
	private Text DNI;

	@FXML
	private Text NombrePaciente;

	@FXML
	private Text Pulsaciones;

	@FXML
	private Text NombrePacHistorial;

	@FXML
	private Text NombrePacChat;

	@FXML
	private Text NumTelefono;

	@FXML
	private CheckBox DetectorCaidas1;

	@FXML
	private VBox DetectorCaidas2;

	@FXML
	private HBox infoPulsometro;

	@FXML
	private HBox infoCaidas;

	@FXML
	private CheckBox OnOfPulsometro1;

	@FXML
	private TextArea chatStreaming;

	@FXML
	private TextField mensaje;

	@FXML
	private Button enviar;

	@FXML
	private Button buttonHistorial;

	@FXML
	private Button buttonChat;

	@FXML
	private MenuButton menuHistorial;

	@FXML
	private MenuButton menuChat;

	@FXML
	private TextArea historialStreaming;

	@FXML
	private Button saveChanges;

	@FXML
	private Circle CirculoFall;

	private User user;
	private PacienteDAO pacienteDAO;
	private int index;
	private List<Paciente> lstPaciente;
	private SimpleDateFormat sf;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO (don't really need to do anything here).
		System.out.println("initialize");
		pacienteDAO = new PacienteDAO();
		sf = new SimpleDateFormat("yyyy-MM-dd");
		index = 0;
	}

	public void initData(User user) {
		System.out.println("initdata");
		this.user = user;
		lstPaciente = pacienteDAO.buscarPacientes(user.getId());
		this.Previous.setDisable(true);
		postInitData();
	}
	
	private void mostarDatosPaciente(int index) {
		if(index >=0 && index < lstPaciente.size()) {
			Paciente paciente = lstPaciente.get(index);
			NombrePaciente.setText(paciente.getNombre());
			Apellido.setText(paciente.getApellidos());
			NumTelefono.setText(paciente.getTelefono());
			DNI.setText(paciente.getDNI());
			Correo.setText(paciente.getCorreo());
			FechaNacimiento.setText(sf.format(paciente.getFecha()));
//			try {
//				String DireccionFotoP = "Imagen/FotoPerfil" + paciente.getCorreo() + ".png";
//				System.out.println(DireccionFotoP);
//				Image image = new Image(DireccionFotoP);
//				FotoPaciente.setImage(image);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}

	private void postInitData() {
		// Nombre y foto  del medico
		Nombre.setText(user.getNombre() + " " + user.getApellidos());
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

		// Muestra los datos del primer paciente
		mostarDatosPaciente(index);
		
		
		Map<String, String[]> mapC = Main.leerArchivoClase();
		String[] p = mapC.get(user.getCorreo());
		Map<String, User> map = Main.leerArchivo();

		// Historial, chat y sensores(MenuButon)

		/*if (p!= null && !p[2].equals("null")) {
			p[2] = p[2].replace(" ", "");
			String[] Pacientes = p[2].split("\\|");
			User[] Paciente = new User[Pacientes.length];
			for (int i = 0; i < Pacientes.length; i++) {
				Paciente[i] = map.get(Pacientes[i]);
				MenuItem menuItem1 = new MenuItem(
						Pacientes[i] + " (" + Paciente[i].getNombre() + " " + Paciente[i].getApellidos() + ")");
				int r = i;
				menuItem1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							String path = "historial/" + Pacientes[r] + "-historial.txt";
							String content;
							content = readFile(path);
							historialStreaming.setText(content);
						}

						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							String DireccionFotoP = "Imagen/FotoPerfil" + Pacientes[r] + ".png";
							System.out.println(DireccionFotoP);
							Image image = new Image(DireccionFotoP);
							FotoPacHistorial.setImage(image);
						} catch (Exception e) {
							e.printStackTrace();
						}
						NombrePacHistorial.setText(Paciente[r].getNombre() + " " + Paciente[r].getApellidos());
						correoPacHistorial.setText(Paciente[r].getCorreo());
					}
				});
				menuHistorial.getItems().add(menuItem1);
				menuItem1 = new MenuItem(
						Pacientes[i] + " (" + Paciente[i].getNombre() + " " + Paciente[i].getApellidos() + ")");
				menuItem1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							CorreoChat = Pacientes[r];
							String path = "chat/" + Pacientes[r] + "-chat.txt";
							String content;
							content = readFile(path);
							chatStreaming.setText(content);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							String DireccionFotoP = "Imagen/FotoPerfil" + Pacientes[r] + ".png";
							System.out.println(DireccionFotoP);
							Image image = new Image(DireccionFotoP);
							FotoPacChat.setImage(image);
						} catch (Exception e) {
							e.printStackTrace();
						}
						NombrePacChat.setText(Paciente[r].getNombre() + " " + Paciente[r].getApellidos());
					}
				});
				menuChat.getItems().add(menuItem1);
				menuItem1 = new MenuItem(
						Pacientes[i] + " (" + Paciente[i].getNombre() + " " + Paciente[i].getApellidos() + ")");
				menuItem1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						// Establecer si estan encendidos o apagados
						PacienteSensores = Pacientes[r];
						infoPulsometro.getChildren().clear();
						try {

							// Leo el fichero al reves

							FileReader fr = new FileReader("sensores/" + Pacientes[r] + "-sensores.txt");
							BufferedReader bf = new BufferedReader(fr);
							LinkedList<String> list = new LinkedList<String>();
							String sCadena;
							while ((sCadena = bf.readLine()) != null) {
								list.add(sCadena);
							}
							Iterator<String> it = list.descendingIterator();
							boolean encontrado = false;
							while (it.hasNext() && encontrado == false) {
								String Linea = it.next();
								System.out.println(Linea);
								String[] info = Linea.split("\\|");
								String[] infoC = info[0].split("=");
								if (infoC[0].equals("Caidas")) {
									encontrado = true;
									if (infoC[1].equals("true")) {
										DetectorCaidas1.setSelected(true);
									} else {
										DetectorCaidas1.setDisable(true);
									}
									String[] infoP = info[1].split("=");
									if (infoP[1].equals("true")) {
										OnOfPulsometro1.setSelected(true);
									} else {
										OnOfPulsometro1.setDisable(true);
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

						if (OnOfPulsometro1.isSelected()) {
							try {
								String path = "sensores/" + Pacientes[r] + "-sensores.txt";
								// Utilizo una lista para leer el fichero alreves
								FileReader fw = new FileReader(path);
								BufferedReader bf = new BufferedReader(fw);
								LinkedList<String> list = new LinkedList<String>();
								String sCadena;
								while ((sCadena = bf.readLine()) != null) {
									list.add(sCadena);
								}
								Iterator<String> it = list.descendingIterator();
								// Creo tabla

								final NumberAxis xAxis = new NumberAxis();
								final NumberAxis yAxis = new NumberAxis();
								xAxis.setLabel("Hours ago");
								// creating the chart
								final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
								XYChart.Series series = new XYChart.Series();
								String linea;
								int cont = 0;
								int valor;
								while (it.hasNext() && cont < 15) {
									linea = it.next();
									linea = linea.replace(" ", "");
									try {
										valor = Integer.parseInt(linea);
										series.getData().add(new XYChart.Data(-cont, valor));
										cont++;
									} catch (Exception e) {

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
						if (DetectorCaidas1.isSelected()) {
							try {
								String path = "sensores/" + Pacientes[r] + "-sensores.txt";
								FileReader fw = new FileReader(path);

								// Utilizo una lista para leer el fichero alreves

								BufferedReader bf = new BufferedReader(fw);
								LinkedList<String> list = new LinkedList<String>();
								String sCadena;
								while ((sCadena = bf.readLine()) != null) {
									list.add(sCadena);
								}
								Iterator<String> it = list.descendingIterator();
								String Valor = "n";
								while (it.hasNext() && !Valor.equals("false") && !Valor.equals("true")) {
									Valor = it.next();
									if (Valor.equals("false")) {

										CirculoFall.setFill(Color.LIGHTGREEN);
									} else if (Valor.equals("true")) {

										CirculoFall.setFill(Color.RED);
									}
								}
								bf.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							CirculoFall.setFill(Color.SNOW);
						}
						try {
							String DireccionFotoP = "Imagen/FotoPerfil" + Pacientes[r] + ".png";
							System.out.println(DireccionFotoP);
							Image image = new Image(DireccionFotoP);
							FotoPacSensores.setImage(image);
						} catch (Exception e) {
							e.printStackTrace();
						}
						NombrePacSensores.setText(Paciente[r].getNombre() + " " + Paciente[r].getApellidos());
					}
				});
				menuSensores.getItems().add(menuItem1);
			}
		}*/

	}

	@FXML
	void On_CaidaFalso_Clicked(ActionEvent event) {

		try {
			if (PacienteSensores != null && OnOfPulsometro1.isSelected()) {
				File text = new File("sensores/" + PacienteSensores + "-sensores.txt");
				FileWriter fw = new FileWriter(text, true);
				fw.write("false\n");
				fw.close();
				CirculoFall.setFill(Color.LIGHTGREEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void on_newAccount_Clicked(ActionEvent event) {
		try {
			Stage stageA = (Stage) newAccount.getScene().getWindow();
			VentanaCuidador = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Cuidador/RegisterPaciente.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void on_Perfil_Clicked(ActionEvent event) {
		try {
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			VentanaCuidador = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Perfil.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@FXML
	void on_LogOut_Clicked(ActionEvent event) {
		try {
			Stage stageA = (Stage) Nombre.getScene().getWindow();

			stageA.close();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static Medico DatosCuidador(String correo) {
		System.out.print("Entro en DatosPaciente");

		Map<String, String[]> map = Main.leerArchivoClase();
		String[] Datos = map.get(correo);
		Medico cuidador = null;
//		try {
//			cuidador = new Medico(Datos[1], Datos[2], Datos[3]);
//	    	System.out.println("\tCuidador: " + cuidador.getAdministrador());
//			System.out.println("\tFamiliar: " + cuidador.getPaciente());
//			System.out.println("\tRuta: " + cuidador.getespecificaciones());
//		}catch(Exception e){
//			System.out.println(e);
//		}
		return cuidador;
	}

	private String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	@FXML
	void enviarActioned() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String aux = mensaje.getText();
		// par chat con el nombre del cuidador
		// String path = NombreCuidador + "chat.txt";
		// para chat con el nombre del paciente
		// String path = NombrePaciente + "chat.txt";

		try {
			String path = "chat/" + CorreoChat + "-chat.txt";
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
			// Text contenido = new Text(content);
			chatStreaming.setText(content);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mensaje.clear();
		mensaje.requestFocus();
	}

	@FXML
	void On_Previous_Clicked() {
		if(index > 0 && index <= lstPaciente.size()) {
			index--;
			this.Next.setDisable(false);
			mostarDatosPaciente(index);
		}else {
			this.Previous.setDisable(true);
		}
//		Map<String, String[]> mapC = Main.leerArchivoClase();
//		String[] p = mapC.get(user.getCorreo());
//		String CorreoAnt = Correo.getText();
//		if (!p[2].equals("null")) {
//			p[2] = p[2].replace(" ", "");
//			String[] Pacientes = p[2].split("\\|");
//
//			Map<String, User> map = Main.leerArchivo();
//			int l = Pacientes.length;
//			System.out.println("Pacientes[0] = " + Pacientes[0]);
//			System.out.println("Numero pacientes " + l);
//			User[] Paciente = new User[l];
//			int numPac = 0;
//			for (int i = 0; i < l; i++) {
//				System.out.println("Datos paciente " + i + " " + map.get(Pacientes[i]));
//				Paciente[i] = map.get(Pacientes[i]);
//				// System.out.println("Paciente: " + Paciente[i] + " Paciente anterior: " +
//				// CorreoAnt);
//				if (CorreoAnt.equals(Pacientes[i])) {
//					numPac = i - 1;
//					// System.out.println("Numero Paciente " + numPac);
//					if (numPac < 0) {
//						numPac = l - 1;
//						// System.out.println("Numero Paciente " + numPac);
//					}
//				}
//			}
//
//			NombrePaciente.setText(Paciente[numPac].getNombre());
//			Apellido.setText(Paciente[numPac].getApellidos());
//			NumTelefono.setText(Paciente[numPac].getTelefono());
//			DNI.setText(Paciente[numPac].getDNI());
//			Correo.setText(Paciente[numPac].getCorreo());
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			FechaNacimiento.setText(sf.format((Paciente[numPac].getFecha())));
//
//			try {
//				String DireccionFotoP = "Imagen/FotoPerfil" + Paciente[numPac].getCorreo() + ".png";
//				System.out.println(DireccionFotoP);
//				Image image = new Image(DireccionFotoP);
//				FotoPaciente.setImage(image);
//			} catch (Exception e) {
//				String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
//				System.out.println(DireccionFotoP);
//				Image image = new Image(DireccionFotoP);
//				FotoPaciente.setImage(image);
//				e.printStackTrace();
//			}
//		}
	}

	@FXML
	void On_Next_Clicked() {
		
		if(index < lstPaciente.size() - 1) {
			index++;
			this.Previous.setDisable(false);
			mostarDatosPaciente(index);
		}else {
			this.Next.setDisable(true);
		}
		
		
//		Map<String, String[]> mapC = Main.leerArchivoClase();
//		String[] p = mapC.get(user.getCorreo());
//		String CorreoAnt = Correo.getText();
//		if (!p[2].equals("null")) {
//			p[2] = p[2].replace(" ", "");
//			String[] Pacientes = p[2].split("\\|");
//
//			Map<String, User> map = Main.leerArchivo();
//			int l = Pacientes.length;
//			System.out.println("Pacientes[0] = " + Pacientes[0]);
//			System.out.println("Numero pacientes " + l);
//			User[] Paciente = new User[l];
//			int numPac = 0;
//			for (int i = 0; i < l; i++) {
//				System.out.println("Datos paciente " + i + " " + map.get(Pacientes[i]));
//				Paciente[i] = map.get(Pacientes[i]);
//				// System.out.println("Paciente: " + Paciente[i] + " Paciente anterior: " +
//				// CorreoAnt);
//				if (CorreoAnt.equals(Pacientes[i])) {
//					numPac = i + 1;
//					// System.out.println("Numero Paciente " + numPac);
//					if (numPac >= l) {
//						numPac = 0;
//						// System.out.println("Numero Paciente " + numPac);
//					}
//				}
//			}
//
//			NombrePaciente.setText(Paciente[numPac].getNombre());
//			Apellido.setText(Paciente[numPac].getApellidos());
//			NumTelefono.setText(Paciente[numPac].getTelefono());
//			DNI.setText(Paciente[numPac].getDNI());
//			Correo.setText(Paciente[numPac].getCorreo());
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			FechaNacimiento.setText(sf.format((Paciente[numPac].getFecha())));
//
//			try {
//				String DireccionFotoP = "Imagen/FotoPerfil" + Paciente[numPac].getCorreo() + ".png";
//				System.out.println(DireccionFotoP);
//				Image image = new Image(DireccionFotoP);
//				FotoPaciente.setImage(image);
//			} catch (Exception e) {
//				String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
//				System.out.println(DireccionFotoP);
//				Image image = new Image(DireccionFotoP);
//				FotoPaciente.setImage(image);
//				e.printStackTrace();
//			}
//		}
	}

	@FXML
	void On_SaveChanges_Clicked() {
		String path = "historial/" + correoPacHistorial.getText() + "-historial.txt";
		String aux = historialStreaming.getText();
		try {

			FileWriter fw = new FileWriter(path);
			fw.write(aux);
			fw.write(System.lineSeparator());
			fw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
