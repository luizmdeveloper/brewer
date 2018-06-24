package com.luizmario.brewer.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	public static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path local){
		this.local = local;
		criarPastas();		
	}

	@Override
	public String salvarFotoTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0){			 
			MultipartFile arquivo = files[0];
			novoNome = gerarNovoNomeFoto(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toFile() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro ao salvar a foto temporariamente", e);				
			}
		}
		
		return novoNome;		
	}
	
	@Override
	public byte[] buscarFotoTemporario(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler local temporário da foto", e);
		}
	}
	
	@Override
	public byte[] buscarFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler local da foto", e);
		}
	}
	
	@Override
	public byte[] buscarFotoThumbnail(String nome) {
		return buscarFoto("thumbnail." + nome);
	}

	
	@Override
	public void salvar(String nome) {
		try {
			Files.move(this.localTemporario.resolve(nome), this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar a foto no local", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(nome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
	
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if (logger.isDebugEnabled()){
				logger.debug("Criando diretório das fotos");
				logger.debug("Pasta salvar foto " + this.local.toAbsolutePath());
				logger.debug("Pasta temporaria salvar foto " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar diretórios para salvar fotos", e);
		}
		
	}
	
	private String gerarNovoNomeFoto(String nomeOriginal){
		String novoNome = UUID.randomUUID() + "_" + nomeOriginal;
		
		if (logger.isDebugEnabled()){
			logger.debug(String.format("nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
	}

}
