import java.util.LinkedList;


public class FiltersSet {
	LinkedList<Filter> filters;
	AttributesSet attributeSet;
	int currID;
	
	FiltersSet(){
		filters = new LinkedList<Filter>();
		currID = 0;
	}
	
	// returns the id of the filter created.
	public int createFilter(String gender,String nameAttribute,LinkedList<String> values){
		Filter filter = new Filter(currID, nameAttribute, gender, values);
        filters.add(filter);
        return currID++; //currID is increased to be different for each filter in the Set
	}
	
	// Deletes the filter with this id
	public void deleteFilter(int id){
		int i = 0;
        for (Filter filter : filters) {
            if(filter.id == id){
                filters.remove(i);
                return;
            }
            i++;
        }
	}
	
	// Returns the set of all the daters male satisfying the filters (first element of the value returned)
	// and the set of all the daters female satisfying the filters.
	public LinkedList<Dater>[] getDaters(){
		LinkedList<String> l = new LinkedList<String>();
		l.add("1");
		LinkedList<Dater> filteredMale = attributeSet.getDaters("gender", l);
        l = new LinkedList<String>();
        l.add("0");		
		LinkedList<Dater> filteredFem = attributeSet.getDaters("gender", l);
        
        for (Filter filter : filters) {
            if(filter.gender.equals("1")){
                LinkedList<Dater> currFiltM = attributeSet.getDaters(filter.nameAttribute, filter.values);
                filteredMale = Dater.commonDaters(currFiltM, filteredMale); //on prend les Dater qui match le nouveau filtre et tous les anciens
            }
            else{
                LinkedList<Dater> currFiltF = attributeSet.getDaters(filter.nameAttribute, filter.values);
                filteredFem = Dater.commonDaters(currFiltF, filteredFem);//on prend les Dater qui match le nouveau filtre et tous les anciens
            }
        }
        
        LinkedList<Dater>[] result = (LinkedList<Dater>[]) new LinkedList[2];
        result[0] = filteredMale;
        result[1] = filteredFem;
        return result;
	}
	
}
