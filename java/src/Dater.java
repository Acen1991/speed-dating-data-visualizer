import java.util.HashMap;
import java.util.LinkedList;


public class Dater {
	public static int maxIid;
	LinkedList<Date> dates;
	HashMap<String,Attribute> attributes;
	
	Dater(){
		dates = new LinkedList<Date>();
		attributes = new HashMap<String,Attribute>();
	}

	public boolean equals(Dater d) {
		if (d.attributes.get("iid")==attributes.get("iid")) {
			return true;
		}
		return false;
	}


	public static LinkedList<Dater> commonDaters(LinkedList<Dater> d1, LinkedList<Dater> d2){
		LinkedList<Dater> d = new LinkedList<Dater>();
		for(Dater u:d1){
			for(Dater v:d2){
				if(u.equals(v)){
					d.add(u);
				}
			}
		}
		return d;
	}
	

}
