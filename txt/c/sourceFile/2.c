#include <stdio.h>
#include <string.h>

void main()
{
	char data[50], data1[2];
	int i = 0, j = 0;
	printf("���ڿ� �Է��ϼ��� :");
	gets(data);
	int len = strlen(data);
	int count = 0;
	
	printf("������ ���ڸ� �Է��ϼ���. : ");
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
		printf("���ڿ� \"%s\"���� ���� \'%s\'�� �����ϴ�.\n", data, data1);
}