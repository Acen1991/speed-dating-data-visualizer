import java.util.HashMap;
import java.util.LinkedList;

public class CalculationUnit {
	// The entry dated[n][m] indicates if the dater male with pid n has dated
	// the dater female
	// with pid m.
	Date[][] dated;

	ProjectionUnit projectionUnit;

	AttributesSet attributesSet;

	public String[] attributesF;

	public String[] attributesM;

	// Calculates from the hashmap returned by the projection unit for each pair
	// of values,
	// the number of yes from male with no match, the number of yes from female
	// with no match and
	// the number of matches.
	public int[][][] getFigures() {
		
		HashMap<String, LinkedList<Dater>>[] hash = projectionUnit.getDaters();
		HashMap<String, LinkedList<Dater>> hashMen = hash[0];
		HashMap<String, LinkedList<Dater>> hashWomen = hash[1];

		int sizeM = projectionUnit.valuesM.size();
		int sizeF = projectionUnit.valuesF.size();
		int i = 0;
		
		if (attributesSet.scaledAttr.get(projectionUnit.nameAttributeF)) {
			attributesF = new String[sizeF - 1];
			String s2 = "";
			for (String s : projectionUnit.valuesF) {
				if (i != 0) {
					attributesF[i - 1] = s2 + " - " + s;
				}
				s2 = s;
				i++;
			}
		} else {
			attributesF = new String[sizeF];
			for (String s : projectionUnit.valuesF) {
				attributesF[i] = s;
				i++;
			}
		}
		
		i = 0;
		if (attributesSet.scaledAttr.get(projectionUnit.nameAttributeM)) {
			attributesM = new String[sizeM - 1];
			String s2 = "";
			for (String s : projectionUnit.valuesM) {
				if (i != 0) {
					attributesM[i - 1] = s2 + " - " + s;
				}
				s2 = s;
				i++;
			}
		} else {
			attributesM = new String[sizeM];
			for (String s : projectionUnit.valuesM) {
				attributesM[i] = s;
				i++;
			}
		}

		int[][][] tab = new int[attributesM.length][attributesF.length][3];
		i = 0;
		// double loop to treat each couple of values
		for (int vM = attributesM.length; i < vM; i++) {
			String valuesForMen = attributesM[i];

			for (int j = 0, vW = attributesF.length; j < vW; j++) {
				String valuesForWomen = attributesF[j];

				// list of Daters for the values valuesMen and valuesWomen
				LinkedList<Dater> listOfMen = hashMen.get(valuesForMen);
				LinkedList<Dater> listOfWomen = hashWomen.get(valuesForWomen);

				int countYesFromMan = 0, countYesFromWoman = 0, countMatches = 0;

				// double loop to treat each couple of daters who are related to
				// the current value
				for (Dater man : listOfMen) {
					for (Dater woman : listOfWomen) {

						// We calculate the sum of Yes toward the man or the
						// woman and the matches

						/*
						 * Other solution : using the transferred array of the
						 * dates
						 */
						int womanPid = Integer.parseInt(woman.attributes
								.get("iid").valueAttribute);
						int manPid = Integer
								.parseInt(man.attributes.get("iid").valueAttribute);
						Date date = dated[manPid][womanPid];
						if (date != null) {
							if (date.attrDate.get("match").valueAttribute
									.equals("1")) {
								countMatches++;
							} else {
								if (date.attrDate.get("dec").valueAttribute
										.equals("1")) {
									countYesFromMan++;
								}
								if (date.attrDate.get("dec_o").valueAttribute
										.equals("1")) {
									countYesFromWoman++;
								}
							}
						}

					}
				}

				tab[i][j][0] = countYesFromMan;
				tab[i][j][1] = countMatches;
				tab[i][j][2] = countYesFromWoman;

			}
		}

		return tab;
	}

	// Set eveything in the view in order to draw the strokes.
	public void setView() {
		/*
		int[][][] tab = getFigures();

		int sizeM = attributesM.length;
		int sizeF = attributesF.length;

		view.nameAttributeF = hashTranslation.attr2attr.get(pu.nameAttributeF);
		view.nameAttributeM = hashTranslation.attr2attr.get(pu.nameAttributeM);
		
		if (hashTranslation.value2value.get(pu.nameAttributeF) != null) {
			view.attributesF = new String[attributesF.length];
			for (int i = 0; i < attributesF.length; i++) {
				view.attributesF[i] = hashTranslation.value2value.get(pu.nameAttributeF).get(attributesF[i].split("\\.")[0]);
			}
		} else {
			view.attributesF = attributesF;
		}
		
		if (hashTranslation.value2value.get(pu.nameAttributeM) != null) {
			view.attributesM = new String[attributesM.length];
			for (int i = 0; i < attributesM.length; i++) {
				view.attributesM[i] = hashTranslation.value2value.get(pu.nameAttributeM).get(attributesM[i].split("\\.")[0]);
			}
		} else {
			view.attributesM = attributesM;
		}

		// Treatment for color
		int[][][] color = new int[sizeM][sizeF][3];
		int i;
		for (i = 0; i < sizeM; i++) {
			for (int j = 0; j < sizeF; j++) {
				int index = 0;
				int max = 0;
				for (int k = 0; k < 3; k++) {
					if (tab[i][j][k] > max) {
						index = k;
						max = tab[i][j][k];
					}
				}
				color[i][j][index] = 255;
			}
		}
		view.color = color;

		// Treatment for width
		float[][] total = new float[sizeM][sizeF];
		float max = 0;
		for (i = 0; i < sizeM; i++) {
			for (int j = 0; j < sizeF; j++) {
				total[i][j] = tab[i][j][0] + tab[i][j][1] + tab[i][j][2];
				if (total[i][j] > max) {
					max = total[i][j];
				}
			}
		}
		for (i = 0; i < sizeM; i++) {
			for (int j = 0; j < sizeF; j++) {
				total[i][j] /= max;
			}
		}
		view.thickness = total;
		*/
	}
}