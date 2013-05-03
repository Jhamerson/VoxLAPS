package com.laps.vox;

import java.io.Serializable;

public class adjetivo implements Serializable{
	
	private int ID_adj;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codadj_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_adj() {
		return ID_adj;
	}
	public void setID_adj(int iD_adj) {
		ID_adj = iD_adj;
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
	public int getCodadj_menu() {
		return codadj_menu;
	}
	public void setCodadj_menu(int codadj_menu) {
		this.codadj_menu = codadj_menu;
	}
	

}
