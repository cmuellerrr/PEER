package edu.cmu.hcii.novo.kadarbra;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

public class StepPreviewWidget extends LinearLayout{
	private TextView leftText;
	private TextView rightText;	
	private static int LAYOUT_HEIGHT = 70;	// height of widget
	
	
	public StepPreviewWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	/**
	 * Initializes TextViews
	 * @param context
	 */
	private void init(Context context){
		leftText = new TextView(context);
		rightText = new TextView(context);
		
		leftText.setLayoutParams(new LayoutParams(0, LAYOUT_HEIGHT, 1));
		rightText.setLayoutParams(new LayoutParams(0, LAYOUT_HEIGHT, 1));
		
		leftText.setBackgroundColor(Color.DKGRAY);
		rightText.setBackgroundColor(Color.GRAY);	
		
		addView(leftText);
		addView(rightText);
	}
	
	/**
	 * 
	 * @param procedure current procedure
	 * @param step current page within ViewPager
	 */
	public void setCurrentStep(Procedure procedure, int step){
		setStepText(procedure, step-1, leftText);
		setStepText(procedure, step+1, rightText);
	}
	
	/**
	 * 
	 * @param procedure current procedure
	 * @param step the page within ViewPager that we want to short a preview of
	 * @param textView the TextView used to display the preview
	 */
	private void setStepText(Procedure procedure, int step, TextView textView){
		String string = "";
		if (step < ProcedureActivity.PREPARE_PAGES){
			if (step == 0){
				string = "Microbiology Sample Collection";
			}else if (step == 1){
				string = "Please gather the following equipment...";
			}else if (step == 2){
				string = "Execution Notes";
			}
			
		}else if (step-ProcedureActivity.PREPARE_PAGES < procedure.getStepsUnnested().size()){
			Step currentStep = procedure.getStepsUnnested().get(step-ProcedureActivity.PREPARE_PAGES);
			string = currentStep.getNumber() + ": " + currentStep.getText();
			
		}
			
		textView.setText(string);
	}
	
}
