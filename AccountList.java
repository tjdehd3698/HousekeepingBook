package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AccountList {
	private static ArrayList<Account> list = new ArrayList<Account>();	
	private static ArrayList<String> incomeList = new ArrayList<String>();
	private static ArrayList<String> outcomeList = new ArrayList<String>();
	private static ObservableList<String> tyleList = FXCollections.observableArrayList("수입","지출");
	
	public static ArrayList<Account> getList() {
		return list;
	}
	
	public static ArrayList<String> getIncomeList() {
		return incomeList;
	}
	
	public static ArrayList<String> getOutcomeList() {
		return outcomeList;
	}
	
	public static ObservableList<String> getTypeList() {
		return tyleList;
	}
	

	public static void getAccountFromText() {
		try{
			File file = new File("text.txt");
			FileReader file_reader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(file_reader);
			if(!file.exists()) {file.createNewFile();}
			
			String income=bufReader.readLine(); 
			if(income!=null&&!income.equals(""))
			{
				String[] incomelist= income.split("/");
				for(int i=0;i<incomelist.length;i++) 
				{
					incomeList.add(incomelist[i]);
				}
			}
			
			String outcome=bufReader.readLine(); 
			if(outcome!=null&&!outcome.equals(""))
			{
				String[] outcomelist= outcome.split("/");
				for(int i=0;i<outcomelist.length;i++) 
				{
					outcomeList.add(outcomelist[i]);
				}
			}

			String line = "";
            while((line = bufReader.readLine()) != null)
            {
               String[] list= line.split("/");
               AccountList.list.add(new Account(LocalDate.parse(list[0]), Integer.parseInt(list[1]),list[2],list[3],Integer.parseInt(list[4])));
            }
			file_reader.close();
			
		}catch (FileNotFoundException e)
		{
            e.getStackTrace();
        }catch(IOException e)
		{
            e.getStackTrace();
        }
	}
	
	public static void inputAccountToText() {
		try{
			OutputStream output = new FileOutputStream("text.txt");
			Iterator<Account> iterator = list.iterator();
			Iterator<String> iterator2 = incomeList.iterator();
			Iterator<String> iterator3 = outcomeList.iterator();
			String income="",outcome="";
			while (iterator2.hasNext())
			{
				income+=iterator2.next()+"/";
			}
			if(!income.equals("")) {
				income+="\n";
				output.write(income.getBytes());
			}
			
			while (iterator3.hasNext())
			{
				outcome+=iterator3.next()+"/";
			}
			if(!outcome.equals("")) {
				outcome+="\n";
				output.write(outcome.getBytes());
			}
			
			while (iterator.hasNext())
			{
				Account act= iterator.next();
				String tmp= act.getPreparationDate().toString()+"/"+act.getMoney()+"/"+act.getType()+"/"+act.getCategory()+"/"+act.getBalance()+"\n";
				byte[] by=tmp.getBytes();
			    output.write(by);
			}
			output.close();
		}catch (FileNotFoundException e) {
            e.getStackTrace();
        }catch(IOException e){
            e.getStackTrace();
        }
	}
	
	public static ArrayList<Account> getAccount(String year,String month) {
		Iterator<Account> iterator = list.iterator();
		ArrayList<Account> monthList= new ArrayList<Account>();
		while (iterator.hasNext())
		{
			Account act= iterator.next();
			LocalDate tmp= act.getPreparationDate();
			if(tmp.getYear()==Integer.parseInt(year)&&tmp.getMonthValue()==Integer.parseInt(month)) {
				monthList.add(act);
			}
		}
		return monthList;
	}
	
	public static void changeBalance(Account act) {
		Iterator<Account> iterator = list.iterator();
		boolean check= false;
		int preBalance=0;
		while (iterator.hasNext())
		{
			Account tmp= iterator.next();
			if(tmp==act) {
				check=true;
				act.setBalance(preBalance);
			}
			
			if(check== true)
			{
				if(act.getType()=="수입")
					tmp.setBalance(tmp.getBalance()+act.getMoney());
				else 
					tmp.setBalance(tmp.getBalance()-act.getMoney());
			}
			preBalance=tmp.getBalance();
		}
	}
	
	public static void changeType(Account act,int money) {
		Iterator<Account> iterator = list.iterator();
		boolean check= false;
		while (iterator.hasNext())
		{
			Account tmp= iterator.next();
			if(tmp==act) {
				check=true;
				if(act.getType()=="수입")
					act.setBalance(act.getBalance()-act.getMoney()-money);
				else
					act.setBalance(act.getBalance()+act.getMoney()+money);
			}
			
			if(check== true)
			{
				if(act.getType()=="수입")
					tmp.setBalance(tmp.getBalance()-act.getMoney()-money);
				else 
					tmp.setBalance(tmp.getBalance()+act.getMoney()+money);
			}
		}
	}
	
	public static void changeMoney(Account act, int money) {
		Iterator<Account> iterator = list.iterator();
		boolean check= false;
		while (iterator.hasNext())
		{
			Account tmp= iterator.next();
			if(tmp==act) {
				check=true;
				if(act.getType()=="수입")
					act.setBalance(act.getBalance()-act.getMoney()+money);
				else
					act.setBalance(act.getBalance()+act.getMoney()-money);
			}
			
			if(check== true)
			{
				if(act.getType()=="수입")
					tmp.setBalance(tmp.getBalance()-act.getMoney()+money);
				else 
					tmp.setBalance(tmp.getBalance()+act.getMoney()-money);
			}
		}
	}
	
	public static void deleteBalance(Account act) {
		Iterator<Account> iterator = list.iterator();
		boolean check= false;
		while (iterator.hasNext())
		{
			Account tmp= iterator.next();
			if(tmp==act) {
				check=true;
			}
			
			if(check== true)
			{
				if(act.getType()=="수입")
					tmp.setBalance(tmp.getBalance()-act.getMoney());
				else 
					tmp.setBalance(tmp.getBalance()+act.getMoney());
			}
		}
		list.remove(act);
	}
	
	public static ArrayList<String> getYearList() {
		Iterator<Account> iterator = list.iterator();
		ArrayList<String> list= new ArrayList<String>();
		while (iterator.hasNext())
		{
			Account act= iterator.next();
			String year=Integer.toString(act.getPreparationDate().getYear());
			if(!list.contains(year))
				list.add(year);
		}
		return list;
	}
	
}
