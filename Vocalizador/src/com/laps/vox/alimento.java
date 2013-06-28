package com.laps.vox;

import java.io.Serializable;

public class alimento implements Serializable {
	
	private int ID_alim;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codal_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_alim() {
		return ID_alim;
	}
	public void setID_alim(int iD_alim) {
		ID_alim = iD_alim;
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
	public int getCodal_menu() {
		return codal_menu;
	}
	public void setCodal_menu(int codal_menu) {
		this.codal_menu = codal_menu;
	}

}
