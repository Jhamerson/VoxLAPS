package com.laps.vox;

import java.io.Serializable;

public class comer implements Serializable{
	
	private int ID_comer;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codc_quero;
	
	private static final long serialVersionUID = 1L;
	
	public int getID_comer() {
		return ID_comer;
	}
	public void setID_comer(int iD_comer) {
		ID_comer = iD_comer;
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
		return codc_quero;
	}
	public void setID_quero(int iD_quero) {
		codc_quero = iD_quero;
	}

}
