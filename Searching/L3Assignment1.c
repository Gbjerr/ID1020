/***************
Assignment 1 for lab 3

Program reads chars from StdIn and prints the read char if they are alphabetic or
newline. Otherwise if not alphabetic or newline, print a blank (' '). Function
isalpha() recognizes whether a char is alphabetic or not.

run with:
./a.out < tale.txt > cleanedText.txt
 ***************/


#include <stdio.h>
#include <ctype.h>
int main() {

    char c = getchar();
    while(c != EOF)  {

        // check if current char is a newline, blanck or alphabetic in which case
        // we will redirect that char to stdOut, else just redirect a blank
        if(isalpha(c) || c == '\n' || c == ' ') {
            putchar(c);
        }
        else {
            putchar(' ');
        }

        c = getchar();
    }
    return 0;
}
