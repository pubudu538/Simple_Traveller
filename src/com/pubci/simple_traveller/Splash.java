package com.pubci.simple_traveller;

/* Simple Traveller
 * @author Pubudu Gunatilaka
 * @version 1.0
 *   */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread timer=new Thread(){
			public void run(){
				
				try{
					sleep(5000);        // splash activity time 4000
				}catch(InterruptedException e){
					
						e.printStackTrace();
				}finally{
					
					// new activity startup 
					Intent openMain =new Intent("com.pubci.simple_traveller.MAINACTIVITY");
					startActivity(openMain);
				}
			}
		};
		
		timer.start();       // thread starts
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();           // finishes after the activity is paused
	}

}
