/**
 * Assignment 6
 *
 * Purpose of program: To implement an ordered queue of elements with integer values,
 * where all elements are in ascending order. For each added element, the queue's
 * interior will print itself.
 */
import java.util.NoSuchElementException;
import java.util.Scanner;


public class L1Assignment6 {
    int[] array;
    int size;

    public static void main(String[] args) {
        L1Assignment6 queue = new L1Assignment6();


        System.out.println("********Assignment 6********\n" +
            "Lets add an element!\n");

         Scanner sc = new Scanner(System.in);

        while(sc.hasNextInt()) {
            queue.enqueueInAscending(sc.nextInt());
            System.out.println("printing content in queue: ");
            queue.printQueue();
            System.out.println("\nTo continue, enter more integers. To interrupt, enter a char");
        }


        queue.printQueue();
    }

    // Constructor for queue, initializing array with 20 indexes
    // and fills those with values of -1.
    public L1Assignment6() {
        array = new int[20];
        size = 0;
    }

    // Method with prints out the values of the queue.
    public void printQueue() {
        StringBuilder sb = new StringBuilder();


        for(int i = 0; i < size; i++) {
            if(i == size - 1) {
                sb.append("[" + array[i] + "]");
            }
            else {
                sb.append("[" + array[i] + "], ");
            }
        }
        System.out.println(sb.toString());

    }

    // Metod creating another array with twice the size, then
    // copying elements from original array to new one and replaces
    // original array with larger-space-one.
    public void boostSize() {
        int[] temp = new int[array.length * 2];
        int i = 0;
        while(i < array.length) {
            temp[i] = array[i];
            i++;
        }

        array = temp;

    }

    public void enqueueInAscending(int input) {

        // As we do not use array space 0, in the array this becomes
        // the base case. Meaning, if all array spaces is taken, then
        // we double the size of the array.
        if(array.length == size / 2) {
            boostSize();
        }
        else if(size == 0) {
            array[0] = input;
        }
        else {

            if(input <= array[0]) {

              array[1] = array[0];
              array[0] = input;
              size++;
              shiftElemsRight(1);
              return;
            }

            // Loop through queue and find an appropriate spot for input element
            int j = 1;
            while(j < size) {
                if(input >= array[j-1] && input <= array[j]){

                    break;
                }
                j++;
            }

            // Move all values in the queue one spot to right
            shiftElemsRight(j);
            array[j] = input;
        }
        size++;
    }

    public void shiftElemsRight(int j) {

      int i = array.length - 1;

      while(i > j) {
          array[i] = array[i - 1];
          i--;
      }

    }

}
