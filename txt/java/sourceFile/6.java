package _6;
import java.util.*;

public class _6번 {
	public static void main(String[] args) {
		Scanner s= new Scanner(System.in);
		Circle c[] = new Circle[3];
		for(int i=0; i<c.length; i++)
		{
			System.out.print("x, y, radius >>>");
			double x = s.nextDouble();
			double y = s.nextDouble();
			int radius = s.nextInt();
			c[i] = new Circle(x,y,radius);
			c[i].cal();
		}
		
		for(int i=0; i<c.length; i++)
		{
			if(Circle.max == c[i].getRadius())
			{
				System.out.print("가장 면적이 큰 원은 ");
				c[i].show();
			}
		}
		s.close();
	}
}

class Circle{
	private double x,y;
	private int radius;
	static int max = 0;
	public Circle(double x, double y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	public void cal()
	{
		if(max * 2 * 3.14 < radius * 2 * 3.14)
			max = radius;
	}
	public int getRadius()
	{
		return radius;
	}
	public void show() {
		System.out.println("("+x+","+y+")"+radius);
	}
}
