package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.service.SellerService;

public class SellerFormController implements Initializable{
	
	private Seller entity;  //entidade associado ao formulário
	
	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	//componentes da tela
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	
	public void setSeller(Seller entity) {  //instancia do departament 
		this.entity = entity;
	}
	
	public void setSellerService(SellerService service) {   
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		//escrever o listener na lista
		dataChangeListeners.add(listener);
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		//caso programador esqueça de injetar dependencias no listcontroller (manual)
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErros());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	//pega dados do formulário e retorna um novo objeto
	private Seller getFormData() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText())); //converter para inteiro
		
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if (exception.getErros().size() > 0) { //se há na coleção ao menos um erro
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	
	private void initializeNodes() {
		//algumas restrições nos campos text:
		Constraints.setTextFieldInteger(txtId); 
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		//popular as caixas de texto
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());		
	}

	private void setErrorMessages(Map<String, String> erros) { //preencher erros nas caixas no formulário
		Set<String> fields = erros.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(erros.get("name"));
		}
	}
	
}
