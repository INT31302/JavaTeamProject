package _1번;

public class 실습4_1번 {
	public static void main(String[] args) {
			TV myTV = new TV("LG",2017,32);
			myTV.show();
	}
}

class TV {
	String manufacturer;
	int year, inch;
	public TV(String manufacturer, int year, int inch ){
		this.manufacturer = manufacturer;
		this.year = year;
		this.inch = inch;
	}
	
	public void show() {
		System.out.println(manufacturer+"에서 만든 "+year+"년형 "+inch+"인치 TV");
	}
}