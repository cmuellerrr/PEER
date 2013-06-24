package edu.cmu.hcii.novo.kadarbra;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";	// used for logging purposes
	public final static String PROCEDURE = "edu.cmu.hcii.novo.kadarbra.PROCEDURE";
	
	ListView procedureListView; // android widget for lists
	List<Procedure> procedures;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		procedures = ProcedureFactory.getProcedures(this);
		
		initExampleList();
	}
	
	// Initializes the example list
	private void initExampleList() {
		procedureListView = (ListView) findViewById(R.id.listView1);
	
		final ArrayList<String> procedureList = new ArrayList<String>();
		
		for (int i = 0; i < procedures.size(); i++) { // dummy data that fills the list up
			procedureList.add(procedures.get(i).getNumber() + ": " + procedures.get(i).getTitle());
		}
		
		final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, procedureList);
		procedureListView.setAdapter(adapter); // adapter is used to populate the ListView
		
		
		// sets an listener for if the user clicks on one of the ListView items
		procedureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          int position, long id) {
		    	  
		    	  Log.i(TAG, "Selected " + position);
		    	  Intent intent = new Intent(parent.getContext(), ProcedureActivity.class);
		    	  intent.putExtra(PROCEDURE, procedures.get(position));
		    	  startActivity(intent); 
		      }

		    });		
	}	

    // The activity is about to become visible.
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");   
    }
	
    // The activity has become visible (it is now "resumed").     
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");

    }
    
    // The activity is paused
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.v(TAG, "onPause");
    }
    
    // The activity is no longer visible (it is now "stopped")
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }
    
    // The activity is about to be destroyed.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }
	
	
	
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
*/
	
}
