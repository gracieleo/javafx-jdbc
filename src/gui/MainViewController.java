package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	
	//tratar eventos 
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		//loadView("/gui/DepartmentList.fxml");
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		
	}
	
	//função para trabalhar com outra tela
	//synchronized = garante que a execução não será interropmpida visto que a aplicação trabalha com várias threds sendo carregadas
	
	private synchronized void loadView(String absoluteName) {   					//absolute pq vai passar o caminho completo
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));  
			VBox newVBox = loader.load();	
			
			//mostrar aboutview dentro da view principal
			//1 - pegar referencia da cena
			Scene mainScene = Main.getMainScene();
			
			//2- pegar referencia para o VBox da janela principal
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0); 			//1º filho de VBox
			mainVBox.getChildren().clear(); 						//limpa todo o menu
			mainVBox.getChildren().add(mainMenu);	
			mainVBox.getChildren().addAll(newVBox.getChildren());	//add o menu e filhos do About.fxml
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	
	private synchronized void loadView2(String absoluteName) {   					//absolute pq vai passar o caminho completo
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));  
			VBox newVBox = loader.load();	
			
			//mostrar aboutview dentro da view principal
			//1 - pegar referencia da cena
			Scene mainScene = Main.getMainScene();
			
			//2- pegar referencia para o VBox da janela principal
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0); 			//1º filho de VBox
			mainVBox.getChildren().clear(); 						//limpa todo o menu
			mainVBox.getChildren().add(mainMenu);	
			mainVBox.getChildren().addAll(newVBox.getChildren());	//add o menu e filhos do About.fxml
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	

}
