package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InsertController implements Initializable {
	@FXML
	private ComboBox<String> type;
	@FXML
	private ComboBox<String> category;
	@FXML
	private DatePicker preparationDate;
	@FXML
	private TextField money;
	@FXML
	private Button ok;
	@FXML
	private ComboBox<String> typecheck;
	@FXML
	private TextField newCategory;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		type.setItems(AccountList.getTypeList());
		typecheck.setItems(AccountList.getTypeList());
	}

	@FXML
	public void okHandler(ActionEvent event) throws Exception {
		try {
			String type_data = type.getValue();
			String category_data = category.getValue();
			LocalDate preparationDate_data = preparationDate.getValue();
			String money_data = money.getText();
			System.out.println(AccountList.getTypeList().size());
			if (AccountList.getIncomeList().size() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("수입분류가 없습니다.추가하세요!!");
				alert.showAndWait();
			} else if (AccountList.getOutcomeList().size() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("지출분류가 없습니다.추가하세요!!");
				alert.showAndWait();
			} else if (type_data != null && category_data != null && !money_data.equals("")
					&& preparationDate_data != null) {
				Account act = new Account(preparationDate_data, Integer.parseInt(money_data), type_data, category_data,
						0);
				AccountList.getList().add(act);
				Collections.sort(AccountList.getList());
				AccountList.changeBalance(act);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("correctPop.fxml"));
				Parent root = (Parent) loader.load();
				Stage stage = new Stage();
				stage.setTitle("popup");
				stage.setScene(new Scene(root));
				stage.show();
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
				Parent root = (Parent) loader.load();
				Stage stage = new Stage();
				stage.setTitle("popup");
				stage.setScene(new Scene(root));
				stage.show();
			}
		} catch (Exception e) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("popup");
			stage.setScene(new Scene(root));
			stage.show();
		}
	}

	@FXML
	public void add(ActionEvent event) throws Exception {
		try {
			String type_data = typecheck.getValue();
			String category_data = newCategory.getText();
			if (type_data != null && !category_data.equals("")) {
				if (type_data == "수입") {
					if (!AccountList.getIncomeList().contains(category_data)) {
						AccountList.getIncomeList().add(category_data);
						FXMLLoader loader = new FXMLLoader(getClass().getResource("correctPop.fxml"));
						Parent root = (Parent) loader.load();
						Stage stage = new Stage();
						stage.setTitle("popup");
						stage.setScene(new Scene(root));
						stage.show();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("이미 존재하는 분류입니다.");
						alert.showAndWait();
					}
				} else {
					if (!AccountList.getOutcomeList().contains(category_data)) {
						AccountList.getOutcomeList().add(category_data);
						FXMLLoader loader = new FXMLLoader(getClass().getResource("correctPop.fxml"));
						Parent root = (Parent) loader.load();
						Stage stage = new Stage();
						stage.setTitle("popup");
						stage.setScene(new Scene(root));
						stage.show();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("이미 존재하는 분류입니다.");
						alert.showAndWait();
					}
				}
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
				Parent root = (Parent) loader.load();
				Stage stage = new Stage();
				stage.setTitle("popup");
				stage.setScene(new Scene(root));
				stage.show();
			}
		} catch (Exception e) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("pop.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("popup");
			stage.setScene(new Scene(root));
			stage.show();
		}
	}

	@FXML
	public void typeChangeAction(ActionEvent event) throws Exception {
		if (type.getValue() == "수입") {
			category.setItems(FXCollections.observableList(AccountList.getIncomeList()));
		} else {
			category.setItems(FXCollections.observableList(AccountList.getOutcomeList()));
		}
	}

}
