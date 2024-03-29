package com.laps.vox;

/*
 * This is the prototype of a Vocalizer made by LaPS/UFPA and NEDETA/UEPA
 * @author Willian Rocha and Fábio Araújo
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
import android.app.ListActivity;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;


public class voxlaps extends Activity implements TextToSpeech.OnInitListener{

	private int sizeX = 3;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	
	String LOG_TAG = "Assets";
	protected int numb;
	
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
        createButtons();//sizeX
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
    	sizeX = savedInstanceState.getInt("sizeX");//entender
    }

    //create the buttons dynamically*
    public void createButtons(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	//get all itens in the database
    	List<menu> imagens = getImages();
    	        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
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
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura);
            	count++;
            	
            	if(figura.getId() < 10){
            		figura.setOnClickListener(getDominios(figura));
            	}
        	}
    	}
    }//*
    
          
    public void createButtonsPes(){
    	int linha=3;
    	int coluna=4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	//get all itens in the database
    	List<pessoa> imagens = getPes();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }
     	
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);
        	
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
          
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
            	
    	  }
        }
    }

       public void createButtonsPerg(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<pergunta> imagens = getPerg();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
       
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsExp(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<expressao> imagens = getExp();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
       
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsAcao(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<acao> imagens = getAC();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
      
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsAdj(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<adjetivo> imagens = getAdj();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
     
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsSent(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<sentimento> imagens = getSent();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
       
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsVerb(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<verbo> imagens = getVerb();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsAlim(){
    	int linha = 3;
    	int coluna = 4;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<alimento> imagens = getAl();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsLetra(){
    	int linha = 6;
    	int coluna = 5;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<letra> imagens = getLet();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
    
    public void createButtonsNum(){
    	int linha = 2;
    	int coluna = 5;
    	int i = 0,j = 0;
    	int count = 0;
    	
    	
    	//get all itens in the database
    	List<numero> imagens = getNum();
        	
    	//path to get the images
    	String ExternalPath = Environment.getExternalStorageDirectory()
    			.toString()+"/voxlaps/";
    	
    	//Set the weight of all layout
    	LinearLayout MasterLine = (LinearLayout) findViewById(R.id.lines);
        MasterLine.setWeightSum(linha);//Numero de linhas
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
        
        ImageButtonM figura2 = null;
        
        //Clear All view before add button;
        for(i = 0;i < layouts.length; i++){
        	Lines = (LinearLayout) findViewById(layouts[i]);
        	Lines.removeAllViews();
        }

        //set the attributes of the ImageButonM
        for(j = 0; j < linha; j++){
        	for(i = 0; i < coluna; i++){
        		figura2 = new ImageButtonM(this);//ImageButtonM extends
        		//Image button and add a String Text to attributes
            	figura2.setTag("mybutton "+count);
            	figura2.setId(count); 
            	figura2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); 
            	figura2.setLayoutParams(new TableLayout.LayoutParams(
            	        ViewGroup.LayoutParams.MATCH_PARENT,
            	            ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            	//If image was added by user, get the full path from database
        		if(imagens.get(count).getImagem()
        				.contains("saved")){
        			figura2.setImageURI(Uri.parse(imagens.get(count)
        					.getImagem()));
        		
        		}
        		//else get the name of image and use external Path
        		else{
        			figura2.setImageURI(Uri.parse(ExternalPath
        					+ imagens.get(count).getImagem()));
        		}
        		//get text that will by synthesis
        		figura2.setText(imagens.get(count).getTexto());
            	figura2.setOnClickListener(getOnClick(figura2));
            	Lines = (LinearLayout) findViewById(layouts[j]);
            	Lines.setLayoutParams(new LinearLayout.LayoutParams(
            			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
            	Lines.setWeightSum(coluna);//Numero de colunas
            	Lines.addView(figura2);
            	count++;
        	}
    	}
    }
  
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
  View.OnClickListener getDominios(final ImageButtonM figura){
	   final int cod = figura.getId();
	   return new View.OnClickListener() {
		@Override
		public void onClick(View v2) {
		
			switch(cod){
			
			case 0:
				createButtonsPes();
				break;	
			case 1:
				createButtonsPerg();
				break;
			case 2:
				createButtonsExp();
				break;
			case 3:	
				createButtonsVerb();		
				break;
			case 4:
				createButtonsAdj();				
				break;
			case 5:
				createButtonsAcao();					
				break;
			case 6:
				createButtonsSent();			
				break;
			case 7:
				createButtonsAlim();			
				break;
			case 8:
				createButtonsLetra();			
				break;
			case 9:
				createButtonsNum();			
				break;
				
				}
			}
	};
   }

    //define menu
    public boolean onCreateOptionsMenu(Menu menu) {
      
        MenuItem excluir = menu.add(0,0,0,"Excluir Bot�o");
        MenuItem novo = menu.add(0,1,0,"Adicionar Imagem");
        excluir.setIcon(R.drawable.cancel);
        novo.setIcon(android.R.drawable.ic_menu_camera);
      
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    
       if( item.getItemId() == 0){
    //add exclusao das imgs
        }
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
	
	public void buttonVoltar(View v)  {
		Button volta = (Button) findViewById (R.id.voltar);
		volta.setClickable(true);
		createButtons();
    }
	
	public void buttonProximo(View v)  {
		
		Button proximo = (Button) findViewById (R.id.proximo);
		proximo.setClickable(true);
		
		List<pessoa> img = getPes();
	
		if(img.equals(true)){
			Toast.makeText(voxlaps.this,"funcionou!!!", Toast.LENGTH_LONG).show();
		}
	
	//condi��o das telas
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
	
	public List<acao> getAC(){//mostra as imagens da tabela a��es
		imageDB ac = new imageDB(this);
		final List<acao> img = ac.getListaAC();
		ac.close();
		
		return img;
	}
	
	public List<verbo> getVerb(){//mostra as imagens da tabela verbo
		imageDB vb = new imageDB(this);
		final List<verbo> img = vb.getListaVerb();
		vb.close();
		
		return img;
	}
	
	public List<alimento> getAl(){//mostra as imagens da tabela verbo
		imageDB al = new imageDB(this);
		final List<alimento> img = al.getListaAL();
		al.close();
		
		return img;
	}
	
	public List<letra> getLet(){//mostra as imagens da tabela verbo
		imageDB let = new imageDB(this);
		final List<letra> img = let.getListaLET();
		let.close();
		
		return img;
	}
	
	public List<numero> getNum(){//mostra as imagens da tabela verbo
		imageDB n = new imageDB(this);
		final List<numero> img = n.getListaN();
		n.close();
		
		return img;
	}
	
	
}