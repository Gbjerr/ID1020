/**************************************************
 * Gustaf Bjering
 *
 * Assignment 5 in Lab 2 for course ID1020 at KTH.
 *
 * Purpose of program: To take an array of integers and order it
 * so that all negative elements are placed in front of the array.
 *
 * Execution of program:
 *
 * ******** Assignment 5 *******
 * Enter values to be placed in array
 *
 * (5 element/s more to enter)
 *
 * 5
 * (4 element/s more to enter)
 *
 * 1
 * (3 element/s more to enter)
 *
 * -1
 * (2 element/s more to enter)
 *
 * -6
 * (1 element/s more to enter)
 *
 * -4
 *
 * Moving negative values.....
 * Your array:
 * [-1] [-6] [-4] [1] [5]
 *
 ************************************************/

#include <stdio.h>

// declaration of function
void moveNegatives();

int main() {

    int array[5];
    int sizeOfArr = (int) sizeof(array) / sizeof(array[0]);
    printf("******* Assignment 5 *******\n");

    int elements = 5;
    int i = 0;
    printf("Enter values to be placed in array\n\n");
    while(i < sizeOfArr) {
        printf("(%d element/s more to enter)\n\n", elements--);
        scanf("%d", &array[i]);
        i++;
    }

    printf("\nMoving negative values.....\n");
    moveNegatives(array, sizeOfArr);
    printf("Your array: \n");
    for(i = 0; i < sizeOfArr; i++) {
        printf("[%d] ",  array[i]);
    }
}

// This function loops through the array and when finding a negative value
// we put it at the left as we can of the array, according to reference j.
void moveNegatives(int arr[], int n) {

    int i = 0, j = 0, temp;
    while(i < n) {

        //** Loop invariant **
        // Size of array remains constant through each iteration as its "in place"
        // where and it ensures that we swap places with elements so
        // that negative values are places as close to the front as possible,
        // giving it a memory complexity of O(1)
        if(arr[i] < 0 && (arr[i] != arr[j])) {
            temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
            j++;
        }
        i++;
    }

}
