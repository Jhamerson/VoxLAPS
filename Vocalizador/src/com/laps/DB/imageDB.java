package com.laps.DB;

import java.util.ArrayList;
import java.util.List;
import com.laps.vox.acao;
import com.laps.vox.adjetivo;
import com.laps.vox.expressao;
import com.laps.vox.menu;
import com.laps.vox.pergunta;
import com.laps.vox.pessoa;
import com.laps.vox.sentimento;
import com.laps.vox.verbo;
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
	
	private static final String MENU = "menu";
	private static final String PES = "pessoa";
	private static final String PERG = "pergunta";
	private static final String EXP = "expressao";
	private static final String ADJ = "adjetivo";
	private static final String SENT = "sentimento";
	private static final String AC = "acao";
	private static final String VERB = "verbo";
	private static Context context;

	private static final String[] colmenu = {"ID_menu", "Nome", "Texto", "Imagem", "Posicao"};
	private static final String[] colpes = {"ID_pes","Nome", "Texto", "Imagem", "Posicao", "codpes_menu"};
	private static final String[] colperg = {"ID_perg","Nome", "Texto", "Imagem", "Posicao", "codperg_menu"};
	private static final String[] colexp = {"ID_exp", "Nome", "Texto", "Imagem", "Posicao", "codexp_menu"};
	private static final String[] coladj = {"ID_adj","Nome", "Texto", "Imagem", "Posicao", "codadj_menu"};
	private static final String[] colsent = {"ID_sent","Nome", "Texto", "Imagem", "Posicao", "codsent_menu"};
	private static final String[] colac = {"ID_ac","Nome", "Texto", "Imagem", "Posicao", "codac_menu"};
	private static final String[] colverb = {"ID_verb","Nome", "Texto", "Imagem", "Posicao", "codv_menu"};
	
	public imageDB(Context context) {
		super(context, NAME_DB, null, VERSAO);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//cria a estrutura do banco de dados
		try{
		
		String SQL = "CREATE TABLE IF NOT EXISTS menu (ID_menu INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER)";
		String SQL2 = "CREATE TABLE IF NOT EXISTS pessoa (ID_pes INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL, " +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codpes_menu INTEGER, constraint fk_pm FOREIGN KEY (codpes_menu) references menu (ID_menu))";
		String SQL3 = "CREATE TABLE IF NOT EXISTS pergunta (ID_perg INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codperg_menu INTEGER, constraint fk_pem FOREIGN KEY (codperg_menu) references menu (ID_menu))";
		String SQL4 = "CREATE TABLE IF NOT EXISTS expressao (ID_exp INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codexp_menu INTEGER, constraint fk_exm FOREIGN KEY (codexp_menu) references menu (ID_menu))";
		String SQL5 = "CREATE TABLE IF NOT EXISTS adjetivo (ID_adj INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codadj_menu INTEGER, constraint fk_adjm FOREIGN KEY (codadj_menu) references menu (ID_menu))";
		String SQL6 = "CREATE TABLE IF NOT EXISTS sentimento (ID_sent INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codsent_menu INTEGER, constraint fk_sentm FOREIGN KEY (codsent_menu) references menu (ID_menu))";
		String SQL7 = "CREATE TABLE IF NOT EXISTS acao (ID_ac INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codac_menu INTEGER, constraint fk_acm FOREIGN KEY (codac_menu) references menu (ID_menu))";
		String SQL8 = "CREATE TABLE IF NOT EXISTS verbo (ID_verb INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT UNIQUE NOT NULL, Texto TEXT UNIQUE NOT NULL," +
"Imagem TEXT UNIQUE NOT NULL, Posicao INTEGER, codv_menu INTEGER, constraint fk_vm FOREIGN KEY (codv_menu) references menu (ID_menu))";
		
		db.execSQL(SQL);
		db.execSQL(SQL2);
		db.execSQL(SQL3);
		db.execSQL(SQL4);
		db.execSQL(SQL5);
		db.execSQL(SQL6);
		db.execSQL(SQL7);
		db.execSQL(SQL8);
	
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
	
	public List<menu> getLista(){//retorna a lista com o conteudo do bd
		List<menu> imagens = new ArrayList<menu>();
		
		//retorna o conteudo do bd
		Cursor valorbd = getWritableDatabase().query(MENU, colmenu, null, null, null, null, null);
		//manipula o bd
	

		while(valorbd.moveToNext()){//add os valores ao objeto
		menu image = new menu();
		image = toImage(valorbd);
		imagens.add(image);//add a lista de imagens
	}
		valorbd.close();
		return imagens;
	}//fecha lista
	
	
	public menu toImage(Cursor addvalor){//seta os valores para o objeto imagem
		menu valorp = new menu();
		valorp.setID(addvalor.getInt(0));
		valorp.setNome(addvalor.getString(1));
		valorp.setTexto(addvalor.getString(2));
		valorp.setImagem(addvalor.getString(3));
		valorp.setPosicao(addvalor.getInt(4));
		return valorp;	
	}
	
	
	public List<pessoa> getListaP(){//lista de imagens tabela pessoa
		
		List<pessoa> pes = new ArrayList<pessoa>();
		
Cursor valorbd2 = getWritableDatabase().query(PES, colpes, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		pessoa valorpes = new pessoa();
		valorpes.setID_pes(valorbd2.getInt(0));
		valorpes.setNome(valorbd2.getString(1));
		valorpes.setTexto(valorbd2.getString(2));
		valorpes.setImagem(valorbd2.getString(3));
		valorpes.setPosicao(valorbd2.getInt(4));
		valorpes.setCodpes_menu(valorbd2.getInt(5));
		pes.add(valorpes);
	}
	valorbd2.close();
	return pes;
	
	}
	
	public List<pergunta> getListaPerg(){//lista de imagens tabela pergunta
		
		List<pergunta> pergunta = new ArrayList<pergunta>();
		
		Cursor valorbd2 = getWritableDatabase().query(PERG, colperg, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		pergunta valorperg = new pergunta();
		valorperg.setID_perg(valorbd2.getInt(0));
		valorperg.setNome(valorbd2.getString(1));
		valorperg.setTexto(valorbd2.getString(2));
		valorperg.setImagem(valorbd2.getString(3));
		valorperg.setPosicao(valorbd2.getInt(4));
		valorperg.setCodperg_menu(valorbd2.getInt(5));
		pergunta.add(valorperg);
	}
	valorbd2.close();
	return pergunta;
	
	}
	
public List<expressao> getListaExp(){//lista de imagens tabela expressao
		
		List<expressao> exp = new ArrayList<expressao>();
		
		Cursor valorbd2 = getWritableDatabase().query(EXP, colexp, null, null, null, null, null);
		
	while (valorbd2.moveToNext()){
		expressao valorexpr = new expressao();
		valorexpr.setID_exp(valorbd2.getInt(0));
		valorexpr.setNome(valorbd2.getString(1));
		valorexpr.setTexto(valorbd2.getString(2));
		valorexpr.setImagem(valorbd2.getString(3));
		valorexpr.setPosicao(valorbd2.getInt(4));
		valorexpr.setCodexp_menu(valorbd2.getInt(5));
		exp.add(valorexpr);
	}
	valorbd2.close();
	return exp;
	
	}

public List<adjetivo> getListaAdj(){//lista de imagens tabela adjetivo
	
	List<adjetivo> adj = new ArrayList<adjetivo>();
	
	Cursor valorbd2 = getWritableDatabase().query(ADJ, coladj, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	adjetivo valoradj = new adjetivo();
	valoradj.setID_adj(valorbd2.getInt(0));
	valoradj.setNome(valorbd2.getString(1));
	valoradj.setTexto(valorbd2.getString(2));
	valoradj.setImagem(valorbd2.getString(3));
	valoradj.setPosicao(valorbd2.getInt(4));
	valoradj.setCodadj_menu(valorbd2.getInt(5));
	adj.add(valoradj);
}
valorbd2.close();
return adj;

}

public List<sentimento> getListaSent(){//lista de imagens tabela sentimento
	
	List<sentimento> sent = new ArrayList<sentimento>();
	
	Cursor valorbd2 = getWritableDatabase().query(SENT, colsent, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	sentimento valorsent = new sentimento();
	valorsent.setID_sent(valorbd2.getInt(0));
	valorsent.setNome(valorbd2.getString(1));
	valorsent.setTexto(valorbd2.getString(2));
	valorsent.setImagem(valorbd2.getString(3));
	valorsent.setPosicao(valorbd2.getInt(4));
	valorsent.setCodsent_menu(valorbd2.getInt(5));
	sent.add(valorsent);
}
valorbd2.close();
return sent;

}

public List<verbo> getListaVerb(){//lista de imagens tabela verbo
	
	List<verbo> verb = new ArrayList<verbo>();
	
	Cursor valorbd2 = getWritableDatabase().query(VERB, colverb, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	verbo valorverb = new verbo();
	valorverb.setID_verb(valorbd2.getInt(0));
	valorverb.setNome(valorbd2.getString(1));
	valorverb.setTexto(valorbd2.getString(2));
	valorverb.setImagem(valorbd2.getString(3));
	valorverb.setPosicao(valorbd2.getInt(4));
	valorverb.setCodv_menu(valorbd2.getInt(5));
	verb.add(valorverb);
}
valorbd2.close();
return verb;

}

public List<acao> getListaAC(){//lista de imagens tabela acao
	
	List<acao> acao = new ArrayList<acao>();
	
	Cursor valorbd2 = getWritableDatabase().query(AC, colac, null, null, null, null, null);
	
while (valorbd2.moveToNext()){
	acao valoracao = new acao();
	valoracao.setID_ac(valorbd2.getInt(0));
	valoracao.setNome(valorbd2.getString(1));
	valoracao.setTexto(valorbd2.getString(2));
	valoracao.setImagem(valorbd2.getString(3));
	valoracao.setPosicao(valorbd2.getInt(4));
	valoracao.setCodac_menu(valorbd2.getInt(5));
	acao.add(valoracao);
}
valorbd2.close();
return acao;

}
	
	public void InserirP(pessoa image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codpes_menu", image.getCodpes_menu());
		getWritableDatabase().insert(PES, null, Novo);	
	}
	
	public void DeletarP(pessoa pes){
	
		getWritableDatabase().delete(PES, "id=?", colpes);
	}
	
	public void InserirV(verbo image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codv_menu", image.getCodv_menu());
		getWritableDatabase().insert(VERB,null,Novo);
		
	}
	
	public void InserirA(acao image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codac_menu", image.getCodac_menu());
		getWritableDatabase().insert(AC,null,Novo);	
	}
	
	public void InserirPerg(pergunta image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codperg_menu", image.getCodperg_menu());
		getWritableDatabase().insert(PERG,null,Novo);	
	}
	public void InserirAdj(adjetivo image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codadj_menu", image.getCodadj_menu());
		getWritableDatabase().insert(ADJ,null,Novo);
	}
	public void InserirS(sentimento image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codsent_menu", image.getCodsent_menu());
		getWritableDatabase().insert(SENT,null,Novo);	
	}
	public void InserirE(expressao image){//add os valores novos
		 
		ContentValues Novo = new ContentValues();
		Novo.put("Nome", image.getNome());
		Novo.put("Texto", image.getTexto());
		Novo.put("Imagem", image.getImagem());
		Novo.put("Posicao", image.getPosicao());
		Novo.put("codexp_menu", image.getCodexp_menu());
		getWritableDatabase().insert(EXP,null,Novo);
	}
	
}
