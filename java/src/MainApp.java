import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Pay attention to order (is it the same for every date for each dater) ?
// Same for met
public class MainApp {

	View view;
	final public static String[] aggrAttributes = { "match", "dec_o", "attr_o",
			"sinc_o", "intel_o", "fun_o", "amb_o", "shar_o", "like_o",
			"prob_o", "dec", "attr", "sinc", "intel", "fun", "amb", "shar",
			"like", "prob", "met" };
	final public static String[] nonAggrAttributes = { "iid", "gender",
			"condtn", "wave", "round", "positin1", "age", "field_cd",
			"undergra", "mn_sat", "tuition", "race", "imprace", "imprelig",
			"from", "zipcode", "income", "goal", "date", "go_out", "career_c",
			"sports", "tvsports", "exercise", "dining", "museums", "art",
			"hiking", "gaming", "clubbing", "reading", "tv", "theater",
			"movies", "concerts", "music", "shopping", "yoga", "exphappy",
			"expnum", "attr1_1", "sinc1_1", "intel1_1", "fun1_1", "amb1_1",
			"shar1_1", "attr4_1", "sinc4_1", "intel4_1", "fun4_1", "amb4_1",
			"shar4_1", "attr2_1", "sinc2_1", "intel2_1", "fun2_1", "amb2_1",
			"shar2_1", "attr3_1", "sinc3_1", "fun3_1", "intel3_1", "amb3_1",
			"attr5_1", "sinc5_1", "intel5_1", "fun5_1", "amb5_1", "match_es",
			"satis_2", "length", "numdat_2", "you_call", "them_cal", "date_3",
			"numdat_3", "num_in_3" };
	final public static String[] datesAttr = { "position", "order", "match",
			"int_corr", "samerace", "dec_o", "attr_o", "sinc_o", "intel_o",
			"fun_o", "amb_o", "shar_o", "like_o", "prob_o", "dec", "attr",
			"sinc", "intel", "fun", "amb", "shar", "like", "prob", "met" };
	

	// Processing setup method
	public static void main(String[] args) throws IOException, JSONException {

		Set<String> possibleAttributes = new HashSet<String>();

		Table table = new Table("res/SpeedDating.csv", ",", null);

		hashTranslation.fillTabs();
		// Important information

		List<Integer> iids = Arrays.asList(ArrayUtils.toObject(table
				.getColumnAsInts(table.getColumnIndex("iid"))));

		int maxIid = Collections.max(iids);
		int ndaters = maxIid + 1;

		AttributesSet attributeSet = new AttributesSet();

		FiltersSet filtersSet = new FiltersSet();
		filtersSet.attributeSet = attributeSet;

		ProjectionUnit projectionUnit = new ProjectionUnit();
		projectionUnit.attributesSet = attributeSet;
		projectionUnit.filtersSet = filtersSet;

		CalculationUnit calculationUnit = new CalculationUnit();
		calculationUnit.attributesSet = attributeSet;
		calculationUnit.projectionUnit = projectionUnit;
		calculationUnit.dated = new Date[ndaters][ndaters];

		// We create a table of all the daters with the aggregated data (some of
		// the data is general information and some of the data has to be
		// aggregated).

		Dater[] daters = new Dater[ndaters]; // Tableau pr�cisant si un iid
												// donn� correspond � un dater
		int cur = 0;

		HashMap<String, Double> aggrTemp = new HashMap<String, Double>();

		for (String s : aggrAttributes) {
			aggrTemp.put(s, 0.0);
		}

		for (int i = 0; i < iids.size(); i++) {
			// If it is a dater unseen so far, then write the aggregated data
			// about the precedent dater, write all the general information
			// about the new dater and start to aggregate data about the new
			// dater
			if (iids.get(i) != cur) {
				if (cur != 0) {
					// Write the aggregated data of precedent writer
					for (String aggrAttributes : aggrAttributes) {
						Double valueAttribute = aggrTemp.get(aggrAttributes)
								/ Double.parseDouble(daters[cur].attributes
										.get("round").valueAttribute);

						Attribute attribute = attributeSet.createAttribute(
								aggrAttributes,
								Double.toString(valueAttribute), daters[cur]);

						daters[cur].attributes.put(aggrAttributes, attribute);
					}
				}

				cur = iids.get(i);

				daters[cur] = new Dater();

				for (String s : nonAggrAttributes) {
					String attr = table.getS(table.getColumnIndex(s), i).trim();
					if (!attr.equals("")) {
						Attribute a = attributeSet.createAttribute(s,
								attr.replaceAll(",", ""), daters[cur]);
						daters[cur].attributes.put(s, a);
					}
				}
				aggrTemp = new HashMap<String, Double>();
				for (String s : aggrAttributes) {
					aggrTemp.put(s, 0.0);
				}
			}

			for (String s : aggrAttributes) {
				aggrTemp.put(
						s,
						aggrTemp.get(s)
								+ table.getFloat(table.getColumnIndex(s), i));
			}
		}
		for (String s : aggrAttributes) {
			Attribute a = attributeSet
					.createAttribute(
							s,
							Double.toString(aggrTemp.get(s)
									/ Double.parseDouble(daters[cur].attributes
											.get("round").valueAttribute)),
							daters[cur]);
			daters[cur].attributes.put(s, a);
		}

		// We fill the information about dates
		for (int i = 0; i < iids.size(); i++) {
			int iid = iids.get(i);
			boolean tempBool = false;
			try {
				tempBool = daters[iid].attributes.get("gender").valueAttribute
						.equals("1");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (tempBool) {
				int piid = 0;
				try {
					piid = table.getInt(table.getColumnIndex("pid"), i);
				} catch (Exception e) {
					// e.printStackTrace();
				}

				Date d = new Date(daters[iid], daters[piid]);
				calculationUnit.dated[iid][piid] = d;
				for (String s : datesAttr) {
					Attribute a = new Attribute(s, table.getS(
							table.getColumnIndex(s), i));
					d.attrDate.put(s, a);
				}
			}
		}
		attributeSet.generateScaledAttributes();

		ArrayList<String> allAttributes = new ArrayList(
				Arrays.asList(aggrAttributes));
		allAttributes.addAll(Arrays.asList(nonAggrAttributes));
		
		for (String maleAtt : allAttributes) {
			for (String femaleAtt : allAttributes) {
				projectionUnit.nameAttributeF = femaleAtt;
				projectionUnit.nameAttributeM = maleAtt;

				String nameAttributeF = hashTranslation.attr2attr
						.get(projectionUnit.nameAttributeF);

				String nameAttributeM = hashTranslation.attr2attr
						.get(projectionUnit.nameAttributeM);

				if (StringUtils.isEmpty(nameAttributeM)
						|| StringUtils.isEmpty(nameAttributeF)) {
					continue;
				}

				// countYesFromMan = 0, countYesFromWoman = 0, countMatches = 0
				int[][][] tab = calculationUnit.getFigures();
				int sizeM = tab.length;
				int sizeF = tab[0].length;

				if (sizeM < 2 || sizeF < 2)
					continue; // Not interesting data

				String[] attributesF, attributesM;

				if (hashTranslation.value2value
						.get(projectionUnit.nameAttributeF) != null) {
					attributesF = new String[calculationUnit.attributesF.length];
					for (int i = 0; i < calculationUnit.attributesF.length; i++) {
						attributesF[i] = hashTranslation.value2value.get(
								projectionUnit.nameAttributeF).get(
								calculationUnit.attributesF[i].split("\\.")[0]);
					}
				} else {
					attributesF = calculationUnit.attributesF;
				}

				if (hashTranslation.value2value
						.get(projectionUnit.nameAttributeM) != null) {
					attributesM = new String[calculationUnit.attributesM.length];
					for (int i = 0; i < calculationUnit.attributesM.length; i++) {
						attributesM[i] = hashTranslation.value2value.get(
								projectionUnit.nameAttributeM).get(
								calculationUnit.attributesM[i].split("\\.")[0]);
					}
				} else {
					attributesM = calculationUnit.attributesM;
				}

				// Treatment for width
				double[][][] datingPerformances = new double[sizeM][sizeF][3];
				for (int i = 0; i < sizeM; i++) {
					for (int j = 0; j < sizeF; j++) {
						int tot = tab[i][j][0] + tab[i][j][2];

						if (tot != 0) {
							datingPerformances[i][j][0] = (double) tab[i][j][0]
									/ tot; // nbOfYesFromMen/(nbOfYesFromMen+nbYesForWomen)
							datingPerformances[i][j][1] = (double) tab[i][j][1]
									/ tot; // nbOfYesFromWomen/(nbOfYesFromMen+nbYesForWomen)
							datingPerformances[i][j][2] = (double) tab[i][j][2]
									/ tot; // nbOfMatches/(nbOfYesFromMen+nbYesForWomen)
						}
					}
				}
				
				possibleAttributes.add(maleAtt);
				possibleAttributes.add(femaleAtt);
				
				JSONObject datingStatisticsSuperObject = new JSONObject();
				
				JSONObject attributesMetaData = new JSONObject();

				attributesMetaData.put("m_attribute", nameAttributeM);
				attributesMetaData.put("f_attribute", nameAttributeF);
				
				attributesMetaData.put("m_axis_values", attributesM);
				attributesMetaData.put("f_axis_values", attributesF);
				
				datingStatisticsSuperObject.put("attributes_data", attributesMetaData);
				
				JSONArray datingStatisticsArray = new JSONArray();
				
				for (int i = 0; i < sizeM; i++) {
					for (int j = 0; j < sizeF; j++) {
						JSONObject oneGroupStat = new JSONObject();
						oneGroupStat.put("couple_id", Math.floor(Math.random()*10000));
						oneGroupStat.put("m_attribute_value", attributesM[i]);
						oneGroupStat.put("f_attribute_value", attributesF[j]);
						oneGroupStat.put("m_like_f", datingPerformances[i][j][0]);
						oneGroupStat.put("f_like_m", datingPerformances[i][j][1]);
						oneGroupStat.put("both_like", datingPerformances[i][j][2]);
						oneGroupStat.put("m_success_attribute", 0.5);
						oneGroupStat.put("f_success_attribute", 0.5);
						
						datingStatisticsArray.put(oneGroupStat);
					}
				}
				
				datingStatisticsSuperObject.put("values", datingStatisticsArray);
				
				System.out.println("data_"+maleAtt+"_"+femaleAtt+" :   " + datingStatisticsSuperObject);
				try {
					File file = new File("res/"+maleAtt+"_"+femaleAtt+".json");
		 
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(datingStatisticsSuperObject.toString());
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				/*

				for (int i = 0; i < sizeM; i++) {
					for (int j = 0; j < sizeF; j++) {
						if (datingPerformances[i][j][0] == 0d
								&& datingPerformances[i][j][1] == 0d
								&& datingPerformances[i][j][2] == 0d) {
							continue;
						}
						System.out.println(i + " - " + j);
						System.out.println("mean performance of men : "
								+ datingPerformances[i][j][0]);
						System.out.println("mean performance of women : "
								+ datingPerformances[i][j][1]);
						System.out.println("mean performance of whole group : "
								+ datingPerformances[i][j][2]);
					}
				}

				System.out.println("-----------");
				System.out.println();
				*/

			}
		}
		
		JSONArray attributeNamesArray = new JSONArray();
		System.out.println();
		
		for(String normalizedAttributeName : possibleAttributes){
			if(normalizedAttributeName.equalsIgnoreCase("round")||normalizedAttributeName.equalsIgnoreCase("wave"))
				continue;
			String humanReadableAttributeName = hashTranslation.attr2attr.get(normalizedAttributeName);
			JSONObject nameAttributeObject = new JSONObject();
			nameAttributeObject.put("normalized_attribute_name", normalizedAttributeName);
			nameAttributeObject.put("human_readable_attribute_name", humanReadableAttributeName);
			attributeNamesArray.put(nameAttributeObject);
		}
		
		System.out.println(attributeNamesArray);
		
		try {
 
			File file = new File("res/attributes.json");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(attributeNamesArray.toString());
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}