package com.laps.vox;


import com.laps.Constants;
import com.laps.DB.imageDB;

import android.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
public class AddImage extends Activity {
    
	Bundle bundle;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.laps.vox.R.layout.addimage);        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.laps.vox.R.menu.addimage, menu);
        return true;
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	//get the path to file and set in ImageView
    	if (!(Constants.URI.equals(""))){
    		((ImageView) findViewById(com.laps.vox.R.id.imagePreview))
				.setImageURI(Uri.parse(Constants.URI));
    	}
    }
    
	public void buttonTPhoto(View v){
		//Call new Activity
		Intent i = new Intent(this, TPhoto.class);
		startActivityForResult(i, 0);
	}

	
	public void buttonSave(View v){
		//Store in database
	String Name = ((EditText) findViewById(com.laps.vox.R.id.Name))
				.getText().toString();
		String Text = ((EditText) findViewById(com.laps.vox.R.id.Text))
				.getText().toString();
		String URI = Constants.URI;
		
		/*
		RadioGroup tela = (RadioGroup) findViewById (com.laps.vox.R.id.opcoes);
        int op = tela.getCheckedRadioButtonId();
        
        if (op==R.id.prin){
        	
        }*/
        
        
        
		//add se antes
		prin novo = new prin();
		
		novo.setNome(Name);
		novo.setTexto(Text);
		novo.setImagem(URI);
		novo.setPosicao(Constants.POSICAO);
		Constants.POSICAO++;
		imageDB db = new imageDB(this);
		db.inserir(novo);
		db.close();
		
		finish();	
	}  
}
