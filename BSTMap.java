//BSTMap.java
//CS231
//Project9
//ThomasDeng

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K,V> implements MyMap<K,V> {

	private TreeNode<K,V> root;
	private Comparator<K> comparator;
	
	//Constructor for BSTMap
	public BSTMap( Comparator<K> comparator ) {
		this.comparator = comparator;
		this.root = null;
		}

	//return true if the map contains a key-value pair with the given key
	public boolean containsKey( K key ) {
		if (root == null) {
			return false;
			}
		else {
			return root.contains(key, comparator);
			}	
		}
	
	//return the value with a given key (if there is no such key then return null)
	public V get (K key) {
		if (root == null) {
			return null;
			}
		else {
			return root.get(key, comparator);
			}
		}	
			
	//puts in a key-value pair
	public void put( K key, V value ) {
		// Call the TreeNode's put method. It will need the comparator passed in to it.
		if (root == null) {
			root = new TreeNode<K,V> (key,value,null,null);
			}
		else {
			root.put(key, value, comparator);
			}	
		}

	//prints the tree in order
	public void printInOrder() {
		if (root!= null) {
			root.printInOrder();
			}
		}
		
	//prints the tree pre order (test use)
	public void printPreOrder() {
		if (root!= null) {
			root.printPreOrder("", "");
			}
		}	
	
	//return the depth of the map
	public int depth() {
		int depth = 0;
		if (root!=null) {
			depth = root.getDepth();
			}
		return depth;	
		}
	
	//return an arraylist of keys (pre order)
	public ArrayList<K> getKeys() {
		ArrayList<K> list = new ArrayList<K>();
		if (root!= null) {
			root.getKeys(list);
			}
		return list;	
		}
		
	//return an arraylist of values (pre order)
	public ArrayList<V> getValues() {
		ArrayList<V> list = new ArrayList<V>();
		if (root!= null) {
			root.getValues(list);
			}
		return list;	
		}		

	//return the size of the map	
	public int size() {
		int size = 0;
		if (root!=null) {
			size = root.getSize();
			}
		return size;	
		}
	
	//return a list of key-value pairs in the map (pre order)
	public ArrayList<KeyValuePair<K,V>> getPairs() {
		ArrayList<KeyValuePair<K,V>> list = new ArrayList<KeyValuePair<K,V>>();
		if (root!=null) {
			root.getPairs(list);
			}
		return list;	
		}
	
	//return a key-sorted list of key-value pairs in the map (in order)
	public ArrayList<KeyValuePair<K,V>> getPairsIO() {
		ArrayList<KeyValuePair<K,V>> list = new ArrayList<KeyValuePair<K,V>>();
		if (root!=null) {
			root.getPairsIO(list);
			}
		return list;	
		}						
	
	//test method
	public static void main( String[] args ) {
		System.out.println( "test code" );
		BSTMap<String,Integer> map = new BSTMap<String,Integer>(new AscendingStringComparator());
		map.put( "B", 2 );
		map.put( "A", 1);
		map.put( "D", 2 );
		map.put( "C", 2 );
		map.printInOrder();
		System.out.println();
		System.out.println( "Has A: " + map.containsKey( "A" ) );
		System.out.println( "Has G: " + map.containsKey( "G" ) );
		map.put( "D", 3 );
		System.out.println( "D now has value " + map.get( "D" ) );
		System.out.println( "The tree has " + map.size() + " elements" );
		System.out.println( "The tree has " + map.depth() + " levels" );
		ArrayList<KeyValuePair<String,Integer>> pairs  = map.getPairs();
		System.out.println( "In useful order: " );
		System.out.println( pairs );
		}        
	}

	//---------------------------------------

	class TreeNode<Key,Value> {
	    
	    private KeyValuePair<Key,Value> pair;
	    private TreeNode<Key,Value> left;
	    private TreeNode<Key,Value> right;

	    public TreeNode( Key this_key, Value this_val, TreeNode<Key,Value> l, TreeNode<Key,Value> r ) {
	        pair = new KeyValuePair<Key,Value>(this_key,this_val);
	        left = l;
	        right = r;
	    	}

		//return left
		public TreeNode<Key,Value> getLeft() {
			return left;
			}
		
		//return right	
		public TreeNode<Key,Value> getRight() {
			return right;
			}
		
		//reset left 
		public void setLeft(TreeNode<Key,Value> l) {
			this.left = l;
			}
		
		//reset right
		public void setRight(TreeNode<Key,Value> r) {
			this.right = r;
			}
		
		//return pair
		public KeyValuePair<Key,Value> getThing() {
			return pair;
			}				
		
		//prints the tree (in order)
		public void printInOrder() {
			if (left != null) {
				left.printInOrder();
				}
			System.out.println(pair.toString());	
			if (right != null) {
				right.printInOrder();
				}	
			}
			
		//prints how the tree look like (pre order)
		public void printPreOrder(String blank, String lr) {
			System.out.println(lr + blank + pair.toString());
			String blank2 = blank + "   ";			
			if (left != null) {				
				left.printPreOrder(blank2, "left");
				}
			if (right != null) {
				right.printPreOrder(blank2, "right");
				}			
			}	
		
		//return the depth of the tree
		public int getDepth() {
			int depth = 0;
			if (left==null && right!=null) {
				depth = right.getDepth()+1;
				}
			else if (left!=null && right == null) {
				depth = left.getDepth()+1;
				}	
			else if (left!=null && right!=null) {
				depth = Math.max(left.getDepth(), right.getDepth())+1;
				}
			return depth;	
			}
		
		//determine if the key is in the tree node
		public boolean contains (Key key, Comparator<Key> comp) {
			if (comp.compare(key, pair.getKey())==0) {
				return true;
				}
			else if (comp.compare(key, pair.getKey())>0) {
				if (right!=null) {
					return right.contains(key, comp);
					}
				else {
					return false;
					}	 
				}
			else {
				if (left!=null) {
					return left.contains(key, comp);
					}
				else {
					return false;
					}	
				}	
			}
		
		//return a list of keys	(pre order)
		public void getKeys(ArrayList<Key> list) {
			list.add(pair.getKey());
			if (left!=null) {
				left.getKeys(list);
				}
			if (right!=null) {
				right.getKeys(list);
				}	
			}
			
		//return a list of values	(pre order)
		public void getValues(ArrayList<Value> list) {
			list.add(pair.getValue());
			if (left!=null) {
				left.getValues(list);
				}
			if (right!=null) {
				right.getValues(list);
				}	
			}			
			
		//return the value with a given key
		public Value get(Key key, Comparator<Key> comp) {
			if (comp.compare(key, pair.getKey())==0) {
				return pair.getValue();
				}
			else if (comp.compare(key, pair.getKey())>0) {
				if (right!=null) {
					return right.get(key,comp);
					}
				else {
					return null;
					}	
				}
			else {
				if (left!=null) {
					return left.get(key, comp);
					}
				else {
					return null;
					}	
				}		
			}			
		
		//return how many nodes are in the tree.
		public int getSize() {
			int size = 1;
			if (left!=null) {
				size += left.getSize();
				}
			if (right!=null) {
				size += right.getSize();
				}
			return size;		 
			}
		
		//gets the pairs in the node (pre order)
		public void getPairs(ArrayList<KeyValuePair<Key,Value>> list) {
			list.add(this.getThing());
			if (left!=null) {	
				left.getPairs(list);
				}
			if (right!=null) {
				right.getPairs(list);
				}		
			}
			
		//gets the pairs in the node (in order)
		//return a key-sorted list
		public void getPairsIO(ArrayList<KeyValuePair<Key,Value>> list) {
			if (left!=null) {	
				left.getPairsIO(list);
				}
			list.add(this.getThing());	
			if (right!=null) {
				right.getPairsIO(list);
				}		
			}				
		
		//put a key-value-pair in the tree
		public void put (Key key, Value value, Comparator<Key> comp) {
			if (comp.compare(key, pair.getKey())==0) {
				pair.setValue(value);
				}
			else if (comp.compare(key, pair.getKey())>0) {
				if (right==null) {
					right = new TreeNode<Key, Value> (key, value, null, null);
					}
				else {	
					right.put(key, value, comp);
					}	
				}
			else {
				if (left==null) {
					left = new TreeNode<Key, Value> (key, value, null, null);
					}
				else {	
					left.put(key, value, comp);
					}	
				}		
			}
		
		} // end class TreeNode

	class AscendingStringComparator implements Comparator<String> {
	    public int compare( String i1, String i2 ) {
	        // returns negative number if i2 comes after i1 lexicographically
	        return i1.compareTo(i2);
	    	}
		}
	//---------------------------------------