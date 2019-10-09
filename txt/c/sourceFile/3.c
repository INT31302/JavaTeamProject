#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct date{
	int year, month, day;
};

struct student{
	char name[9];
	int score[4];
	struct date birthday;
};

struct backup{
	char name[9];
	int score[4];
	struct date birthday;
};

int search(struct student clase[], char sname[], char rname[], int size);
void delete(struct student clase[], char dname[], int check[], int size);
int add(struct student clase[], char aname[], int check[], int *size);
void output(struct student clase[], int check[], int size);

void main(){
	int i, size;
	struct student *clase;
	int *check;
	char sname[10];
	char rname[10];
	char dname[10];
	char aname[10];

	printf("배열 입력 ");
	scanf("%d", &size);
	clase = (struct student *)malloc(sizeof(struct student)*size);
	check = (int *)malloc(sizeof(int)*size);
	printf("이름 1차점수 2차점수 3차점수 4차점수 년 월 일\n");
	for (i = 0; i < size; i++){
		scanf("%s %d %d %d %d %d %d %d", clase[i].name,
			&clase[i].score[0], &clase[i].score[1], &clase[i].score[2], &clase[i].score[3],
			&clase[i].birthday.year, &clase[i].birthday.month, &clase[i].birthday.day);
		check[i] = 1;
	}
	printf("검색할 이름은? ? ");
	scanf("%s", sname);
	printf("바꿀 이름은? ");
	scanf("%s", rname);
	search(clase, sname, rname, size);
	output(clase, check, size);
	printf("지울 이름은? ");
	scanf("%s", dname);
	delete(clase, dname, check, size);
	printf("추가할 이름은? ");
	add(clase, aname,check, &size);
	output(clase, check, size);
}

int search(struct student clase[], char sname[], char rname[], int size)
{
	int i;
	for (i = 0; i < size; i++){
		if (strcmp(clase[i].name, sname) == 0)
			strcpy(clase[i].name, rname);
	}
}

void output(struct student clase[], int check[], int size){
	int i;
	for (i = 0; i < size; i++){
		if (check[i] == 1){
			printf("%s %d %d %d %d %d %d %d\n", clase[i].name,
				clase[i].score[0], clase[i].score[1], clase[i].score[2], clase[i].score[3],
				clase[i].birthday.year, clase[i].birthday.month, clase[i].birthday.day);
		}
	}
}

void delete(struct student clase[], char dname[], int check[], int size){
	int i;
	for (i = 0; i < size; i++){
		if (strcmp(clase[i].name, dname) == 0)
	}
}

int add(struct student clase[], char aname[],int check[], int *size)
{
	int i;
	int new_size = *size + 1;
	struct backup *temp;
	int *temp2;
	temp = (struct backup *)malloc(sizeof(struct backup)*new_size);
	temp2 = (int *)malloc(sizeof(int)*new_size);
	for (i = 0; i < *size; i++){
		strcpy(temp[i].name, clase[i].name);
		temp[i].score[0] = clase[i].score[0];
		temp[i].score[1] = clase[i].score[1];
		temp[i].score[2] = clase[i].score[2];
		temp[i].score[3] = clase[i].score[3];
		temp[i].birthday.year = clase[i].birthday.year;
		temp[i].birthday.month = clase[i].birthday.month;
		temp[i].birthday.day = clase[i].birthday.day;
		temp2[i] = check[i];
	}

	free(clase);
	free(check);
	for (i = 0; i < *size; i++){
		strcpy(clase[i].name, temp[i].name);
		clase[i].score[0] = temp[i].score[0];
		clase[i].score[1] = temp[i].score[1];
		clase[i].score[2] = temp[i].score[2];
		clase[i].score[3] = temp[i].score[3];
		clase[i].birthday.year = temp[i].birthday.year;
		clase[i].birthday.month = temp[i].birthday.month;
		clase[i].birthday.day = temp[i].birthday.day;
		check[i] = temp2[i];
	}

	*size = new_size;
	i = *size - 1;
	scanf("%s %d %d %d %d %d %d %d", &clase[i].name,
		&clase[i].score[0], &clase[i].score[1], &clase[i].score[2], &clase[i].score[3],
		&clase[i].birthday.year, &clase[i].birthday.month, &clase[i].birthday.day);
	check[i] = 1;
}