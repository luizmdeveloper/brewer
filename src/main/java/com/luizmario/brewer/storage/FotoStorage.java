package com.luizmario.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarFotoTemporariamente(MultipartFile[] files);
	
	public byte[] buscarFoto(String nome);
}
