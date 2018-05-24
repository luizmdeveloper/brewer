Brewer.TabelaVenda = (function(){
	
	function TabelaVenda(autocomplete){
		this.autocomplete = autocomplete;
		this.containerTabelaItem = $('.js-container-tabela-item-venda');
	}
	
	TabelaVenda.prototype.iniciar = function(){
		this.autocomplete.emitter.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(event, item){
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo
			}
		});
		
		resposta.done(onAtualizarTabelaItem.bind(this));
	}
	
	function onAtualizarTabelaItem(html){
		this.containerTabelaItem.html(html);
		$('.js-quantidade-tabela-item-cerveja').on('change', onQuantidadeAlterada.bind(this));
		$('.js-tabela-item').on('dblclick', onDoubleClickItem);
		$('.js-tabela-item-exclusao-btn').on('click', onExcluirItemClick.bind(this));
	}
	
	function onQuantidadeAlterada(evento){
		var inputQuantidade = $(evento.target);
		var codigoCerveja = inputQuantidade.data('codigo-cerveja');
		
		var resposta = $.ajax({
			url: inputQuantidade.data('url') + codigoCerveja,
			method: 'PUT',
			data: {
				quantidade: inputQuantidade.val()
			}
		});
		
		resposta.done(onAtualizarTabelaItem.bind(this));
	}
	
	function onDoubleClickItem(evento){
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExcluirItemClick(evento){
		var btnExcluirItem = $(evento.target);
		var codigoCerveja  = btnExcluirItem.data('codigo-cerveja');
		
		var resposta = $.ajax({
			url: btnExcluirItem.data('url') + codigoCerveja,
			method: 'DELETE'
		});
		
		resposta.done(onAtualizarTabelaItem.bind(this));
	}
	
	return TabelaVenda
	
}());

$(function(){
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaVenda = new Brewer.TabelaVenda(autocomplete);
	tabelaVenda.iniciar();
});