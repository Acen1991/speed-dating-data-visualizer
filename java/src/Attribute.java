import java.util.LinkedList;


public class Attribute {
	String nameAttribute;
	String valueAttribute;
	Attribute(String n, String v){
		nameAttribute = n;
		valueAttribute = v;
	}
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if(other.nameAttribute.equals(nameAttribute)&&other.valueAttribute.equals(valueAttribute)){
			return true;
		}
		return false;
	}

}
