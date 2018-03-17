package com.luizmario.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.luizmario.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {
	
	private DeferredResult<FotoDTO> resultado;
	private MultipartFile[] files;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(DeferredResult<FotoDTO> resultado, MultipartFile[] files, FotoStorage fotoStorage) {
		this.resultado = resultado;
		this.files = files;
		this.fotoStorage = fotoStorage;
	}

	@Override
	public void run() {
		String novoNome = fotoStorage.salvarFotoTemporariamente(files);
		String contentType = files[0].getContentType();
		resultado.setResult(new FotoDTO(novoNome, contentType));
	}

}
