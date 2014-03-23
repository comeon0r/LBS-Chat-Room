package com.baidu.mapapi.demo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);

		EditText username=(EditText)findViewById(R.id.editText1);
		EditText password=(EditText)findViewById(R.id.editText2);
		final Button login=(Button)findViewById(R.id.button1);
		final TextView register=(TextView)findViewById(R.id.link_to_register);

		//login listener
		android.view.View.OnClickListener loginListener=new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Ìø×ª
				gotoMapActivity();
			}
		};
		login.setOnClickListener(loginListener);

		android.view.View.OnClickListener registerListener=new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Ìø×ª
				gotoRegisterActivity();
			}
		};
		register.setOnClickListener(registerListener);
	}



	void gotoMapActivity(){
		try {
			Intent intent = null;
			intent = new Intent(login.this, tab_MainActivity.class);
			this.startActivity(intent);
		} catch ( ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	void gotoRegisterActivity(){
		try {
			Intent intent = null;
			intent = new Intent(login.this, register.class);
			this.startActivity(intent);
		} catch ( ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}
}
