package com.laps.vox;

import java.io.Serializable;

public class acao implements Serializable{

	private int ID_ac;
	private String Nome;
	private String Texto;
	private String Imagem;
	private int Posicao;
	private int codac_menu;
	private static final long serialVersionUID = 1L;

	public int getID_ac() {
		return ID_ac;
	}

	public void setID_ac(int iD_ac) {
		ID_ac = iD_ac;
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

	public int getCodac_menu() {
		return codac_menu;
	}

	public void setCodac_menu(int codac_menu) {
		this.codac_menu = codac_menu;
	}
		
}
