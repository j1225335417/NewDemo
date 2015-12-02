package com.example.gestureexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements OnGesturePerformedListener{
GestureLibrary mLibrary;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if(!(mLibrary.load())){
			finish();
		}
		GestureOverlayView myview = (GestureOverlayView)findViewById(R.id.gesture2);
		myview.addOnGesturePerformedListener(this);
	}
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gest) {
		// TODO Auto-generated method stub
		ArrayList<Prediction> predictions = mLibrary.recognize(gest);
		
		if(predictions.size() > 0 && predictions.get(0).score > 1.0){
			String result = predictions.get(0).name;
			
			if("open".equalsIgnoreCase(result))
			{	
			try {
				
				Intent i = new Intent("com.example.gestureexample.ABOUT");
				 startActivity(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}
			else if("save".equalsIgnoreCase(result))
				Toast.makeText(this, "Saving the document", Toast.LENGTH_LONG).show();
			else if("saves".equalsIgnoreCase(result))
				Toast.makeText(this, "Saving the document", Toast.LENGTH_LONG).show();
			
			else
				Toast.makeText(this, "No gesture found!!", Toast.LENGTH_LONG).show();
		}
		
	}

}
