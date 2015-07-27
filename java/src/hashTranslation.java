
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class hashTranslation {
	
	static HashMap<String, String> 					attr2attr = new HashMap<String, String>();
	static HashMap<String, HashMap<String, String>> value2value = new HashMap<String, HashMap<String, String>>();
	
	
	
	public static void fillTabs()
	{
		attr2attr.put("attr_o","Perceived attractiveness");
		attr2attr.put("sinc_o","Perceived sincerity");
		attr2attr.put("intel_o","Perceived intelligence");
		attr2attr.put("fun_o","Perceived sense of humour");
		attr2attr.put("amb_o","Perceived ambitiousness");
		attr2attr.put("shar_o","Perceived shared interest");
		attr2attr.put("like_o","Philantropy");
		attr2attr.put("prob_o","Confidence");
		
		attr2attr.put("condtn", "Condition of choice");
		value2value.put("condtn", toHashMap(new ArrayList(){{ 
			add("1"); add("limited choice"); 
			add("2"); add("extensive choice");
			}}));
		
		attr2attr.put("wave","filter");
		attr2attr.put("round","filter");
		attr2attr.put("age","Age");
		attr2attr.put("field_cd", "Field studied");
		value2value.put("field_cd", toHashMap(new ArrayList(){{ 
			add("1"); add("Law");  									// each couple of values is added to the ArrayList
			add("2"); add("Math"); 									// represents the key and the associated value in the HashMap
			add("3"); add("Social science, Psychologist"); 
			add("4"); add("Medical Science, Pharmaceuticaals and Bio Tech"); 
			add("5"); add("Engineering"); 
			add("6"); add("English/Creative Writing/Journalism");
			add("7"); add("History/Religion/Philiosphy"); 
			add("8"); add("Business/Economy/Finance"); 
			add("9"); add("Education, Academia"); 
			add("10"); add("Biological Sciences/Chemistry/Physics"); 
			add("11"); add("Social Work"); 
			add("12"); add("Undergrad/undecided"); 
			add("13"); add("Political Science/International Affairs"); 
			add("14"); add("Film"); 
			add("15"); add("Fine Arts/ Arts Administration"); 
			add("16"); add("Languages"); 
			add("17"); add("Architecture"); 
			add("18"); add("Other");
			}}));
		attr2attr.put("mn_sat","Scolar Intellingence");
		attr2attr.put("race", "Race");
		value2value.put("race", toHashMap(new ArrayList(){{ 
			add("1"); add("Black/African American");
			add("2"); add("European/Caucasian-American"); 
			add("3"); add("Latino/Hispanc American"); 
			add("4"); add("Asian/Pacific Islander/Asian-American"); 
			add("5"); add("Native American");
			add("6"); add("Other");
			}}));
		attr2attr.put("imprace","Race tolerance");
		attr2attr.put("imprelig","Religion tolerance");
		attr2attr.put("income","Income");
		value2value.put("goal", toHashMap(new ArrayList(){{ 
			add("1"); add("Seemed like a fun night out");
			add("2"); add("To meet new people"); 
			add("3"); add("To get a date"); 
			add("4"); add("Looking for a serious relationship"); 
			add("5"); add("To say I did it");
			add("6"); add("Other");
			}}));
		attr2attr.put("date", "Frequency dating");
		value2value.put("date", toHashMap(new ArrayList(){{ 
			add("1"); add("Several times a week");
			add("2"); add("Twice a week"); 
			add("3"); add("Once a week"); 
			add("4"); add("Twice a month"); 
			add("5"); add("Once a month");
			add("6"); add("Several times a year");
			add("7"); add("Almost never");
			}}));
		attr2attr.put("go out", "Frequency going out");
		value2value.put("go_out", toHashMap(new ArrayList(){{ 
			add("1"); add("Several times a week");
			add("2"); add("Twice a week"); 
			add("3"); add("Once a week"); 
			add("4"); add("Twice a month"); 
			add("5"); add("Once a month");
			add("6"); add("Several times a year");
			add("7"); add("Almost never");
			}}));
		attr2attr.put("sports","Playing sports/ athletics");
		attr2attr.put("tvsports","Watching sports");
		attr2attr.put("exercise","Body building/ excercising");
		attr2attr.put("dining","Dining out");
		attr2attr.put("museums","Museums/galleries");
		attr2attr.put("art","Art");
		attr2attr.put("hiking","Hiking/Camping");
		attr2attr.put("gaming","Gaming");
		attr2attr.put("clubbing","Dancing/Clubbing");
		attr2attr.put("reading","Reading");
		attr2attr.put("tv","Watching TV");
		attr2attr.put("theater","Theater");
		attr2attr.put("movies","Movies");
		attr2attr.put("concerts","Going to concerts");
		attr2attr.put("music","Music");
		attr2attr.put("shopping","Shooping");
		attr2attr.put("yoga","Yoga/meditation");
		attr2attr.put("exhappy","Enthusiasm");
		attr2attr.put("expnum","Confidence");
		attr2attr.put("attr1_1","Attractive");
		attr2attr.put("sinc1_1","Sincere");
		attr2attr.put("intel1_1","Intelligent");
		attr2attr.put("fun1_1","Fun");
		attr2attr.put("amb1_1","Ambitious");
		attr2attr.put("shar1_1","Shared Interests");
		attr2attr.put("attr3_1","Attractive");
		attr2attr.put("sinc3_1","Sincere");
		attr2attr.put("fun3_1","Fun");
		attr2attr.put("intel3_1","Intelligence");
		attr2attr.put("amb3_1","Ambitious");

	}
	
	
	//this method transform an ArrayList (with a very specific shape) into a HashMap Object
	public static HashMap<String,String> toHashMap(ArrayList<String> serializedHashMap)
	{
		HashMap<String,String> hash = new HashMap<String,String>();
		for(int i = 0, c = serializedHashMap.size(); i < c-1; i=i+2)
		{
			hash.put(serializedHashMap.get(i), serializedHashMap.get(i+1));
		}
		return hash;
	}
}
