package edu.cmu.hcii.novo.kadarbra;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;

public class StepPreviewWidget extends LinearLayout{

	
	
	public StepPreviewWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.preview, null);

        this.addView(page);
	}
	
	
	
	/**
	 * 
	 * @param procedure current procedure
	 * @param step current page within ViewPager
	 */
	public void setCurrentStep(Procedure procedure, int step){
		setStepText(procedure, step-1, (TextView)findViewById(R.id.leftText));
		setStepText(procedure, step+1, (TextView)findViewById(R.id.rightText));
	}
	
	/**
	 * 
	 * @param procedure current procedure
	 * @param pagerIndex the page within ViewPager that we want to short a preview of
	 * @param textView the TextView used to display the preview
	 */
	private void setStepText(Procedure procedure, int pagerIndex, TextView textView) {
		String preview = "";
		if (pagerIndex < ProcedureActivity.PREPARE_PAGES) {
			if (pagerIndex == 0){
				preview = procedure.getTitle();
			}else if (pagerIndex == 1){
				preview = "Please gather the following equipment...";
			}else if (pagerIndex == 2){
				preview = "Execution Notes";
			}
			
		} else if (pagerIndex - ProcedureActivity.PREPARE_PAGES < procedure.getStepPreviewSize()) {
			preview = procedure.getStepPreview(pagerIndex - ProcedureActivity.PREPARE_PAGES);
		}
			
		textView.setText(preview);
	}
	
}
