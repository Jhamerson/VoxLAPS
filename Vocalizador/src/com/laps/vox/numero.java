package com.laps.vox;

import java.io.Serializable;

public class numero implements Serializable {
	
	private int ID_num;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codnum_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_num() {
		return ID_num;
	}
	public void setID_num(int iD_num) {
		ID_num = iD_num;
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
	public int getCodnum_menu() {
		return codnum_menu;
	}
	public void setCodnum_menu(int codnum_menu) {
		this.codnum_menu = codnum_menu;
	}

}
