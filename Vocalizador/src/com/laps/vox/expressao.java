package com.laps.vox;

import java.io.Serializable;

public class expressao implements Serializable{
	
	private int ID_exp;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codexp_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_exp() {
		return ID_exp;
	}
	public void setID_exp(int iD_exp) {
		ID_exp = iD_exp;
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
	public int getCodexp_menu() {
		return codexp_menu;
	}
	public void setCodexp_menu(int codexp_menu) {
		this.codexp_menu = codexp_menu;
	}

}
