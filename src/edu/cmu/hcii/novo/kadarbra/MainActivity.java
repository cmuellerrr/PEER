package edu.cmu.hcii.novo.kadarbra;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";	// used for logging purposes
	
	ListView procedureListView; // android widget for lists
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		initExampleList();
	}
	
	// Initializes the example list
	private void initExampleList(){
		procedureListView = (ListView) findViewById(R.id.listView1);
	
		final ArrayList<String> procedureList = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++){ // dummy data that fills the list up
			procedureList.add("Procedure #"+i);
		}
		
		final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, procedureList);
		procedureListView.setAdapter(adapter); // adapter is used to populate the ListView
		
		
		// sets an listener for if the user clicks on one of the ListView items
		procedureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          int position, long id) {
		    	  //if (position==0){
		    		  Intent intent = new Intent(parent.getContext(), ProcedureActivity.class);
		    		  startNewActivity(); 
		    	 // }
		      }

		    });		
	}
	
	
	// This function currently starts the ProcedureActivity activity
	private void startNewActivity(){
		  Intent intent = new Intent(this, ProcedureActivity.class);
		  startActivity(intent);
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
    protected void onPause(){
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
