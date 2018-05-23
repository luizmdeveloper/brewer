package com.luizmario.brewer.venda;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.luizmario.brewer.model.Cerveja;
import com.luizmario.brewer.session.TabelaItemVenda;

public class TabelaItemVendaTest {
	
	private TabelaItemVenda itens;
	
	@Before
	public void setUp() {
		this.itens = new TabelaItemVenda();
	}

	@Test
	public void calcularValotTotalSemNenhumItemDaVenda() throws Exception {
		assertEquals(BigDecimal.ZERO, itens.getValorTotal());
	}
	
	@Test
	public void calcularValorTotalComUmItem() throws Exception {
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
				
		itens.adicionarItem(cerveja, 1);
		
		assertEquals(valor, itens.getValorTotal());
	}
	
	@Test
	public void calcularValorTotalVariosItens() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		BigDecimal valor = new BigDecimal("8.90");
		c1.setValor(valor);

		Cerveja c2 = new Cerveja();
		c2.setCodigo(2L);
		BigDecimal v2 = new BigDecimal("16.97");
		c2.setValor(v2);
		
		itens.adicionarItem(c1, 1);
		itens.adicionarItem(c2, 2);
		
		assertEquals(new BigDecimal("42.84"), itens.getValorTotal());
	}
	
	@Test
	public void deveManterAQuantidadeItemParaCervejaIguais() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		BigDecimal valor = new BigDecimal("8.90");
		c1.setValor(valor);

		itens.adicionarItem(c1, 1);
		itens.adicionarItem(c1, 1);
		
		assertEquals(1, itens.getTotaisItens());
		assertEquals(new BigDecimal("17.80"), itens.getValorTotal());
	}
}
