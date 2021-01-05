package application;

import java.time.LocalDate;

public class Account implements Comparable<Account> { // 가계부에 저장될 객체 클래스
	private LocalDate preparationDate; // 작성시간
	private int money; // 금액
	private String type; // 지출 or수입
	private String category; // 분류
	private int balance; // 잔액

	public Account(LocalDate preparationDate, int money, String type, String category, int balance) {
		super();
		this.preparationDate = preparationDate;
		this.money = money;
		this.type = type;
		this.category = category;
		this.balance = balance;
	}

	public LocalDate getPreparationDate() {
		return preparationDate;
	}

	public void setPreparationDate(LocalDate preparationDate) {
		this.preparationDate = preparationDate;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public int compareTo(Account act) { // 날짜순 정렬을 위한 override
		if (this.preparationDate.isBefore(act.getPreparationDate()))
			return -1;
		else if (this.preparationDate.isEqual(act.getPreparationDate()))
			return 0;
		else
			return 1;
	}

}
