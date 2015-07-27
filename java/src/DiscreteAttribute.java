import java.util.LinkedList;


public class DiscreteAttribute extends Attribute {
	LinkedList<Dater> daters;
	DiscreteAttribute(String n, String v) {
		super(n, v);
		daters = new LinkedList<Dater>();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nameAttribute == null) ? 0 : nameAttribute.hashCode());
		result = prime * result
				+ ((valueAttribute == null) ? 0 : valueAttribute.hashCode());
		return result;
	}

}
