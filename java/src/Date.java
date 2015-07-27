import java.util.HashMap;

public class Date {
	Dater daterM;
	Dater daterF;
	HashMap<String,Attribute> attrDate;
	Date(Dater dM,Dater dF){
		daterM = dM;
		daterF = dF;
		attrDate = new HashMap<String,Attribute>();
	}
}
