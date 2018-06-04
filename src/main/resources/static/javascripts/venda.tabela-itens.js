Brewer.TabelaVenda = (function(){
	
	function TabelaVenda(autocomplete){
		this.autocomplete = autocomplete;
		this.containerTabelaItem = $('.js-container-tabela-item-venda');
		this.uuid = $('#uuid');		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter); 
	}
	
	TabelaVenda.prototype.iniciar = function(){
		this.autocomplete.emitter.on('item-selecionado', onItemSelecionado.bind(this));
		
		bindQuantidade.call(this);
		bindTabelaItem.call(this);
	}
	
	TabelaVenda.prototype.valorTotal = function (){
		return this.containerTabelaItem.data('valor');
	}
	
	function onItemSelecionado(event, item){
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo,
				uuid: this.uuid.val()
			}
		});
		
		resposta.done(onAtualizarTabelaItem.bind(this));
	}
	
	function onAtualizarTabelaItem(html){
		this.containerTabelaItem.html(html);
		
		bindQuantidade.call(this);		
		var tabelaItem = bindTabelaItem.call(this);
		this.emitter.trigger('valor-total-atualizado', tabelaItem.data('valor-total'));
	}
	
	function onQuantidadeAlterada(evento){
		var inputQuantidade = $(evento.target);
		var codigoCerveja = inputQuantidade.data('codigo-cerveja');		
		var quantidade = inputQuantidade.val();
		
		if (quantidade <= 0){
			inputQuantidade.val('1');
			quantidade = 1;
		}
		
		var resposta = $.ajax({
			url: inputQuantidade.data('url') + codigoCerveja,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				uuid: this.uuid.val()
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
			url: btnExcluirItem.data('url') + this.uuid.val() + '/' + codigoCerveja,
			method: 'DELETE'
		});
		
		resposta.done(onAtualizarTabelaItem.bind(this));
	}
	
	function bindQuantidade(){
		var quantidadeInput = $('.js-quantidade-tabela-item-cerveja'); 
		$('.js-quantidade-tabela-item-cerveja').on('change', onQuantidadeAlterada.bind(this));
		quantidadeInput.maskMoney({precision : 0, thousands : '.'});
	}
	
	function bindTabelaItem(){
		var tabelaItem = $('.js-tabela-item');
		tabelaItem.on('dblclick', onDoubleClickItem);
		$('.js-tabela-item-exclusao-btn').on('click', onExcluirItemClick.bind(this));
		
		return tabelaItem;
	}
	
	return TabelaVenda	
}());