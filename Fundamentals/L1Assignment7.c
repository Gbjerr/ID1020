/**
* Assignment 7
*
* Purpose of program: To implement a filter which reads from stdin detects
* whether the types of parantheses {},[],() are balanced.
*/

#include <stdio.h>

int bracketLeft, bracketRight;
int curlyLeft, curlyRight;
int paranLeft, paranRight;

void confirmIfBalanced();
int main() {
    printf("%s\n", "Assignment 7");
    printf("%s\n", "Enter a line of text ");

    char ch;
    while((ch = getchar()) != '\n') {

        switch(ch) {
          case '(' :
              paranLeft++;
              break;

          case ')' :
              paranRight++;
              break;

          case '[' :
              bracketLeft++;
              break;

          case ']' :
              bracketRight++;
              break;

          case '{' :
              curlyLeft++;
              break;

          case '}' :
              curlyRight++;
              break;
        }

    }

    confirmIfBalanced();


    return 0;
}

void confirmIfBalanced() {

        if((paranLeft == paranRight) && paranLeft != 0) {
            printf("Parantheses are balanced\n");
        }
        else {
            printf("Parantheses are unbalanced\n");
        }

        if((bracketLeft == bracketRight) && bracketLeft != 0) {
            printf("Brackets are balanced\n");
        }
        else {
            printf("Brackets are unbalanced\n");
        }

        if((curlyLeft == curlyRight) && curlyLeft != 0) {
            printf("Curly brackets are balanced\n");
        }
        else {
            printf("Curly brackets are unbalanced\n");
        }
}
