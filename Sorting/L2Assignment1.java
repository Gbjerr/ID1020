/**
 * Assignment 1 in Lab 2
 *
 * Purpose of program: To sort an array of integers using algorithm Selection sort
 * in ascending order via input from user.
 *
 * Execution of program:
 *
 * ******* Assignment 1 *******
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
 * Sorting array.....
 * [-1], [8], [16], [5], [3]
 * [-1], [3], [16], [5], [8]
 * [-1], [3], [5], [16], [8]
 * [-1], [3], [5], [8], [16]
 *
 *
 */

import java.util.Scanner;
public class L2Assignment1 {

    public static void main(String[] args) {

       System.out.println("******* Assignment 1 *******\n"
            + "Enter the size of your array: ");
       Scanner sc = new Scanner(System.in);
       int[] array = new int[sc.nextInt()];

       System.out.println("\nNow enter the elements to be inserted to the array: ");

       for(int i = 0; i < array.length; i++) {
           array[i] = sc.nextInt();
       }

       System.out.println("\nThis is your array unsorted: ");
       printArray(array);

       System.out.println("\nSorting array.....");
       sort(array, array.length);


    }

    /**
     * Method which sorts an array of integers using algorithm Selection sort.
     * Selection sort uses a "min" reference to the minimum value which will be
     * compared continously with other elements in the array. When we find a value
     * less than the minimum, we point the reference to index of the new minimum.
     * Thereafter we replace the old minimum with the new newly found one.
     * @param arr - The array to be sorted.
     * @param n - Length of arr
     */
    private static void sort(int[] arr, int n){
        if(n <= 1) {
            System.out.println("Nothing to sort..");
            return;
        }

        for(int i = 0; i < n; i++) {
            int min = i;
            for(int j = i+1; j < n; j++) {

                if(arr[min] > arr[j]) {
                    min = j;
                }
            }
                if(i != min) {
                    int temp = arr[min];
                    arr[min] = arr[i];
                    arr[i] = temp;
                    printArray(arr);
                }
        }
    }

    /**
     * Method which prints out the content of current array.
     * @param arr - The array to be printed.
     */
    public static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < arr.length; i++) {
            if(i == arr.length - 1) {
                sb.append("[" + arr[i]+ "]");
            }
            else {
                sb.append("[" + arr[i]+ "]" +", ");
            }
        }
        System.out.println(sb.toString());
    }

}
