/***
Assignment1:

Program implements a recursive and an iterative version of a function which reads
characters from stdin until a newline character is read and then prints them on
stdout in reverse order.
*/

#include <stdio.h>
void iterativeVersion();
void recursiveVersion();

int main() {

    printf("%s\n", "Assignment 1");

    printf("Recursive version:\n\n");
    recursiveVersion();
    printf("\n\n");



    printf("Iterative version:\n\n");
    iterativeVersion();


    return 0;
}

void recursiveVersion() {
    char ch;

    if((ch = getchar()) != '\n') {
        recursiveVersion();
    }

    putchar(ch);
}

void iterativeVersion() {
    char ch[50];

    int i = 0;
    ch[i] = getchar();
    for(i = 1; ch[i - 1] != '\n'; i++) {
         ch[i] = getchar();
    }

    for(i = i - 1; i >= 0; i--) {
        putchar(ch[i]);
    }
}
