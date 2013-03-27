package com.laps.DB;

import java.util.ArrayList;
import java.util.List;

import com.laps.vox.beber;
import com.laps.vox.comer;
import com.laps.vox.est;
import com.laps.vox.perg;
import com.laps.vox.perg;
import com.laps.vox.prin;
import com.laps.vox.quero;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.RadioGroup;

public class imageDB extends SQLiteOpenHelper {

	private static final String NAME_DB = "BDVox.sqlite";
	private static final int VERSAO = 1;
	
	private static final String PRIN = "prin";
	private static final String EST = "est";
	private static final String PERG = "perg";
	private static final String QUERO = "quero";
	private static final String COMER = "comer";
	private static final String BEBER = "beber";
	private static Context context;

	private static final String[] colprin = {"ID_prin", "Nome", "Texto", "Imagem", "Posicao"};
	private static final String[] colest = {"ID_est","Nome", "Texto", "Imagem", "Posicao", "code_prin"};
	private static final String[] colperg = {"ID_perg","Nome", "Texto", "Imagem", "Posicao", "codp_prin"};
	private static final String[] colquero = {"ID_quero", "Nome", "Texto", "Imagem", "Posicao", "codq_prin"};
	private static final String[] colcomer = {"ID_comer","Nome", "Texto", "Imagem", "Posicao", "codc_qp"};
	private static final String[] colbeber = {"ID_beber","Nome", "Texto", "Imagem", "Posicao", "codb_qp"};
	
	public imageDB(Context context) {
		super(context, NAME_DB, null, VERSAO);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//cria a estrutura do banco de dados
		try{
		
		String SQL = "CREATE TABLE IF NOT EXISTS prin (ID_prin INTEGER PRIMARY KEY, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER)";
		String SQL2 = "CREATE TABLE IF NOT EXISTS est (ID_est INTEGER PRIMARY KEY, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL, " +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, code_prin INTEGER, constraint fk_prin FOREIGN KEY (code_prin) references prin (ID_prin))";
		String SQL3 = "CREATE TABLE IF NOT EXISTS perg (ID_perg INTEGER PRIMARY KEY, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codp_prin INTEGER, constraint fk_prin FOREIGN KEY (codp_prin) references prin (ID_prin))";
		String SQL4 = "CREATE TABLE IF NOT EXISTS quero (ID_quero INTEGER PRIMARY KEY, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codq_prin INTEGER, constraint fk_prin FOREIGN KEY (codq_prin) references prin (ID_prin))";
		String SQL5 = "CREATE TABLE IF NOT EXISTS comer (ID_comer INTEGER, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codc_quero INTEGER, constraint pk_comer PRIMARY KEY (ID_comer, codc_quero), constraint fk_quero FOREIGN KEY (codc_quero) references quero (ID_quero))";
		String SQL6 = "CREATE TABLE IF NOT EXISTS beber (ID_beber INTEGER, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codb_quero INTEGER, constraint pk_beber PRIMARY KEY (ID_beber, codb_quero), constraint fk_quero FOREIGN KEY (codb_quero) references quero (ID_quero))";
		
		db.execSQL(SQL);
		db.execSQL(SQL2);
		db.execSQL(SQL3);
		db.execSQL(SQL4);
		db.execSQL(SQL5);
		db.execSQL(SQL6);
			
		
		/*String SQL8 = "SELECT * FROM prin JOIN est ON prin.ID_prin=est.code_prin";
		String SQL9 = "SELECT * FROM prin JOIN perg ON prin.ID_prin=perg.codp_prin";
		String SQL10 = "SELECT * FROM prin JOIN quero ON prin.ID_prin=quero.codq_prin";
		String SQL11 = "SELECT * FROM quero JOIN comer ON quero.ID_quero=comer.codc_quero";
		String SQL12 = "SELECT * FROM quero JOIN beber ON quero.ID_quero=beber.codb_quero";
		
	db.execSQL(SQL8);
	db.execSQL(SQL9);
	db.execSQL(SQL10);
	db.execSQL(SQL11);
	db.execSQL(SQL12);*/


	
	db.beginTransaction();
	
	}
		catch(SQLException e){
			Log.e("Erro ao criar as tabelas e testar os dados", e.toString());
		}
		finally{
			 db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// atualiza a estrutura do bd
	//falta fazer
	
		try{			
			if (newVersion > oldVersion){
			db.beginTransaction();
		
			db.setTransactionSuccessful();
			}
			
		}
		catch(SQLException e){
			Log.e("Erro ao atualizar as tabelas e testar os dados", e.toString());
			throw e;
		}
		finally{
			 db.endTransaction();
		}
	}
	
	public List<prin> getLista(){//retorna a lista com o conteudo do bd
		List<prin> imagens = new ArrayList<prin>();
		
		//String selecionadif = "SELECT * FROM prin WHERE ID_prin != code_prin";
		//retorna o conteudo do bd
		Cursor valorbd = getWritableDatabase().query(PRIN, colprin, null, null, null, null, null);
		//manipula o bd
	

		while(valorbd.moveToNext()){//add os valores ao objeto
		prin image = new prin();
		image = toImage(valorbd);
		imagens.add(image);//add a lista de imagens
	}
		valorbd.close();
		return imagens;
	}//fecha lista
	
	
	public prin toImage(Cursor addvalor){//seta os valores para o objeto imagem
		prin valorp = new prin();
		valorp.setID(addvalor.getInt(0));
		valorp.setNome(addvalor.getString(1));
		valorp.setTexto(addvalor.getString(2));
		valorp.setImagem(addvalor.getString(3));
		valorp.setPosicao(addvalor.getInt(4));
		return valorp;	
	}
	
	public void getDominio (prin img){//verificar se é necessário
		if (img.getID()==1){
			getListaEst();
		}else if (img.getID()==2){
			getListaQuero();
		}else if (img.getID()==4){
			getListaPerg();
		}
	}
	
	public List<est> getListaEst(){//lista de imagens tabela Estou
		
		List<est> estou = new ArrayList<est>();
		
Cursor valorbd2 = getWritableDatabase().query(EST, colest, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		est valorest = new est();
		valorest.setID_est(valorbd2.getInt(0));
		valorest.setNome(valorbd2.getString(1));
		valorest.setTexto(valorbd2.getString(2));
		valorest.setImagem(valorbd2.getString(4));
		valorest.setPosicao(valorbd2.getInt(5));
		valorest.setID_prin(valorbd2.getInt(6));
		estou.add(valorest);
	}
	valorbd2.close();
	return estou;
	
	}
	
	public List<perg> getListaPerg(){//lista de imagens tabela Pergunta
		
		List<perg> pergunta = new ArrayList<perg>();
		
		Cursor valorbd2 = getWritableDatabase().query(PERG, colperg, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		perg valorperg = new perg();
		valorperg.setID_perg(valorbd2.getInt(0));
		valorperg.setNome(valorbd2.getString(1));
		valorperg.setTexto(valorbd2.getString(2));
		valorperg.setImagem(valorbd2.getString(4));
		valorperg.setPosicao(valorbd2.getInt(5));
		valorperg.setID_prin(valorbd2.getInt(6));
		pergunta.add(valorperg);
	}
	valorbd2.close();
	return pergunta;
	
	}
	
public List<quero> getListaQuero(){//lista de imagens tabela Quero
		
		List<quero> quero = new ArrayList<quero>();
		
		Cursor valorbd2 = getWritableDatabase().query(QUERO, colquero, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		quero valorquero = new quero();
		valorquero.setID_quero(valorbd2.getInt(0));
		valorquero.setNome(valorbd2.getString(1));
		valorquero.setTexto(valorbd2.getString(2));
		valorquero.setImagem(valorbd2.getString(4));
		valorquero.setPosicao(valorbd2.getInt(5));
		valorquero.setID_prin(valorbd2.getInt(6));
		quero.add(valorquero);
	}
	valorbd2.close();
	return quero;
	
	}

public List<comer> getListaComer(){//lista de imagens tabela Comer
	
	List<comer> comer = new ArrayList<comer>();
	
	Cursor valorbd2 = getWritableDatabase().query(COMER, colcomer, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	comer valorcome = new comer();
	valorcome.setID_comer(valorbd2.getInt(0));
	valorcome.setNome(valorbd2.getString(1));
	valorcome.setTexto(valorbd2.getString(2));
	valorcome.setImagem(valorbd2.getString(4));
	valorcome.setPosicao(valorbd2.getInt(5));
	valorcome.setID_quero(valorbd2.getInt(6));
	comer.add(valorcome);
}
valorbd2.close();
return comer;

}

public List<beber> getListaBeber(){//lista de imagens tabela Beber
	
	List<beber> beber = new ArrayList<beber>();
	
	Cursor valorbd2 = getWritableDatabase().query(BEBER, colbeber, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	beber valorbebe = new beber();
	valorbebe.setID_beber(valorbd2.getInt(0));
	valorbebe.setNome(valorbd2.getString(1));
	valorbebe.setTexto(valorbd2.getString(2));
	valorbebe.setImagem(valorbd2.getString(4));
	valorbebe.setPosicao(valorbd2.getInt(5));
	valorbebe.setID_quero(valorbd2.getInt(6));
	beber.add(valorbebe);
}
valorbd2.close();
return beber;

}
	
	public ContentValues toValues(prin image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		
		return Novo;	
	}
	

	public void inserir(prin nova){ //insere novas imagens ao bd
		ContentValues values = toValues(nova);
		getWritableDatabase().insert(PRIN,null,values);
	}
}
