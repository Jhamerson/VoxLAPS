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
		
		pessoa novo = new pessoa();
		
		novo.setNome(Name);
		novo.setTexto(Text);
		novo.setImagem(URI);
		novo.setPosicao(Constants.POSICAO);
		Constants.POSICAO++;
		novo.setCodpes_menu(1);
		imageDB db = new imageDB(this);
		db.InserirP(novo);
		db.close();
		
		finish();
		/*int tela = ((RadioGroup) findViewById (com.laps.vox.R.id.opcoes))
				.getCheckedRadioButtonId();
		
		switch(tela){
		case com.laps.vox.R.id.pes:
			
			pessoa novo = new pessoa();
			
			novo.setNome(Name);
			novo.setTexto(Text);
			novo.setImagem(URI);
			novo.setPosicao(Constants.POSICAO);
			Constants.POSICAO++;
			imageDB db = new imageDB(this);
			db.inserir(novo);
			db.close();
			
			finish();
			
			break;
			
		case com.laps.vox.R.id.verb:
			
			verbo novo2 = new verbo();
			
			novo2.setNome(Name);
			novo2.setTexto(Text);
			novo2.setImagem(URI);
			novo2.setPosicao(Constants.POSICAO);
			Constants.POSICAO++;
			imageDB db2 = new imageDB(this);
			db2.inserir2(novo2);
			db2.close();
			
			finish();
			
			break;	
			
		case com.laps.vox.R.id.acao:
			
			acoes novo3 = new acoes();
			
			novo3.setNome(Name);
			novo3.setTexto(Text);
			novo3.setImagem(URI);
			novo3.setPosicao(Constants.POSICAO);
			Constants.POSICAO++;
			imageDB db3 = new imageDB(this);
			db3.inserir3(novo3);
			db3.close();
			
			finish();
			
			break;	
			
		case com.laps.vox.R.id.adj:
	
	adjetivo novo4 = new adjetivo();
	
	novo4.setNome(Name);
	novo4.setTexto(Text);
	novo4.setImagem(URI);
	novo4.setPosicao(Constants.POSICAO);
	Constants.POSICAO++;
	imageDB db4 = new imageDB(this);
	db4.inserir5(novo4);
	db4.close();
	
	finish();
	
	break;	
	
	case com.laps.vox.R.id.perg:
	
	pergunta novo5 = new pergunta();
	
	novo5.setNome(Name);
	novo5.setTexto(Text);
	novo5.setImagem(URI);
	novo5.setPosicao(Constants.POSICAO);
	Constants.POSICAO++;
	imageDB db5 = new imageDB(this);
	db5.inserir6(novo5);
	db5.close();
	
	finish();
	
	break;	
	
	case com.laps.vox.R.id.exp:
	
	expressao novo6 = new expressao();
	
	novo6.setNome(Name);
	novo6.setTexto(Text);
	novo6.setImagem(URI);
	novo6.setPosicao(Constants.POSICAO);
	Constants.POSICAO++;
	imageDB db6 = new imageDB(this);
	db6.inserir4(novo6);
	db6.close();
	
	finish();
	
	break;	
	
case com.laps.vox.R.id.sent:
		
		sentimento novo7 = new sentimento();
		
		novo7.setNome(Name);
		novo7.setTexto(Text);
		novo7.setImagem(URI);
		novo7.setPosicao(Constants.POSICAO);
		Constants.POSICAO++;
		imageDB db7 = new imageDB(this);
		db7.inserir7(novo7);
		db7.close();
		
		finish();
		
		break;	
		
	case com.laps.vox.R.id.po:
		
		pronomeO novo8 = new pronomeO();
		
		novo8.setNome(Name);
		novo8.setTexto(Text);
		novo8.setImagem(URI);
		novo8.setPosicao(Constants.POSICAO);
		Constants.POSICAO++;
		imageDB db8 = new imageDB(this);
		db8.inserir8(novo8);
		db8.close();
		
		finish();
		
		break;	
		
	case com.laps.vox.R.id.pr:
		
		pronomeR novo9 = new pronomeR();
		
		novo9.setNome(Name);
		novo9.setTexto(Text);
		novo9.setImagem(URI);
		novo9.setPosicao(Constants.POSICAO);
		Constants.POSICAO++;
		imageDB db9 = new imageDB(this);
		db9.inserir9(novo9);
		db9.close();
		
		finish();
		
		break;
		}*/
  
	}  
}
