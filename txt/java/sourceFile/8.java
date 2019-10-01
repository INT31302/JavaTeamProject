package _8;
import java.util.*;

class Phone
{
	String name, tel;
	public Phone(String name, String tel)
	{
		this.name = name;
		this.tel = tel;
	}
	
	public String getName() {return name;}
	public String getTel() {return tel;}
}
public class _8번 {
	private Scanner s;
	private String name ,tel;
	private int cnt;
	private Phone p[];
	public _8번()
	{
		s = new Scanner(System.in);
		System.out.print("인원수>>");
		cnt = s.nextInt();
		p = new Phone[cnt];
		for(int i =0; i<p.length; i++)
		{
			System.out.print("이름과 전화번호(이름과 번호는 빈 칸없이 입력>>");
			name = s.next();
			tel = s.next();
			p[i] = new Phone(name,tel);
		}
		System.out.println("저장되었습니다...");
	}
	
	public void run()
	{
		while(true)
		{
			System.out.print("검색할 이름>>");
			s = new Scanner(System.in);
			name = s.next();
			if(name.equals("그만")) { s.close(); return;}
			find();
		}
	}
	
	public void find()
	{
		for(int i=0; i<p.length; i++)
		{
			if(name.equals(p[i].getName()))
			{
				System.out.println(name+"의 번호는 "+ p[i].getTel() +" 입니다.");
				return;
			}
		}
		System.out.println(name+" 이 없습니다.");
	}
	
	public static void main(String[] args) {
		_8번 PhoneBook = new _8번();
		PhoneBook.run();
	}
}
