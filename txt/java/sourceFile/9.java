package _9;

class ArrayUtil{
	public static int[] concat(int[] a, int[] b){
		int sum = a.length + b.length;
		int[] array = new int[sum];
		int index;
		
		for(index = 0; index<a.length; index++) {array[index] = a[index];}
		for(int j = 0; j<b.length; j++) {array[index] = b[j]; index++;}
		
		return array;
	}
	
	public static void print(int[] a) {
		System.out.print("[ ");
		for(int i=0; i<a.length; i++)
		{
			System.out.print(a[i]+" ");
		}
		System.out.println("]");
	}
}
public class _9번 {
	public static void main(String[] args) {
		int[] array1 = {1,5,7,9};
		int[] array2 = {3,6,-1,100,77};
		int[] array3 = ArrayUtil.concat(array1, array2);
		ArrayUtil.print(array3);
	}

}
