#include <stdio.h>

void main(){
	char ch;
	int num = 0, temp = 0, max = '9', min = '0';
	int isNumber = 1;
	while (isNumber){
		ch = getch();
		printf("%c", ch);
		if (ch >= min && ch <= max){
			num = num * 10;
			temp = ch - min;
			num += temp;
		}
		else{
			switch (ch){
			case 13:
			case '+':
			case '=':
				printf("\n�Է��� ���� %d�Դϴ�.\n", num);
				isNumber = 0;
				break;
			default:
				printf("\n�߸��� ���� �Է��Ͽ����ϴ�.\n");
				isNumber = 0;
				break;
			}
		}
	}
}