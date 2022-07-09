package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {

	//referencias para os componentes da tela DepartmentList
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;  //<TipoDaEntidade, TipoDaColuna>
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		initializeNodes();
	}

	
	private void initializeNodes() {             //padrão para iniciar o comportamento das Colunas
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//ajustar tamanho da table em relação a janela
		Stage stage = (Stage) Main.getMainScene().getWindow();  //main é superclasse de stage 
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty()); //fazer table acompanhar altura da janela
	}

}
