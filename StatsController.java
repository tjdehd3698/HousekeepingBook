package application;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

public class StatsController implements Initializable {
	@FXML
    private ComboBox<String> monthBox;
	@FXML
	private ComboBox<String> yearBox;
	@FXML
	private PieChart income;
	@FXML
	private PieChart outcome;
	
	
	ObservableList<String> monthList = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
	ObservableList<String> yearList = FXCollections.observableArrayList(AccountList.getYearList());
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		monthBox.setItems(monthList);
		yearBox.setItems(yearList);
	}
	
	@FXML
	public void check(ActionEvent event){
		if(yearBox.getValue()!=null&& monthBox.getValue()!=null) {
			
			Iterator<Account> iterator = AccountList.getAccount(yearBox.getValue(),monthBox.getValue()).iterator();
			Iterator<String> iterator2 = AccountList.getIncomeList().iterator();
			Iterator<String> iterator3 = AccountList.getOutcomeList().iterator();

			HashMap<String,Integer> incomehash=  new HashMap<String,Integer>();
			HashMap<String,Integer> outcomehash=  new HashMap<String,Integer>();
			ObservableList<PieChart.Data> incomelist = FXCollections.observableArrayList();
			ObservableList<PieChart.Data> outcomelist = FXCollections.observableArrayList();

			
			while (iterator2.hasNext()) {
				incomehash.put(iterator2.next(),0);
			}
			while (iterator3.hasNext()) {
				outcomehash.put(iterator3.next(),0);
			}
			
			while (iterator.hasNext()){
				Account act= iterator.next();
				if(act.getType().equals("수입")) {
					int ti=incomehash.get(act.getCategory());
					incomehash.put(act.getCategory(),act.getMoney()+ti);
				}
				else {
					int ti=outcomehash.get(act.getCategory());
					outcomehash.put(act.getCategory(),act.getMoney()+ti);
				}
			}
			
			for( String key : incomehash.keySet() ){
				incomelist.add(new PieChart.Data(key,incomehash.get(key)));
	        }
			for( String key : outcomehash.keySet() ){
				outcomelist.add(new PieChart.Data(key,outcomehash.get(key)));
	        }
			
			income.setData(incomelist);
			outcome.setData(outcomelist);
			
			income.setLegendSide(Side.BOTTOM);
			income.setLabelsVisible(false);
			outcome.setLegendSide(Side.BOTTOM);
			outcome.setLabelsVisible(false);

		}	
	}
}
