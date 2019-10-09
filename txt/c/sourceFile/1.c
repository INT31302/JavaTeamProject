#include <stdio.h>
#include <string.h>
#include<stdlib.h>

struct date
{
	int year;
	int month;
	int day;
};
struct student
{
	char name[10];
	int score[4];
	struct date birthday;
};
int search(struct student clase[],char fname[], char nname[],int size);
void del(struct student clase[],char fname[],int check[],char dname[],int size);
void output_info(struct student clase[],int size,int check[]);
void main()
{
	/*int mcnt=5;*/ int i,size;
	int check[10]={1,1,1,1,1};
	char fname[10]; //검색할 이름
	char nname[10]; //바꿀 이름
	char dname[10]; //삭제할 이름
	struct student *clase;
	printf("배열");
	scanf("%d", &size); 
	clase = (struct student *)malloc(sizeof(struct student)*size);
	for(i=0; i<size; i++)
	{
		scanf("%s %d %d %d %d %d %d %d",clase[i].name,&clase[i].score[0],&clase[i].score[1],&clase[i].score[2],&clase[i].score[3],
			&clase[i].birthday.year,&clase[i].birthday.month,&clase[i].birthday.day);
	}
	fflush(stdin);
	printf("검색할 이름");
	gets(fname);
	printf("\n");
	fflush(stdin);
	printf("바꿀 이름");
	gets(nname);
	printf("삭제할 이름");
	gets(dname);
	search(clase,fname,nname,size);
	del(clase,fname,check,dname,size);
	output_info(clase,size,check);
	
}
int search(struct student clase[],char fname[], char nname[],int size)
{
	int i;
	int count=0;
	for(i=0; i<size; i++)
	{
		if(strcmp(clase[i].name,fname)==0)
		{	
			strcpy(clase[i].name,nname);
			count++;
		}
	}
	if(count ==0)
	{
		printf("존재하지않습니다");
	}
	return 0;
}
void del(struct student clase[],char fname[],int check[],char dname[],int size)
{
	int i;
	for(i=0; i<size; i++)
	{
		if(strcmp(clase[i].name,dname)==0)
			check[i] =0;
	}
}
void output_info(struct student clase[],int size,int check[])
{
	int i,j;
	int sum=0;
	for(i=0; i<size; i++)
	{
		sum=0;
		for(j=0; j<4; j++)
		{
			sum += clase[i].score[j];
		}
		if(check[i]==1)
		printf("%s %d %d %d %d %d %d %d 합은 %d 평균은 %.0lf\n",clase[i].name,clase[i].score[0],clase[i].score[1],clase[i].score[2],clase[i].score[3],clase[i].birthday.year,
			clase[i].birthday.month,clase[i].birthday.day,sum,sum/4.0);
	}
}