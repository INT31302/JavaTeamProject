package _10번;
import java.util.*;

class Dictionary{
	private static String[] kor = {"사랑", "아기", "돈", "미래", "희망"};
	private static String[] eng = {"love", "baby", "money", "future", "hope"};
	public static String kor2Eng(String Word) {
		String err = "저의 사전에 없습니다.";
		for(int i =0; i< kor.length; i++)
		{
			if(kor[i].equals(Word))
			{
				return eng[i];
			}
		}
		return err;
	}
}
public class _10번 {
	private Scanner s;
	String kr;
	public void run() {
		s = new Scanner(System.in);
		System.out.println("한영 단어검색 프로그램입니다.");
		while(true) {
		System.out.print("한글 단어?");
		kr = s.next();
		if(kr.equals("그만")) { s.close(); return;}
		System.out.println(kr+"은 " + Dictionary.kor2Eng(kr));
		}
	}
	
	
	public static void main(String[] args) {
		_10번 DicApp = new _10번();
		DicApp.run();
	}
}
