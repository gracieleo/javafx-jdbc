package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
		//passar tbm uma ação de inicialização
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});  //about não leva em nada, apenas tem um texto
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		
	}
	
	//função para trabalhar com outra tela
	//synchronized = garante que a execução não será interropmpida visto que a aplicação trabalha com várias threds sendo carregadas
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {   					//absolute pq vai passar o caminho completo
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
			
			//inicializa a ação que passar como parametro 
			//retorna o controlador do tipo que chamar nos métodos acima de loadView
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
