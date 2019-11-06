#include <stdio.h>

void main(){
	char ch;
	int num = 0, num2 = 0, temp = 0, cnt = 0, cnt1 = 0;
	int max = '9', min = '0', result = 0;
	double dresult = 0;
	char myoprator = ' ';
	while (1){
		ch = getch();
		printf("%c", ch);
		if (ch >= min && ch <= max){
			num = num * 10;
			temp = ch - min;
			num += temp;
			if (num > 999){
				printf("\n1000보다 작은 수를 입력하세요.\n");
				break;
			}
		}
		switch (ch){
		case '+':
			myoprator = '+';
			break;
		case '-':
			myoprator = '-';
			break;
		case'*':
			myoprator = '*';
			break;
		case'/':
			myoprator = '/';
			break;
		}
		if (myoprator != ' '){
			if (num == 0){
				printf("\n상수를 입력하세요.\n");
				break;
			}
			temp = 0;
			break;
		}
	}
	while (1){
		ch = getch();
		printf("%c", ch);
		if (ch >= min && ch <= max){
			num2 = num2 * 10;
			temp = ch - min;
			num2 += temp;
			if (num2 > 999){
				printf("\n1000보다 작은 수를 입력하세요.\n");
				break;
			}
		}
		if (ch == '='){
			if (num2 == 0){
				printf("\n상수를 먼저 입력하세요.\n");
			}
			else{
				switch (myoprator){
				case '+':
					result = num + num2;
					break;
				case '-':
					result = num - num2;
					break;
				case '*':
					result = num * num2;
					break;
				case '/':
					if ((double)num / num2 - num / num2 == 0)
					{
						result = num / num2;
					}
					else
					{
						dresult = (double)num / num2;
						cnt++;
					}
					break;
				}
				switch (cnt){
				case 1:
					printf("%.1lf", dresult);
					break;
				default:
					printf("%d", result);
				}
			}
			break;

		}
	}
}