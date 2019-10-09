#include <stdio.h>

void main(){
	int a, b, c;
	printf("정수를 입력하세요.");
	scanf_s("%d", &a);
	if (a % 2 == 0){
		printf("홀수를 입력하세요!\n");
		main();
	}
	else{
		for (b = 1; b < a + 1; b++){
			if (b == 1 || b == a){
				for (c = 1; c < a + 1; c++){
					printf("*");
				}
			}
			else{
				printf("*");
				for (c = b; c < a - 1; c++){
					printf(" ");
				}
				printf("*");
				for (c = 1; c < b - 1; c++){
					printf(" ");
				}
				printf("*");
			}
			printf("\n");
		}
	}
}