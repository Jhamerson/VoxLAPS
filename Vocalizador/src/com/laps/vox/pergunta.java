package com.laps.vox;

import java.io.Serializable;

public class pergunta implements Serializable{
	
	private int ID_perg;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codperg_menu;
	private static final long serialVersionUID = 1L;
	
	public int getID_perg() {
		return ID_perg;
	}
	public void setID_perg(int iD_perg) {
		ID_perg = iD_perg;
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
	public int getCodperg_menu() {
		return codperg_menu;
	}
	public void setCodperg_menu(int codperg_menu) {
		this.codperg_menu = codperg_menu;
	}


}
