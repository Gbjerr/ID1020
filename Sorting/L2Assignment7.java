/**
 * Assignment 7 in Lab 2
 *
 * Purpose of program: To sort an array of integers using algorithm insertion sort
 * in descending order.
 *
 * Execution of program:
 *
 * ******* Assignment 7 *******
 * Enter the size of your array:
 * 5
 *
 * Now enter the elements to be inserted to the array:
 * 5
 * 8
 * 16
 * -1
 * 3
 *
 * This is your array unsorted: [5], [8], [16], [-1], [3]
 *
 * Sorting array in descending order.....
 *
 * [-8], [-5], [-16], [1], [-3]
 * [-16], [-8], [-5], [1], [-3]
 * [-16], [-8], [-5], [1], [-3]
 * [-16], [-8], [-5], [-3], [1]
 *
 * [16], [8], [5], [3], [-1]
 *
 */

import edu.princeton.cs.algs4.StdIn;

public class L2Assignment7 {

    public static void main(String[] args) {

        System.out.println("******* Assignment 7 *******\n"
                + "Enter the size of your array: ");
        int[] array = new int[StdIn.readInt()];

        System.out.println("\nNow enter the elements to be inserted to the array: ");

        for(int i = 0; i < array.length; i++) {
            array[i] = StdIn.readInt();
        }

        System.out.println("\nThis is your array unsorted: ");
        printArray(array);
        invertArr(array);

        System.out.println("\nSorting array in descending order.....");
        insertionSort(array);

        invertArr(array);

        System.out.println();
        printArray(array);

    }

    public static void invertArr(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            arr[i] *= -1;
        }
    }

    /**
     * Method which sorts an array of integers using algorithm Insertion sort.
     * @param arr - The array to be sorted.
     */
    public static void insertionSort(int[] arr) {

        int var;
        // iterates array through from second element of the array
        for (int i = 1; i < arr.length; i++) {

            // swap adjacent elements at position j and j-1 until element at position
            // j is smaller than element at position j-1
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
