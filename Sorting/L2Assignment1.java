/**
 * Assignment 1 in Lab 2
 *
 * Purpose of program: To sort an array of integers using algorithm insertion sort
 * in ascending order via input from user.
 *
 * Execution of program:
 *
 * ******* Assignment 1 *******
 * Enter the size of your array:
 * 5
 *
 * Now enter the integers to be placed to the array:
 * 5
 * 8
 * 16
 * -1
 * 3
 *
 * This is your array unsorted: [5], [8], [16], [-1], [3]
 *
 * Sorting array.....
 * [5], [8], [16], [-1], [3]
 * [5], [8], [16], [-1], [3]
 * [-1], [5], [8], [16], [3]
 * [-1], [3], [5], [8], [16]
 *
 *
 */

import edu.princeton.cs.algs4.StdIn;

public class L2Assignment1 {

    public static void main(String[] args) {

        System.out.println("******* Assignment 1 *******\n"
                + "Enter the size of your array: ");

        int n = StdIn.readInt();

        // interrupt if input size is lesser than 1
        if(n < 1) {
            System.out.println("ERROR: Size of array must be greater than 0");
            System.exit(0);
        }

        int[] array = new int[n];

        System.out.println("\nNow enter the integers to be placed to the array: ");

        for(int i = 0; i < array.length; i++) {
            array[i] = StdIn.readInt();
        }

        System.out.println("\nThis is your array unsorted: ");
        printArray(array);

        System.out.println("\nSorting array.....");
        insertionSort(array);



    }

    /**
     * Method which sorts an array of integers using algorithm Insertion sort.
     * @param arr - The array to be sorted.
     */
    public static void insertionSort(int[] arr) {

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

            }
            printArray(arr);
        }
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
