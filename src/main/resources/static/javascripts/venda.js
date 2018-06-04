Brewer.Venda = (function(){
	
	function Venda(tabelaItemVenda){
		this.tabelaItemVenda = tabelaItemVenda;
		this.valorTotalItens = $('.js-valor-total-venda');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotal = this.tabelaItemVenda.valorTotal();
		this.valorFrete = this.valorFreteInput.data('valor');
		this.valorDesconto = this.valorDescontoInput.data('valor');
	}
	
	Venda.prototype.iniciar = function(){
		this.tabelaItemVenda.emitter.on('valor-total-atualizado', onValorTotalAlterado.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));

		this.tabelaItemVenda.emitter.on('valor-total-atualizado', onCalcularValorTotalVenda.bind(this));
		this.valorFreteInput.on('keyup', onCalcularValorTotalVenda.bind(this));
		this.valorDescontoInput.on('keyup', onCalcularValorTotalVenda.bind(this));
		
		onCalcularValorTotalVenda.call(this);
	}
	
	function onValorTotalAlterado(evento, valorTotal){
		var valor =  valorTotal == null ? 0 : valorTotal;
		this.valorTotal = valor;
	}
	
	function onValorFreteAlterado(evento){
		this.valorFrete = Brewer.removerMascara($(evento.target).val());
	}
	
	function onValorDescontoAlterado(evento){
		this.valorTotalItens.removeClass('has-error');
		this.valorDesconto = Brewer.removerMascara($(evento.target).val());		
	} 
	
	function onCalcularValorTotalVenda(){		
		var valor = numeral(this.valorTotal) + numeral(this.valorFrete) - numeral(this.valorDesconto);		
		this.valorTotalItens.html(Brewer.formatarMoeda(valor));
		
		this.valorTotalBoxContainer.toggleClass('negativo', valor < 0);
	}
	
	return Venda;
}());

$(function(){
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaVenda = new Brewer.TabelaVenda(autocomplete);
	tabelaVenda.iniciar();
	
	var venda = new Brewer.Venda(tabelaVenda);
	venda.iniciar();	
});