package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MenuController implements Initializable {
	//메뉴화면 controller
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane ap;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AccountList.getAccountFromText();	//파일에서 저장되어있는 데이터 가져오는 함수 실행
	}
	
	@FXML
	private void insert(MouseEvent event) {	//
		loadPage("insert");
	}
	
	@FXML
	private void stats(MouseEvent event) {
		loadPage("stats");
	}
	
	@FXML
	private void change(MouseEvent event) {
		loadPage("change");
	}
	
	@FXML
	private void barchart(MouseEvent event) {
		loadPage("barChart");
	}
	
	private void loadPage(String page) {
		Parent root=null;
		try {
			root =FXMLLoader.load(getClass().getResource(page+".fxml"));
			System.out.println(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bp.setCenter(root);
	}
	
	@FXML
	public void exit(MouseEvent event){
		AccountList.inputAccountToText();
    }
	
}
