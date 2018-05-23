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
		
		resposta.done(onAdicionadoItem.bind(this));
	}
	
	function onAdicionadoItem(html){
		this.containerTabelaItem.html(html);
	}
	
	return TabelaVenda
	
}());

$(function(){
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaVenda = new Brewer.TabelaVenda(autocomplete);
	tabelaVenda.iniciar();
});