import java.util.LinkedList;

public class Filter{
	
	// Id of the filter
	int id;
	// Name of the attribute filtered (ex : job)
	String nameAttribute;
	
	// The gender we are filtering
	String gender;
	
	// The values we want to keep for the attribute.
	LinkedList<String> values;
	
	
	Filter(int i, String n, String g, LinkedList<String> v){
		id = i;
		nameAttribute = n;
		gender = g;
		values = v;
	}
}
