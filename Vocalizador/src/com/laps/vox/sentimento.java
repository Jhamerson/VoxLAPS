package com.laps.vox;

import java.io.Serializable;

public class sentimento implements Serializable{
	
	private int ID_sent;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codsent_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_sent() {
		return ID_sent;
	}
	public void setID_sent(int iD_sent) {
		ID_sent = iD_sent;
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
	public int getCodsent_menu() {
		return codsent_menu;
	}
	public void setCodsent_menu(int codsent_menu) {
		this.codsent_menu = codsent_menu;
	}

}
