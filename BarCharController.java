package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class BarCharController implements Initializable{
	@FXML private BarChart<String,Integer> income;
	@FXML private BarChart<String,Integer> outcome;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LocalDate date = LocalDate.now();
		int month =date.getMonthValue();
		int year= date.getYear()-1;
		System.out.println(month+"/"+year);
		while(month!=date.getMonthValue()||year!=date.getYear()) {
			ArrayList<Account> list=AccountList.getAccount(Integer.toString(year),Integer.toString(month));
			Iterator<Account> iterator = list.iterator();
			Iterator<String> iterator2 = AccountList.getIncomeList().iterator();
			Iterator<String> iterator3 = AccountList.getOutcomeList().iterator();
			Series<String, Integer> incomechart = new XYChart.Series<String, Integer>();
			incomechart.setName(Integer.toString(month)+"월");
			XYChart.Series<String, Integer> outcomechart = new XYChart.Series<String, Integer>();
			outcomechart.setName(Integer.toString(month)+"월");
			
			HashMap<String,Integer> incomehash=  new HashMap<String,Integer>();
			HashMap<String,Integer> outcomehash=  new HashMap<String,Integer>();
			ObservableList<Data<String, Integer>> incomelist = FXCollections.observableArrayList();
			ObservableList<Data<String, Integer>> outcomelist = FXCollections.observableArrayList();
	
			
			while (iterator2.hasNext()) {
				incomehash.put(iterator2.next(),0);
			}
			while (iterator3.hasNext()) {
				outcomehash.put(iterator3.next(),0);
			}
			
			while (iterator.hasNext())
			{
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
				incomelist.add(new Data<String, Integer>(key,incomehash.get(key)));
	        }
			for( String key : outcomehash.keySet() ){
				outcomelist.add(new Data<String, Integer>(key,outcomehash.get(key)));
	        }
			
			incomechart.setData(incomelist);
			income.getData().add(incomechart);
			outcomechart.setData(outcomelist);
			outcome.getData().add(outcomechart);
			income.setCategoryGap(30);
			outcome.setCategoryGap(30);

			month+=1;
			if(month==13) {
				year+=1;
				month=1;
			}
		}
	}

}
