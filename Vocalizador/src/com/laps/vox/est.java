package com.laps.vox;

import java.io.Serializable;

public class est implements Serializable{
	
	private int ID_est;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int code_prin;
	private static final long serialVersionUID = 1L;
	
	public int getID_est() {
		return ID_est;
	}
	public void setID_est(int iD_est) {
		ID_est = iD_est;
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
		return code_prin;
	}
	public void setID_prin(int iD_prin) {
		code_prin = iD_prin;
	}
	
	
}
