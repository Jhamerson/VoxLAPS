package com.laps.vox;

import java.io.Serializable;

public class verbo implements Serializable{
	
	private int ID_verb;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codv_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_verb() {
		return ID_verb;
	}
	public void setID_verb(int iD_verb) {
		ID_verb = iD_verb;
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
	public int getCodv_menu() {
		return codv_menu;
	}
	public void setCodv_menu(int codv_menu) {
		this.codv_menu = codv_menu;
	}
	
}
