/**
 * Assignment 5
 *
 * Purpose of program: To represent a generalized queue. Elements can be added
 * to queue, where they always end up on index 1. The queue is created with help
 * of an array. One can also remove elements at a array space of ones own choice.
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class L1Assignment5<Item> {
    private Item[] array = null;
    private int size;

    public L1Assignment5() {
        size = 0;
    }

    public static void main(String[] args) {
        L1Assignment5<Integer> queue = new L1Assignment5();


        System.out.println("********Assignment 5********\n" +
            "Lets add an element!\n");

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextInt()) {
            queue.addNewElement(sc.nextInt());
              System.out.println("printing content in queue: " + queue.toString() +
                    "\nTo continue, enter more integers. To interrupt, enter a char");
        }

        Scanner sc1 = new Scanner(System.in);
        System.out.println("Now let's delete an element in the list by your own choice!\n");

        while(sc1.hasNextInt()) {
            if(queue.size == 0) {
                System.out.println("No more elements are to be found...");
                break;
            }
            queue.deleteAtPos(sc1.nextInt());
              System.out.println("printing content in queue: " + queue.toString() +
                    "\nTo continue, enter more integers. To interrupt, enter a char");
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < size + 1; i++) {
            if(i + 1 == size + 1) {
                sb.append("[" + array[i] + "]");
            }
            else{
                sb.append("[" + array[i] + "], ");
            }
        }
        return sb.toString();
    }

    // Functionality which increases the size of the array with the double amount.
    private void boostSize() {
        if(array == null) {
            array = (Item[]) new Object[10];
        }
        else {
            System.out.println("Increasing size of array......");
            Item[] temp = (Item[]) new Object[array.length * 2];
            int i = 0;
            while(i < array.length) {
                temp[i] = array[i];
                i++;
            }

        array = temp;
        }
    }

   // Functionality which deletes element at position of the user's choice
    private void deleteAtPos(int pos) {
        if(size < pos || 1 > pos) {
            System.out.println("Such position doesn't exist");
        }
        else {

            for(int i = pos + 1; i < array.length; i++) {
            array[i - 1] = array[i];
        }
        //System.out.println(array[1]);
        size--;
        }

    }

    // Functionality which adds new element, which always appears in
    // array space 1
    private void addNewElement(Item item) {
        if((array == null) ||
                (size == array.length - 2)) {

            boostSize();
        }

        int i = array.length - 1;
        while(i > 1) {
            array[i] = array[i - 1];
            i--;
        }

        array[1] =  item;
        if(array[2] == null) {
        }
        size++;
    }




}
