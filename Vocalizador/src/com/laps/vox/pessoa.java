package com.laps.vox;

import java.io.Serializable;

public class pessoa implements Serializable{
	
	private int ID_pes;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codpes_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_pes() {
		return ID_pes;
	}
	public void setID_pes(int iD_pes) {
		ID_pes = iD_pes;
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
	public int getCodpes_menu() {
		return codpes_menu;
	}
	public void setCodpes_menu(int codpes_menu) {
		this.codpes_menu = codpes_menu;
	}

}
