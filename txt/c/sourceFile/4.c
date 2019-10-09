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
		for (b = 1; b < (a + 1) / 2; b++){
			for (c = b; c< a / 2 + 1; c++){
				printf(" ");
			}
			printf("*");
			if (b>1 && b != a){
				for (c = 0; c < b * 2 - 3; c++){
					printf(" ");
				}
				printf("*");
			}
			if (b == (a + 1) / 2 - 1){
				printf("\n");
				for (c = 1; c < a + 1; c++){
					printf("*");
				}
			}
			printf("\n");
		}
		for (b = 1; b < a / 2 + 1; b++){
			for (c = 0; c < b; c++){
				printf(" ");
			}
			printf("*");
			if (b != (a / 2)){
				for (c = b * 2 - 1; c < (a * 2 - 6) / 2; c++){
					printf(" ");
				}
				printf("*");
			}
			printf("\n");
		}
	}
}