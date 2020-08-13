/***************
Assignment 1 for lab 3

Program reads chars from StdIn and prints the read char if they are alphabetic or
newline. Otherwise if not alphabetic or newline, print a blank (' '). Function
isalpha() recognizes whether a char is alphabetic or not.
 ***************/
#include <stdio.h>
#include <ctype.h>
int main() {

    char c;

    while((c = getchar()) != EOF)  {

        if(isalpha(c) || c == '\n') {
            putchar(c);
        }
        else {
            putchar(' ');
        }
    }
    return 0;
}
