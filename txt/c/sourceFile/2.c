#include <stdio.h>
#include <string.h>

void main()
{
	char data[50], data1[2];
	int i = 0, j = 0;
	printf("문자열 입력하세요 :");
	gets(data);
	int len = strlen(data);
	int count = 0;
	
	printf("삭제할 문자를 입력하세요. : ");
	gets(data1);
	while (i < len){
		if (data1[0] == data[i]){
			j = i;
			while (j < len){
				data[j] = data[j + 1];
				j++;
			}
			count++;
			i = 0;
		}
		else
			i++;
	}
	if (count != 0)
		puts(data);
	else
		printf("문자열 \"%s\"에는 문자 \'%s\'가 없습니다.\n", data, data1);
}