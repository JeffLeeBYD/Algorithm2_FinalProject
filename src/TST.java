import java.io.*;
import java.util.*;

public class TST {
	private int n; // size
	private Node<String> root; // root of TST

	private static class Node<String> {
		private char c; // character
		private Node<String> left, mid, right; // left, middle, and right subtries
		private String val; // String associated with string
	}

	/**
	 * Does this symbol table contain the given key?
	 * 
	 * @param key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}

	/**
	 * Returns the String associated with the given key.
	 * 
	 * @param key the key
	 * @return the String associated with the given key if the key is in the symbol
	 *         table and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public String get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("calls get() with null argument");
		}
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		Node<String> x = get(root, key, 0);
		if (x == null)
			return null;
		return x.val;
	}

	// return subtrie corresponding to given key
	private Node<String> get(Node<String> x, String key, int d) {
		if (x == null)
			return null;
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		char c = key.charAt(d);
		if (c < x.c)
			return get(x.left, key, d);
		else if (c > x.c)
			return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid, key, d + 1);
		else
			return x;
	}

	/**
	 * Inserts the key-String pair into the symbol table, overwriting the old String
	 * with the new String if the key is already in the symbol table. If the String
	 * is {@code null}, this effectively deletes the key from the symbol table.
	 * 
	 * @param key the key
	 * @param val the String
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(String key, String val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with null key");
		}
		if (!contains(key))
			n++;
		else if (val == null)
			n--; // delete existing key
		root = put(root, key, val, 0);
	}

	private Node<String> put(Node<String> x, String key, String val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node<String>();
			x.c = c;
		}
		if (c < x.c)
			x.left = put(x.left, key, val, d);
		else if (c > x.c)
			x.right = put(x.right, key, val, d);
		else if (d < key.length() - 1)
			x.mid = put(x.mid, key, val, d + 1);
		else
			x.val = val;
		return x;
	}

	/**
	 * Returns all of the keys in the set that start with {@code prefix}.
	 * 
	 * @param prefix the prefix
	 * @return all of the keys in the set that start with {@code prefix}, as an
	 *         iterable
	 * @throws IllegalArgumentException if {@code prefix} is {@code null}
	 */
	public Iterable<String> keysWithPrefix(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
		}
		Queue<String> queue = new LinkedList<String>();
		Node<String> x = get(root, prefix, 0);
		if (x == null)
			return queue;
		if (x.val != null)
			queue.add(prefix);
		collect(x.mid, new StringBuilder(prefix), queue);
		return queue;
	}

	// all keys in subtrie rooted at x with given prefix
	private void collect(Node<String> x, StringBuilder prefix, Queue<String> queue) {
		if (x == null)
			return;
		collect(x.left, prefix, queue);
		if (x.val != null)
			queue.add(prefix.toString() + x.c);
		collect(x.mid, prefix.append(x.c), queue);
		prefix.deleteCharAt(prefix.length() - 1);
		collect(x.right, prefix, queue);
	}

	public TST() {
		File file = new File("stops.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: file not found");
		}

		while (sc != null && sc.hasNextLine()) {
			String wholeLine = sc.nextLine();
			if (wholeLine.endsWith(","))
				wholeLine += " ";
			String[] split = wholeLine.split(",");
			StringBuilder stop_name = new StringBuilder(split[2]);
			ArrayList<String> stop_name_list = new ArrayList<String>(Arrays.asList(stop_name.toString().trim().split(" ")));
			String prefix = stop_name_list.get(0);
			if (prefix.equals("FLAGSTOP") || prefix.equals("WB") || 						// dealing with prefix of strings
					prefix.equals("NB") || prefix.equals("SB") || prefix.equals("EB")) {
				stop_name_list.add(prefix);
				stop_name_list.remove(0);
			}																	// move specific prefix to end
			stop_name = new StringBuilder();
			for (String s: stop_name_list){
				stop_name.append(s).append(" ");								// update the new stop_name with prefix handled
			}
			// get the detailed information of the stops, and put it to TST for later call
			ArrayList<String> title = new ArrayList<String>(Arrays.asList("stop_id", "stop_code", "stop_name", "stop_desc",
					"stop_lat", "stop_lon", "zone_id", "stop_url", "location_type", "parent_station"));
			StringBuilder stopInfo = new StringBuilder();
			for (int i = 0; i < title.size(); i++) {
				stopInfo.append(title.get(i)).append(": ").append(split[i]).append("\n");
			}
			// put in key and value into TST
			this.put(stop_name.toString(), stopInfo.toString());
		}
	}
}
