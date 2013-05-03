package com.laps.vox;

/*
 * This is the prototype of a Vocalizer made by LaPS/UFPA and NEDETA/UEPA
 * @author Willian Rocha and FÃ¡bio AraÃºjo
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.laps.Constants;
import com.laps.DB.imageDB;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class voxlaps extends Activity implements TextToSpeech.OnInitListener{

	private int sizeX = 3;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	
	String LOG_TAG = "Assets";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore old info
        if(savedInstanceState != null){
        	restoreProgress(savedInstanceState);
        }
        
        //Copy all pictures to sdcard
        try {
			copyAssets();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //TTS
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
		
        setContentView(R.layout.avoxlaps);
        createButtons(sizeX);
    }
    
    //Save the "resolution" of the buttons
    public void onSaveInstanceState(Bundle saveState){
    	super.onSaveInstanceState(saveState);
    	saveState.putInt("sizeX", sizeX);
    }
    
    private void copyAssets() throws IOException{
    	//get dir of assests
        AssetManager assetManager = getAssets();
        String[] files = null;
        
        //create directory
        try {
			createDir();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open(filename);
              //The file 'BDVox.slite' is the database and
              //store it in other directory, else store
              //in sdcard in /voxlaps
              if(!filename.equals("BDVox.sqlite")){
            	  out = new FileOutputStream(Environment
            			  .getExternalStorageDirectory() + "/voxlaps/"
            			  + filename);
              }else{
            	String toSplit = getFilesDir().toString();
          		String words[] = toSplit.split("/");
          		StringBuilder s = new StringBuilder();
          		for(int i = 0; i < words.length - 1; i++){
          			s.append(words[i]+"/");
          		}
            	  out = new FileOutputStream(s+"/databases/" + filename);
              }
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
            } catch(Exception e) {
                Log.e("tag1", e.getMessage());
            }       
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }

    //create dir to store images in SDCARD
    public void createDir() throws FileNotFoundException{

        File subdirectory = null;
        File basedirectory = Environment.getExternalStorageDirectory();
		subdirectory = new File( basedirectory, "/voxlaps/");
		
		if ( ! subdirectory.exists() ) {
		    Log.i( "mkdir", "attempt to create subdirectory: " +
		subdirectory );
		    subdirectory.mkdirs();
		} 
    }
    
    //When application finish, stop the tts service
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }  
    
    //restore the resolution of the buttons
    private void restoreProgress(Bundle savedInstanceState) {
    	sizeX = savedInstanceState.getInt("sizeX");
    }

    //create the buttons dynamically*
    public void createButtons(int numb){
    	int i = 0,j = 0;
    	int count = 0;
    	
    	//get all itens in the database
    	List<menu> imagens = getImages();
    	//List<pessoa> imagens = getPes();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(numb);//Numero de linhas
        //
        int layouts[] = {
        		((View) findViewById(R.id.line01)).getId(),
        		((View) findViewById(R.id.line02)).getId(),
        		((View) findViewById(R.id.line03)).getId(),
        		((View) findViewById(R.id.line04)).getId(),
        		((View) findViewById(R.id.line05)).getId(),
        		((View) findViewById(R.id.line06)).getId(),
        		((View) findViewById(R.id.line07)).getId(),
        		((View) findViewById(R.id.line08)).getId()
        		};
        LinearLayout Lines = null;
        /**/
        ImageButtonM figura = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < numb; j++){
        	for(i = 0; i < numb; i++){
        		figura = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura.setTag("mybutton "+count);
            	figura.setId(count); 
            	figura.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura.setText(imagens.get(count).getTexto());
            	figura.setOnClickListener(getOnClick(figura));
            	//figura.setOnClickListener(getDominios(imagens));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(numb);//Numero de colunas
            	Lines.addView(figura);
            	count++;
        	}
    	}
    }//*
    
    //Get the text and synthesis that
    View.OnClickListener getOnClick(final ImageButtonM figura)  {
    	final String text = figura.getText();
    	final TextView tx = (TextView)findViewById(R.id.head);
        return new View.OnClickListener() {
            public void onClick(View v) {
            	tx.setText(tx.getText() +" "+ text);
            	//Speech one only word
            	tts.speak(text,
            			TextToSpeech.QUEUE_ADD, null);
            }
        };
    }
    
    //metodo para mostrar os dominios
  View.OnClickListener getDominios(final List<menu> imagens){
	   final int cod = ((menu) imagens).getID();
	   return new View.OnClickListener() {
		@Override
		public void onClick(View v2) {
			
			switch(cod){
			
			case 1:
				getPes();
				break;	
			case 2:
				getVerb();
				break;
			case 3:
				getAC();
				break;
			case 4:	
				getPerg();
				break;
			case 5:
				getSent();
				break;
			case 6:
				getExp();
				break;
			case 7:
				getAdj();
				break;
				}
				
		}
	};
   }

    //define menu
    public boolean onCreateOptionsMenu(Menu menu) {
       //getMenuInflater().inflate(R.menu.avoxlaps, menu); modifiquei p/ ajeitar
        
       // MenuItem resol = menu.add(0,0,0,"Resolução");
        MenuItem novo = menu.add(0,1,0,"Adicionar Imagem");
      //  resol.setIcon(R.drawable.ic_launcher);
        novo.setIcon(android.R.drawable.ic_menu_camera);
      
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	//Define the resolution of the buttons
      /*  if( item.getItemId() == 0){
        	final CharSequence[] items = {"3x3", "4x4","5x5","6x6","7x7"
        			,"8x8"};
        	new AlertDialog.Builder(this)
        	.setIcon(android.R.drawable.ic_dialog_alert)
        	.setTitle("Escolha uma resolução")
        	.setItems(items, new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int item) {
        	    	sizeX = item + 3;
        	    	createButtons(sizeX);
        	        Toast.makeText(getApplicationContext(), items[item],
        	        		Toast.LENGTH_SHORT).show();
        	    }
        	}).show();
        }*/
        if( item.getItemId() == 1){
        	startActivity(new Intent(this, AddImage.class));
        }
     	return false;
    }

    public void onInit(int status) {       
		if (status == TextToSpeech.SUCCESS) {
			//Toast.makeText(VoxLaPS.this,
			//"Text-To-Speech engine is initialized", Toast.LENGTH_LONG).show();
		}
		else if (status == TextToSpeech.ERROR) {
			Toast.makeText(voxlaps.this,
			"Error occurred while initializing Text-To-Speech engine",
				Toast.LENGTH_LONG).show();
		}
    }
 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				tts = new TextToSpeech(this, this);
			}
			else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}
	//Clear the last word in textview
	public void buttonBack(View v){
		String toSplit = (String) ((TextView)findViewById(R.id.head)).getText();
		String words[] = toSplit.split(" ");
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < words.length - 1; i++){
			s.append(words[i]+" ");
		}
		((TextView)findViewById(R.id.head)).setText(s);
	}
	//clear all textview
	public void buttonClear(View v)  {
		((TextView)findViewById(R.id.head)).setText("");
    }
	//synthesis the text in textview
	public void buttonPlay(View v)  {
		tts.speak((String) ((TextView)findViewById(R.id.head)).getText(),
    			TextToSpeech.QUEUE_ADD, null);
    }
	//get all itens in database
	public List<menu> getImages(){
		imageDB dao = new imageDB(this);
		final List<menu> imagens = dao.getLista();
		dao.close();
		
		return imagens;
	}
	
	public List<pessoa> getPes(){//mostra as imagens da tabela pessoa
		imageDB p = new imageDB(this);
		final List<pessoa> img = p.getListaP();
		p.close();
		
		return img;
	}
	
	public List<pergunta> getPerg(){//mostra as imagens da tabela pergunta
		imageDB pergunta = new imageDB(this);
		final List<pergunta> img = pergunta.getListaPerg();
		pergunta.close();
		
		return img;
	}
	
	public List<expressao> getExp(){//mostra as imagens da tabela expressao
		imageDB exp = new imageDB(this);
		final List<expressao> img = exp.getListaExp();
		exp.close();
		
		return img;
	}
	
	public List<sentimento> getSent(){//mostra as imagens da tabela sentimento
		imageDB sent = new imageDB(this);
		final List<sentimento> img = sent.getListaSent();
		sent.close();
		
		return img;
	}
	
	public List<adjetivo> getAdj(){//mostra as imagens da tabela adjetivo
		imageDB adj = new imageDB(this);
		final List<adjetivo> img = adj.getListaAdj();
		adj.close();
		
		return img;
	}
	
	public List<acoes> getAC(){//mostra as imagens da tabela ações
		imageDB ac = new imageDB(this);
		final List<acoes> img = ac.getListaAC();
		ac.close();
		
		return img;
	}
	
	public List<verbo> getVerb(){//mostra as imagens da tabela verbo
		imageDB vb = new imageDB(this);
		final List<verbo> img = vb.getListaVerb();
		vb.close();
		
		return img;
	}
}