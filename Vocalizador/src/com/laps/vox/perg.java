package com.laps.vox;

import java.io.Serializable;

public class perg implements Serializable{
	
	private int ID_perg;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codp_prin;
	
	private static final long serialVersionUID = 1L;
	
	public int getID_perg() {
		return ID_perg;
	}
	public void setID_perg(int iD_perg) {
		ID_perg = iD_perg;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getTexto() {
		return Texto;
	}
	public void setTexto(String texto) {
		Texto = texto;
	}
	public String getImagem() {
		return Imagem;
	}
	public void setImagem(String imagem) {
		Imagem = imagem;
	}
	public int getPosicao() {
		return Posicao;
	}
	public void setPosicao(int posicao) {
		Posicao = posicao;
	}
	public int getID_prin() {
		return codp_prin;
	}
	public void setID_prin(int iD_prin) {
		codp_prin = iD_prin;
	}
	
	
}
