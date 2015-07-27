import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class AttributesSet {
	// A remplir a la main avec les attributs
	static String[] scaledA = {
		"match",
		"dec_o",
		"attr_o",
		"sinc_o",
		"intel_o",
		"fun_o",
		"amb_o",
		"shar_o",
		"like_o",
		"prob_o",
		"dec",
		"attr",
		"sinc",
		"intel",
		"fun",
		"amb",
		"shar",
		"like",
		"prob",
		"met",
		"age",
		"mn_sat",
		"tuition",
		"imprace",
		"imprelig",
		"income",
		"sports",
		"tvsports",
		"exercise",
		"dining",
		"museums",
		"art",
		"hiking",
		"gaming",
		"clubbing",
		"reading",
		"tv",
		"theater",
		"movies",
		"concerts",
		"music",
		"shopping",
		"yoga",
		"exphappy",
		"expnum",
		"attr1_1",
		"sinc1_1",
		"intel1_1",
		"fun1_1",
		"amb1_1",
		"shar1_1",
		"attr4_1",
		"sinc4_1",
		"intel4_1",
		"fun4_1",
		"amb4_1",
		"shar4_1",
		"attr2_1",
		"sinc2_1",
		"intel2_1",
		"fun2_1",
		"amb2_1",
		"shar2_1",
		"attr3_1",
		"sinc3_1",
		"fun3_1",
		"intel3_1",
		"amb3_1",
		"attr5_1",
		"sinc5_1",
		"intel5_1",
		"fun5_1",
		"amb5_1",
		"match_es",
		"satis_2"
	};
	static String[] discreteA = {
		"iid",
		"gender",
		"condtn",
		"wave",
		"round",
		"positin1",
		"field_cd",
		"undergra",
		"race",
		"from",
		"zipcode",
		"goal",
		"date",
		"go_out",
		"career_c",
		"length",
		"numdat_2",
		"you_call",
		"them_cal",
		"date_3",
		"numdat_3",
		"num_in_3"
	};
	
	HashMap<String,PriorityQueue<ScaledAttribute>> scaledAttributesTemp; // Temp 
	HashMap<String,LinkedList<ScaledAttribute>> scaledAttributes;// Useful for the scaled attributes for which we don't know in advance the values and can't use the memo table.
	HashMap<DiscreteAttribute,DiscreteAttribute> memoAttr; // Used during the creation of the attributes and for discrete values.
	HashMap<String, LinkedList<String>> initialValues; // Either the set of all the possible discrete values, either the two bounds for scaled values.
	HashMap<String, Boolean> scaledAttr; // True if the attribute is scaled.
	
	
	AttributesSet(){
		scaledAttributesTemp = new HashMap<String,PriorityQueue<ScaledAttribute>>();
		memoAttr = new HashMap<DiscreteAttribute,DiscreteAttribute>();
		initialValues = new HashMap<String,LinkedList<String>>();
		scaledAttr = new HashMap<String,Boolean>();
		scaledAttributes = new HashMap<String,LinkedList<ScaledAttribute>>();
		for(String s:scaledA){
			scaledAttr.put(s, true);
			scaledAttributesTemp.put(s, new PriorityQueue<ScaledAttribute>());
			LinkedList<String> l = new LinkedList<String>();
			initialValues.put(s, l);
			l.add(Float.toString(Float.MAX_VALUE));
			l.add("0");
		}
		for(String s:discreteA){
			scaledAttr.put(s, false);
			initialValues.put(s, new LinkedList<String>());
		}
		
	}

	// Returns the corresponding attribute ou create a new one and store it in the memo database 
	public Attribute createAttribute(String nameAttribute, String valueAttribute, Dater dater){
		if(scaledAttr.get(nameAttribute)){
			ScaledAttribute s = new ScaledAttribute(nameAttribute,valueAttribute);
			s.dater = dater;
			scaledAttributesTemp.get(nameAttribute).add(s);
			// Initial values
			float min = Math.min(Float.parseFloat(initialValues.get(nameAttribute).pollFirst()), Float.parseFloat(valueAttribute));
			initialValues.get(nameAttribute).add(Float.toString(min));
			float max = Math.max(Float.parseFloat(initialValues.get(nameAttribute).pollLast()), Float.parseFloat(valueAttribute));
			initialValues.get(nameAttribute).addLast(Float.toString(max));
			
			return s;
		}
		DiscreteAttribute a = new DiscreteAttribute(nameAttribute,valueAttribute);
		DiscreteAttribute b = memoAttr.get(a);
		if(b==null){
			a.daters.add(dater);
			memoAttr.put(a, a);
			initialValues.get(nameAttribute).add(valueAttribute);
			return a;
		}
		b.daters.add(dater);
		return b;
		
	}
	
	public void generateScaledAttributes(){
		int i = 0;
		for (String s:scaledAttributesTemp.keySet()){
			LinkedList<ScaledAttribute> l = new LinkedList<ScaledAttribute>();
			scaledAttributes.put(s, l);
			PriorityQueue<ScaledAttribute> q = scaledAttributesTemp.get(s);
			while(!q.isEmpty()){
				l.add(q.poll());
			}
		}
	}
	
	// Returns the set of daters which match the given values for the attribute nameAttribute.
	public LinkedList<Dater> getDaters(String nameAttribute, LinkedList<String> values){
		LinkedList<Dater> l = new LinkedList<Dater>();
		if(scaledAttr.get(nameAttribute)){
			float min = Float.parseFloat(values.peekFirst());
			float max = Float.parseFloat(values.peekLast());
			for(ScaledAttribute d : scaledAttributes.get(nameAttribute)){
				float value = Float.parseFloat(d.valueAttribute);
				// Non-use of the priorityQueue !!
				if(value>=min&&value<=max){
					l.add(d.dater);
				}
			}
		}
		else{
			for(String s:values){
				DiscreteAttribute a = new DiscreteAttribute(nameAttribute,s);
				l.addAll(memoAttr.get(a).daters);
			}
		}
		return l;
			
	}

	public LinkedList<Dater> getDaters(String nameAttribute, String value) {
		LinkedList<String> l = new LinkedList<String>();
		l.add(value);
		return getDaters(nameAttribute,l);
	}
}
