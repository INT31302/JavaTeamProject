#include <stdio.h>

void main(){
	char ch;
	int count = 1, count1 = 0, conver = 97;
	while (count <= 30){
		ch = getch();
		if (ch == conver){
			printf("%c - %d\n", ch - 32, count);
			conver++;
			count1 = 0;
		}
		else{
			printf("%c", ch);
			count1++;
		}
		if (count1 == 15){
			printf("%c - %d\n", conver - 32, count);
			break;
		}
		if (ch >= '0' && ch <= '9'){
			printf("%c - %d\n", conver - 32, count);
			break;
		}
		count++;
	}
}
