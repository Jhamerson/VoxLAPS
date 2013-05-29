package com.laps.vox;

import android.content.Context;
import android.widget.ImageButton;

public class ImageButtonM extends ImageButton{
	private String Text = "";
	private int id;	
	private int posicao;
	
	public ImageButtonM(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	//Recive from database the string that will be sent to tts
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		this.Text = text;
	}
	//recebe id do BD
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosicao() {
		return posicao;
	}
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
