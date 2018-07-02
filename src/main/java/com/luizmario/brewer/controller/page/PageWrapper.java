package com.luizmario.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	private String httpUrl;
	
	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest){
		this.page = page;
		this.httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString()
				.replaceAll("\\+", "%20")
				.replaceAll("excluido", "");				
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	public boolean isVazia(){
		return page.getContent().isEmpty();
	}
	
	public int getAtual(){
		return page.getNumber();
	}
	
	public boolean isPrimeiro(){
		return page.isFirst();
	}
	
	public boolean isUltima(){
		return page.isLast();
	}
	
	public long getTotal(){
		return page.getTotalPages();
	}
	
	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	public String urlOrdernar(String propriedade){
		UriComponentsBuilder uriBuilderOrdernar = UriComponentsBuilder.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriBuilderOrdernar.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public boolean ordenada(String propriedade){
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		
		if (order == null){
			return false;
		}
		
		return order != null ? true : false;
	}
	
	public boolean descedente(String propriedade){
		return inverterDirecao(propriedade).equals("desc") ? true : false;
	}
	
	private String inverterDirecao(String propriedade){
		
		String direcao = "asc";
		
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		
		if (order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";			
		}
		
		return direcao;
	}	
}
