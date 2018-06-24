package com.luizmario.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarFotoTemporariamente(MultipartFile[] files);
	
	public byte[] buscarFotoTemporario(String nome);
	
	public byte[] buscarFoto(String nome);
	
	public void salvar(String nome);
	
	public byte[] buscarFotoThumbnail(String nome);
}
