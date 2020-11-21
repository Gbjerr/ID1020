/**
 * Assignment 3 in Lab 2
 *
 * Purpose of program: To sort an array of integers using algorithm insertion sort
 * in ascending order via input from user, and printing the number of inversions
 * before sorting.
 *
 * Execution of program:
 *
 *    ******* Assignment 3 *******
 *    Enter the size of your array:
 *    5
 *
 *    Now enter the elements to be inserted to the array:
 *    7
 *    11
 *    9
 *    -4
 *    6
 *
 *    This is your array unsorted:
 *
 *    [7], [11], [9], [-4], [6]
 *    The following are your inversions before sorting:
 *    [0, 7], [3, -4]
 *    [0, 7], [4, 6]
 *    [1, 11], [2, 9]
 *    [1, 11], [3, -4]
 *    [1, 11], [4, 6]
 *    [2, 9], [3, -4]
 *    [2, 9], [4, 6]

 *    Number of inversions: 7
 *
 *    Sorting array.....
 *
 *    [7], [11], [9], [-4], [6]
 *    [7], [9], [11], [-4], [6]
 *    [-4], [7], [9], [11], [6]
 *    [-4], [6], [7], [9], [11]
 *    Number of swaps performed during sort process: 7
 *
 */

import edu.princeton.cs.algs4.StdIn;

public class L2Assignment3 {

    public static void main(String[] args) {

        System.out.println("******* Assignment 3 *******\n"
                + "Enter the size of your array: ");

        int n = StdIn.readInt();

        // interrupt if input size is lesser than 1
        if(n < 1) {
            System.out.println("ERROR: Size of array must be greater than 0");
            System.exit(0);
        }

        int[] array = new int[n];

        System.out.println("\nNow enter the elements to be inserted to the array: ");

        for(int i = 0; i < array.length; i++) {
            array[i] = StdIn.readInt();
        }

        System.out.println("\nThis is your array unsorted: ");
        printArray(array);

        System.out.println("\nThe following are your inversions before sorting: ");

        countInv(array, array.length);

        System.out.println("\nSorting array.....");
        insertionSort(array);

    }

    /**
     * Method which sorts an array of integers using algorithm Insertion sort.
     * @param arr - The array to be sorted.
     */
    public static void insertionSort(int[] arr) {

        int swaps = 0;
        int var;
        // loop from the start of array
        for (int i = 1; i < arr.length; i++) {

            // swap adjacent elements at position j and j-1 until element at position
            // j is greater than element at position j-1
            for (int j = i; j > 0 && (arr[j-1] > arr[j]); j--) {
                // swap adjacent elements
                var = arr[j-1];
                arr[j-1] = arr[j];
                arr[j] = var;
                swaps++;

            }
            printArray(arr);
        }

        System.out.printf("\nNumber of swaps during sort process: %d\n", swaps);
    }

    /**
     * Method that goes through an array and prints the inversions.
     * @param arr - The array to go through
     * @param n - Length of the array
     */
    public static void countInv(int[] arr, int n) {
        if(n <= 1) {
            return;
        }
        int count = 0;

        for(int i = 0; i < n; i++) {

            for(int j = i+1; j < n; j++) {

                if(arr[i] > arr[j]) {
                    System.out.printf("[%d, %d], [%d, %d]\n", i, arr[i], j, arr[j]);
                    count++;
                }
            }
        }

        System.out.printf("\nNumber of inversions: %d\n", count);
    }


    /**
     * Method which prints out the content of current array.
     * @param arr - The array to be printed.
     */
    public static void printArray(int[] arr) {

        System.out.println();
        for(int i = 0; i < arr.length; i++) {
            if(i == arr.length - 1) {
                System.out.printf("[%d]", arr[i]);
            }
            else {
                System.out.printf("[%d], ", arr[i]);
            }
        }
    }

}
