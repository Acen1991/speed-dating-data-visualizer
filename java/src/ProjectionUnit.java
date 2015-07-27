import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ProjectionUnit {
	// Name of the attribute of projection for males.
	String nameAttributeM = "field_cd";

	// Name of the attribute of projection for females.
	String nameAttributeF = "race";

	// List of values of the attribute of projection for males.
	LinkedList<String> valuesM;

	// List of values of the attribute of projection for females.
	LinkedList<String> valuesF;

	FiltersSet filtersSet;
	AttributesSet attributesSet;

	// Divides in n spans two given values
	LinkedList<String> divisionScale(int n, LinkedList<String> values) {
		float min = Float.parseFloat(values.peekFirst());
		float max = Float.parseFloat(values.peekLast());
		float notch = (max - min) / n;
		LinkedList<String> ret = new LinkedList<String>();
		ret.add(Float.toString(min));
		for (int i = 0; i < n - 1; i++) {
			min += notch;
			ret.add(Float.toString(min));
		}
		ret.add(Float.toString(max));
		return ret;
	}

	// Returns two maps, the first for males and the second for females:
	// Each links every value of valuesH/F of the attribute nameAttributeH/F
	// with
	// the daters already filtered by the filtersSet so that each dater is
	// mapped to
	// one of the values.
	HashMap<String, LinkedList<Dater>>[] getDaters() {
		// � enlever
		valuesM = attributesSet.initialValues.get(nameAttributeM);
		valuesF = attributesSet.initialValues.get(nameAttributeF);

		HashMap<String, LinkedList<Dater>>[] h = (HashMap<String, LinkedList<Dater>>[]) new HashMap[2];
		h[0] = new HashMap<String, LinkedList<Dater>>();
		h[1] = new HashMap<String, LinkedList<Dater>>();
		LinkedList<Dater>[] daters = filtersSet.getDaters();
		LinkedList<Dater> datersM = daters[0];
		LinkedList<Dater> datersF = daters[1];

		if (attributesSet.scaledAttr.get(nameAttributeM) != null
				&& !attributesSet.scaledAttr.get(nameAttributeM)) {
			for (String s : valuesM) {
				h[0].put(
						s,
						Dater.commonDaters(datersM,
								attributesSet.getDaters(nameAttributeM, s)));
			}
		} else {
			Iterator<String> l = valuesM.iterator();
			String s1 = l.next();
			String s2;
			for (; l.hasNext();) {
				s2 = l.next();
				LinkedList<String> li = new LinkedList<String>();
				li.add(s1);
				li.add(s2);
				h[0].put(
						s1 + " - " + s2,
						Dater.commonDaters(datersM,
								attributesSet.getDaters(nameAttributeM, li)));
				s1 = s2;
			}
		}

		if (!attributesSet.scaledAttr.get(nameAttributeF)) {
			for (String s : valuesF) {
				h[1].put(
						s,
						Dater.commonDaters(datersF,
								attributesSet.getDaters(nameAttributeF, s)));
			}
		} else {
			Iterator<String> l = valuesF.iterator();
			String s1 = l.next();
			String s2;
			for (; l.hasNext();) {
				s2 = l.next();
				LinkedList<String> li = new LinkedList<String>();
				li.add(s1);
				li.add(s2);
				h[1].put(
						s1 + " - " + s2,
						Dater.commonDaters(datersF,
								attributesSet.getDaters(nameAttributeF, li)));
				s1 = s2;
			}
		}

		return h;

	}
}
