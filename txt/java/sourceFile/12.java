package _12;
import java.util.*;

class List{
	private String name[] = new String[10];
	public List() {
		for(int i =0; i<name.length; i++) {name[i] = new String(); name[i] = "---";}
	}
	public void show() {
		for(int i =0; i<name.length; i++) {System.out.print(" "+name[i]);}
		System.out.println();
	}
	public void setList(String name, int index){
		for(int i =0; i < this.name.length; i++) {
			if(this.name[i].equals(name)){
				System.out.println("이미 예약된 좌석이 있습니다.");
				return;
			}
		}
		if(this.name[index].equals("---")) {
			this.name[index] = name;
		}
		else System.out.println("이미 예약된 좌석입니다.");
	}
	
	public void delList(String name){
		for(int i = 0; i<this.name.length; i++){
			if(this.name[i].equals(name)) {
				this.name[i] = "---";
				return;
			}
		}
		System.out.println("취소 가능한 좌석이 없습니다.");
	}
}

public class _12번 {
	private Scanner s = new Scanner(System.in);
	private List l[] = new List[3];
	private String class_arr[] = {"S", "A", "B"};
	private int menu, number, class_num;
	private String name;
	public _12번(){
		System.out.println("명품콘서트홀 예약 시스템입니다.");
		for(int i = 0; i<l.length; i++) { l[i] = new List();}
	}
	private void book() {
		System.out.print("좌석구분 S(1), A(2), B(3)>>");
		try{
			class_num = s.nextInt();
			System.out.print(class_arr[class_num-1]+">>");
			l[class_num-1].show();
			System.out.print("이름>>");
			name = s.next();
			System.out.print("번호>>");
			number = s.nextInt();
			l[class_num-1].setList(name, number-1);
			
		}
		catch(Exception e)
		{
			System.out.println("알 수 없는 오류가 발생하였습니다.\n프로그램을 종료합니다.");
			s.close();
			return;
		}
	}
	private void search() {
		for(int i=0; i<class_arr.length; i++) {
			System.out.print(class_arr[i]+">>");
			l[i].show();
		}
		System.out.println("<<<조회를 완료하였습니다.>>>");
	}
	private void cancel() {
		System.out.print("좌석구분 S(1), A(2), B(3)>>");
		try{
			class_num = s.nextInt();
			System.out.print(class_arr[class_num-1]+">>");
			l[class_num-1].show();
			System.out.print("이름>>");
			name = s.next();
			l[class_num-1].delList(name);
			
		}
		catch(Exception e)
		{
			System.out.println("알 수 없는 오류가 발생하였습니다.\n프로그램을 종료합니다.");
			s.close();
			return;
		}
	}
	
	public void run(){
		while(true){
			System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
			try {
				menu = s.nextInt();
				switch(menu){
					case 1:
						book();
						break;
					case 2:
						search();
						break;
					case 3:
						cancel();
						break;
					case 4:
						s.close();
						return;
					}
			}
			catch(Exception e)
			{
				System.out.println("알 수 없는 오류가 발생하였습니다.\n프로그램을 종료합니다.");
				s.close();
				return;
			}
		}
	}
	public static void main(String[] args) {
		 _12번 Book_System = new _12번();
		 Book_System.run();
	}
}
