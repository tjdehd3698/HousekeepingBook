package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeController implements Initializable{
	@FXML
	private TableView<Account> accountTableView;
	@FXML
	private TableColumn<Account, String> preparationDate;
    @FXML
    private TableColumn<Account, String> type;
    @FXML
    private TableColumn<Account, String> money;
    @FXML
    private TableColumn<Account, String> category;
    @FXML
    private TableColumn<Account, String> balance;
    @FXML
    private ComboBox<String> chType;
	@FXML
	private ComboBox<String> chCategory;
	@FXML
	private DatePicker chDate;
	@FXML
	private TextField chMoney;
	@FXML
    private ComboBox<String> monthBox;
	@FXML
	private ComboBox<String> yearBox;
	
	ObservableList<String> monthList = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
	ObservableList<String> yearList = FXCollections.observableArrayList(AccountList.getYearList());

    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	LocalDate date = LocalDate.now();
		preparationDate.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getPreparationDate().toString()));
		type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		money.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMoney())));
		category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
		balance.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBalance())));
		accountTableView.setItems(FXCollections.observableList(AccountList.getAccount(Integer.toString(date.getYear()),Integer.toString(date.getMonthValue()))));

    	chType.setItems(AccountList.getTypeList());
    	monthBox.setItems(monthList);
    	yearBox.setItems(yearList);
    	yearBox.setValue(Integer.toString(date.getYear()));
    	monthBox.setValue(Integer.toString(date.getMonthValue()));
    }
    
    @FXML
	public void check(ActionEvent event){
		if(yearBox.getValue()!=null&& monthBox.getValue()!=null)
			accountTableView.setItems(FXCollections.observableList(AccountList.getAccount(yearBox.getValue(), monthBox.getValue())));
	}
    
    @FXML
    public void change() throws Exception{
    	try {
	    	if(chType.getValue()!=null&&chCategory.getValue()!=null&&chDate.getValue()!=null&&chMoney.getText()!=null) {
		    	Account act=accountTableView.getSelectionModel().getSelectedItem();
		    	if(act.getPreparationDate()!=chDate.getValue()){
		    		AccountList.deleteBalance(act);
		    		Account tmp = new Account(chDate.getValue(),Integer.parseInt(chMoney.getText()),chType.getValue(),chCategory.getValue(),0);
					AccountList.getList().add(tmp);
					Collections.sort(AccountList.getList());
					AccountList.changeBalance(tmp);
		    	}
		    	else if(act.getType()!=chType.getValue()) {
		    		AccountList.changeType(act,Integer.parseInt(chMoney.getText()));
		    		act.setType(chType.getValue());
					act.setMoney(Integer.parseInt(chMoney.getText()));
					act.setCategory(chCategory.getValue());
		    	}
		    	else if(act.getMoney()!=Integer.parseInt(chMoney.getText())) {
		    		AccountList.changeMoney(act,Integer.parseInt(chMoney.getText()));
					act.setMoney(Integer.parseInt(chMoney.getText()));
		    		act.setCategory(chCategory.getValue());
		    	}
		    	else {
		    		act.setCategory(chCategory.getValue());
		    	}
		    	accountTableView.setItems(FXCollections.observableList(AccountList.getAccount(yearBox.getValue(),monthBox.getValue())));
		    	
	    	}
	    	else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
			    Parent root = (Parent) loader.load();
			    Stage stage = new Stage();
			    stage.setTitle("popup");
			    stage.setScene(new Scene(root));
			    stage.show();	
			}
		}catch(Exception e) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
		    Parent root = (Parent) loader.load();
		    Stage stage = new Stage();
		    stage.setTitle("popup");
		    stage.setScene(new Scene(root));
		    stage.show();
		}
		
    }
    
    @FXML
    public void delete() {
    	Account act=accountTableView.getSelectionModel().getSelectedItem();
    	AccountList.deleteBalance(act);
    }
    
    @FXML
	public void typeChangeAction(ActionEvent event) throws Exception {
		if(chType.getValue()=="수입") {
			chCategory.setItems(FXCollections.observableList(AccountList.getIncomeList()));
		}
		else {
			chCategory.setItems(FXCollections.observableList(AccountList.getOutcomeList()));
		}
	}
    

}
