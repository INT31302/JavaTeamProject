package _3번;

public class 실습4_3 {
	public static void main(String[] args) {
		Song s = new Song("Dancing Queen","ABBA", "스웨덴", 1978);
		s.show();
	}
}

class Song{
	private String title, artist, country;
	private int year;
	public Song()
	{
		this("","","",0);
	}
	public Song(String title, String artist, String country, int year)
	{
		this.title = title;
		this.artist = artist;
		this.country = country;
		this.year = year;
	}
	
	public void show()
	{
		System.out.println(year+"년 "+country+"국적의 "+artist+"가 부른 "+title);
	}
}