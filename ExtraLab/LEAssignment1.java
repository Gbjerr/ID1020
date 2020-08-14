/**
 *
 * Assignment 1 for extra lab
 *
 * Following program implements a generic double linked cirular list with a sentinel
 * node which references both the back, and the front of the list. Making
 * access to both back and front element easy, without iteration.
 *
 */
public class LEAssignment1<Item> {


		public static void main(String[] args) {

				DoubleLinkedSentinelList<Integer> list = new DoubleLinkedSentinelList<Integer>();
				System.out.println("Assignment 1");
				System.out.println("Now adding 6 elements to list");

				list.addFirst(2);
				list.addFirst(1);
				list.addLast(3);
				list.addLast(32);
				list.addLast(76);
				list.addLast(10);
				System.out.println("Our list now looks like following: " + list.toString());

				System.out.println("\nNow deleting one element from the front and one from back");
				list.removeLast();
				list.removeFirst();
				System.out.println("Our list now looks like following: " + list.toString());
		}

		// class representing each node in the list
		private static class Node<Item> {
				private Item data;                  //data stored in node
				private Node<Item> next;            //node positioned in front of current node
				private Node<Item> prev;        		//node positioned previous to current node

				// constructor for a node in the list, taking data as argument and asserts to Node.
				// Makes sure theres no node attached in front of after it.
				public Node(Item item) {
					this.next = this.prev = this.prev;
					data = item;
				}

				// method returning data as a String
				public String toString() {
					return data.toString();
				}
		}

		//class representing the double linked sentinal list
		private static class DoubleLinkedSentinelList<Item> {
				private Node<Item> sentinel; // sentinel node, basically acts as a dummy node

				//contructor for the double linked sentinal list, instantiates
				//a list which is Empty
				public DoubleLinkedSentinelList() {
					sentinel = new Node<Item>(null);
					sentinel.next = sentinel;
					sentinel.prev = sentinel;
				}

				// method reurning string representation of current list
				public String toString() {
					StringBuilder output = new StringBuilder();
					Node<Item> current = sentinel.next;

					while(current != sentinel){
						if(current.next == sentinel)
							output.append("[" + current.toString() + "]");
						else
							output.append("[" + current.toString() + "], ");
						current = current.next;
					}

					return output.toString();
				}

				// method returns boolean if current list is empty or not
				public boolean isEmpty() {
					return sentinel.next == sentinel;
				}

				// method which removes the first node  in the list and returns it
				public Node removeFirst() {
					if(isEmpty()) {
						return null;
					}

					Node<Item> p = sentinel.next;
					Node<Item> oldFirst = sentinel.next;

					p.prev.next = p.next;
					p.next.prev = p.prev;

					return oldFirst;

				}

				// method which removes the last node in the list and returns it
				public Node removeLast() {
					if(isEmpty()) {
						return null;
					}

					Node<Item> p = sentinel.prev;
					Node<Item> oldLast = sentinel.prev;

					p.prev.next = p.next;
					p.next.prev = p.prev;

					return oldLast;
				}

				// method which adds a node at the front of the list
				public void addFirst(Item item) {
					Node<Item> p = sentinel;
					Node<Item> x = new Node<Item>(item);

					x.next = p.next;
					x.prev = p;
					p.next.prev = x;
					p.next = x;
				}

				// method which adds a node to the back of the list
				public void addLast(Item item) {
					Node<Item> p = sentinel.prev;
					Node<Item> x = new Node<Item>(item);


					x.next = p.next;
					x.prev = p;
					p.next.prev = x;
					p.next = x;

				}

		}

}
