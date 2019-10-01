package _7;
import java.util.*;

class Day{
	private String work;
	public void set(String work) {this.work = work;}
	public String get() {return work;}
	public void show() {
		if(work == null) System.out.println("없습니다.");
		else System.out.println(work+"입니다.");
	}
}

public class _7번 {
	
	private int days = 0;
	private Scanner s;
	private Day d[];
	
	public _7번(int days) {
		this.days = days;
		d = new Day[days];
		for(int i =0; i<d.length; i++) {d[i] = new Day();}
		s = new Scanner(System.in);
	}
	public void input() {
		System.out.print("날짜(1~"+days+")?");
		int input_num = s.nextInt();
		if(input_num >= 1 && input_num <=days)
		{
			System.out.print("할일(빈칸없이입력)?");
			String work = s.next();
			d[input_num-1].set(work);
			System.out.println();
		}
	}
	
	public void view() {
		System.out.print("날짜(1~"+days+")?");
		int input_num = s.nextInt();
		if(input_num >= 1 && input_num <=days)
		{
			System.out.print(input_num+"일의 할일은 ");
			d[input_num-1].show();
			System.out.println();
		}
	}
	public void finish()
	{
		s.close();
		System.out.println("프로그램을 종료합니다.");
	}
	public void run()
	{
		int menu;
		System.out.println("이번달 스케쥴 관리 프로그램.");
		while(true)
		{
			System.out.print("할일(입력:1, 보기:2, 끝내기:3>>");
			try {
				menu = s.nextInt();
				switch(menu)
				{
				case 1:
						input();
				break;
				case 2:
						view();
					break;
				case 3:
					finish();
					return;
				}
			}
			catch(Exception e)
			{
				System.out.println("error");
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
		
	}
	public static void main(String[] args) {
		_7번 april = new _7번(30);
		april.run();
	}
	
	
}



