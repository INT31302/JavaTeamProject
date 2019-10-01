package _4;

public class 실습4_4 {
	public static void main(String[] args) {
			Rectangle r = new Rectangle(2,2,8,7);
			Rectangle s = new Rectangle(5,5,6,6);
			Rectangle t = new Rectangle(1,1,10,10);
			
			r.show();
			System.out.println("s의 면적은 " + s.square());
			if(t.contains(r)) System.out.println("t는 r을 포함합니다.");
			if(t.contains(s)) System.out.println("t는 s을 포함합니다.");
	}
}

class Rectangle{
	private int x, y, width, height;
	public Rectangle() {
		this(0,0,0,0);
	}
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void show() {
		System.out.println("("+x+","+y+")에서 크기가 "+ width +"x" + height+"인 사각형");
	}
	
	public int square() {
		return width * height;
	}
	
	public boolean contains(Rectangle r)
	{
		int numx1, numy1;
		int numx2, numy2;
		numx1 = x + width;
		numx2 = r.x + r.width;
		
		numy1 = y + height;
		numy2 = r.y + r.height;
		
		if(x>r.x || y>r.y || numx1>numx2 || numy1>numy2){
			return true;
		}
		else return false;
	}
}
