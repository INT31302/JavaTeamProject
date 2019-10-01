package _11;
import  java.util.*;

class Add{
	private static int a, b;
	static void setValue(int a2, int b2) {
		a = a2;
		b = b2;
	}
	static int calculate() {
		return a + b;
	}
	
}

class Sub{
	private static int a, b;
	static void setValue(int a2, int b2) {
		a = a2;
		b = b2;
	}
	static int calculate() {
		return a - b;
	}
}

class Mul{
	private static int a, b;
	static void setValue(int a2, int b2) {
		a = a2;
		b = b2;
	}
	static int calculate() {
		return a * b;
	}
}

class Div{
	private static int a, b;
	static void setValue(int a2, int b2) {
		a = a2;
		b = b2;
	}
	static int calculate() {
		return a / b;
	}
}
public class _11번 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int num1, num2;
		String oper;
		System.out.print("두 정수와 연산자를 입력하시오>>");
		try{
			num1 = s.nextInt();
			num2 = s.nextInt();
			oper = s.next();
			s.close();
			switch(oper)
			{
			case "+":
				Add.setValue(num1, num2);
				System.out.println(Add.calculate());
				break;
			case "-":
				Sub.setValue(num1, num2);
				System.out.println(Sub.calculate());
				break;
			case "*":
				Mul.setValue(num1, num2);
				System.out.println(Mul.calculate());
				break;
			case "/":
				Div.setValue(num1, num2);
				System.out.println(Div.calculate());
				break;
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
