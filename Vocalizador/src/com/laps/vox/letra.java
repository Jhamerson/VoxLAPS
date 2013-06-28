package com.laps.vox;

import java.io.Serializable;

public class letra implements Serializable {
	
	private int ID_let;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codlet_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_let() {
		return ID_let;
	}
	public void setID_let(int iD_let) {
		ID_let = iD_let;
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
	public int getCodlet_menu() {
		return codlet_menu;
	}
	public void setCodlet_menu(int codlet_menu) {
		this.codlet_menu = codlet_menu;
	}

}
