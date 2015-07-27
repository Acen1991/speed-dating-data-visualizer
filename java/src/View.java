

public class View {
	/*
	//processing parent
	PApplet parent;
	
	String[] attributesM;
	String[] attributesF;
	String nameAttributeM;
	String nameAttributeF;
	int[][][] color;
	float[][] thickness;
	AttributesSet attributesSet;
	
	//Points
	 int windowHeight;
	 int windowWidth;
	 int xCornerLowerLeft;
	 int yCornerLowerLeft;
	 int xCornerLowerRight;
	 int yCornerLowerRight;
	 int xCornerTopLeft;
	 int yCornerTopLeft;
	 int xCornerTopRight;
	 int yCornerTopRight;
	 //Writing font
	 PFont font;
	 public View(PApplet p, int width, int height){
		 parent = p;
		 windowWidth = width;
		 windowHeight = height;
		 //creates all variables necessary for graphics (boundaries of the graph in the applet window)
		 xCornerLowerLeft = windowWidth/3;
		 yCornerLowerLeft = windowHeight-150;
		 xCornerTopLeft = windowWidth/3;
		 yCornerTopLeft = 100;
		 xCornerLowerRight = windowWidth-100;
		 yCornerLowerRight = windowHeight-150;
		 xCornerTopRight = windowWidth-100;
		 yCornerTopRight = 100;
		 //loading font
		 font = parent.loadFont("data/font.vlw");
		 
	 }
	// Draw method
		 
	public void draw(){
		
		//============================================================================
		 // Case of discrete attribute values
		 int matrixHeight = thickness.length;
		 int matrixWidth = thickness[0].length;
		 int numberOfAttributesM = attributesM.length;
		 int numberOfAttributesF = attributesF.length;
		 int yStepM = (yCornerLowerLeft-yCornerTopLeft)/(numberOfAttributesM-1);
		 int yStepF = (yCornerLowerRight-yCornerTopRight)/(numberOfAttributesF-1);
		 		 
		//============================================================================
		 //drawing the lines of the graph
		 for (int i=0; i<matrixHeight; i++) {
			 for (int j=0; j<matrixWidth; j++) {
				 parent.stroke(color[i][j][0], color[i][j][1], color[i][j][2]);
				 parent.fill(color[i][j][0], color[i][j][1], color[i][j][2]);
				 parent.quad(xCornerLowerLeft, yCornerLowerLeft-i*yStepM-thickness[i][j], 
						 xCornerLowerLeft, yCornerLowerLeft-i*yStepM+thickness[i][j], 
						 xCornerLowerRight, yCornerLowerRight -j*yStepF+thickness[i][j], 
						 xCornerLowerRight, yCornerLowerRight -j*yStepF-thickness[i][j]);
			 }
		 }		 
		 
		 //============================================================================
		 //drawing the graph's layout
		 parent.stroke(0);
		 parent.fill(0);		 
		 //axes' lines
		 parent.line(xCornerLowerLeft, yCornerLowerLeft+10, xCornerTopLeft, yCornerTopLeft-10);
		 parent.line(xCornerLowerRight, yCornerLowerRight+10, xCornerTopRight, yCornerTopRight-10);		 
		 //axes names
		 parent.textFont(font,20);
		 parent.text(nameAttributeM, xCornerLowerLeft-nameAttributeM.length()*5, yCornerLowerLeft+20, nameAttributeM.length()*12, 35);
		 parent.text(nameAttributeF, xCornerLowerRight-nameAttributeF.length()*5, yCornerLowerRight+20, nameAttributeF.length()*12, 35);
		 
		 //scaling on male axis
		 parent.textFont(font,10);	 
		 for (int i=0; i<numberOfAttributesM; i++) {
			 parent.line(xCornerLowerLeft-4, yCornerLowerLeft -i*yStepM, xCornerLowerLeft+1, yCornerLowerLeft -i*yStepM);
			 parent.text(attributesM[i], xCornerLowerLeft-attributesM[i].length()*5-6, yCornerLowerLeft -i*yStepM-5, attributesM[i].length()*5, 15);
		 }
		 //scaling on female axis		 		 
		 for (int i=0; i<numberOfAttributesF; i++) {
			 parent.line(xCornerLowerRight-1, yCornerLowerRight -i*yStepF, xCornerLowerRight+4, yCornerLowerRight -i*yStepF);
			 parent.text(attributesF[i], xCornerLowerRight+6, yCornerLowerRight -i*yStepF+2);
		 }	
		 
		 
		 
		//============================================================================
		 // Case of float attribute values
		 
	}
	
	// Returns the object (line or attribute) under the mouse
	// If the table returned contains two positive integers n and m then the object is the line between 
	// attributesM[n] and attributesF[m]. 
	// If the first integer returned is -1 and the second is n >= 0 then the object is the attributeF[n]
	// If the second integer returned is -1 and the first is n >=0 then the object is the attributeM[n]
	public int[] getObject(int mouseX,int mouseY){
		return null;
	}
	*/
}
