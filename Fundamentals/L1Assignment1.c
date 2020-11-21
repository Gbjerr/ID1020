/***
Assignment 1 for lab 1

Program implements a recursive and an iterative version of a function which reads
characters from stdin until a newline character is read and then prints them in
stdout in reverse order.

Test run by following:

    gcc L1Assignment1.c
    ./a.out < L1Test.txt  (or just ./a.out for user input)

    ********Assignment 1********

    Test: testing iterative version with more than 50 letters in StdIn (enter 50 or more characters)

    limit for buffer was reached (iterative version)
    olleholleholleholleholleholleholleholleholleholleh


    Test: testing iterative version
    Iterative version:

    olleh

    Test: testing recursive version
    Recursive version:


    olleh

*/

#include <stdio.h>
void iterativeVersion();
void recursiveVersion();

int main() {

    printf("%s\n", "********Assignment 1********");

    printf("\n%s\n", "Test: testing iterative version with more than 50 letters in StdIn (enter 50 or more characters)");
    iterativeVersion();
    printf("\n\n");

    printf("%s\n", "Test: testing iterative version");
    printf("Iterative version:\n\n");
    iterativeVersion();

    printf("\n%s\n", "Test: testing recursive version");
    printf("Recursive version:\n\n");
    recursiveVersion();

    return 0;
}

// method reads from stdIn until newLine is found and, prints characters recursively
// in reverse order
void recursiveVersion() {
    char ch;

    if((ch = getchar()) != '\n') {
        recursiveVersion();
    }

    putchar(ch);
}

// method reads from stdIn until newLine is found and, prints characters iteratively
// in reverse order
void iterativeVersion() {
    char buffer[50];
    size_t n = sizeof(buffer) / sizeof(char);

    char prev = getchar();
    int count = 0;
    while(prev != '\n') {

         if(count < n){
            buffer[count++] = prev;
         }

         prev = getchar();
    }

    // when the number of characters read is more than size of our buffer, print this
    if(count >= n) printf("\nlimit for buffer was reached (iterative version)\n");

    // begin printing in reverse order starting from the most recently added element
    for(int i = count - 1; i >= 0 ; i--) {
        putchar(buffer[i]);
    }
    printf("\n");


}
