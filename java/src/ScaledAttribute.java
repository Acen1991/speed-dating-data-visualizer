
public class ScaledAttribute extends Attribute implements Comparable<ScaledAttribute>{
	Dater dater;
	ScaledAttribute(String n, String v) {
		super(n, v);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(ScaledAttribute arg0) {
		return (Float.parseFloat(this.valueAttribute)-Float.parseFloat(valueAttribute)>0)?1:-1;
	}
}
