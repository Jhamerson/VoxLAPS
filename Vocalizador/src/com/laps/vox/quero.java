package com.laps.vox;

import java.io.Serializable;

public class quero implements Serializable{
	
	private int ID_quero;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codq_prin;
	
	private static final long serialVersionUID = 1L;
	
	public int getID_quero() {
		return ID_quero;
	}
	public void setID_quero(int iD_qp) {
		ID_quero = iD_qp;
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
		return codq_prin;
	}
	public void setID_prin(int iD_prin) {
		codq_prin = iD_prin;
	}
	
}
