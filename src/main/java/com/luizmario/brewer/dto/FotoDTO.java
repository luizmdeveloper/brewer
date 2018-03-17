package com.luizmario.brewer.dto;

public class FotoDTO {
	
	private String nomeFoto;
	private String contentType;
	
	public FotoDTO(String nomeFoto, String contentType) {
		this.nomeFoto = nomeFoto;
		this.contentType = contentType;
	}

	public String getNomeFoto() {
		return nomeFoto;
	}

	public void setNomeFoto(String nomeFoto) {
		this.nomeFoto = nomeFoto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
