package com.laps.vox;

import java.io.Serializable;

public class beber implements Serializable{
	
	private int ID_beber;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codb_quero;
	
	private static final long serialVersionUID = 1L;
	
	public int getID_beber() {
		return ID_beber;
	}
	public void setID_beber(int iD_beber) {
		ID_beber = iD_beber;
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
	public int getID_quero() {
		return codb_quero;
	}
	public void setID_quero(int iD_quero) {
		codb_quero = iD_quero;
	}
	
}
