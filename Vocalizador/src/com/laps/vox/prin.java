package com.laps.vox;

import java.io.Serializable;

public class prin implements Serializable{
	
	private int ID_prin;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	
	private static final long serialVersionUID = 1L;
	
	public int getID() {
		return ID_prin;
	}
	public void setID(int iD_prin) {
		ID_prin = iD_prin;
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

}
