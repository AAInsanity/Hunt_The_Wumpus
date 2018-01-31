//KeyValuePair.java
//CS231
//Project8
//ThomasDeng

public class KeyValuePair<Key, Value> {
	
	private Key key;
	private Value value;
	
	public KeyValuePair(Key k, Value v) {
		this.key = k;
		this.value = v; 
		}
	
	public Key getKey() {
		return key;
		}
	
	public Value getValue() {
		return value;
		}
	
	public void setValue( Value v ) {
		this.value = v;
		}
	
	public String toString() {
		String str = "key: " + key + ", value: " + value;
		return str;
		}			
	
	public static void main (String[] Args) {
		KeyValuePair<Integer, String> k1 = new KeyValuePair<Integer, String>(7, "a");
		System.out.println(k1.toString());
		System.out.println(k1.getKey());
		System.out.println(k1.getValue());
		k1.setValue("bazinga");
		System.out.println(k1.toString());
		}
	
	}