var Brewer = Brewer || {};

Brewer.MascaraCnpjCpf = (function(){
	
	function MascaraCnpjCpf(){
		this.inputCnpjCpf = $('#input-cliente-cpfcnpj');
		this.labelCnpjCpf = $('[for=input-cliente-cpfcnpj]');
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
	}
	
	MascaraCnpjCpf.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onChangeTipoPessoa.bind(this));
		var tipoPessoaSelecionado = this.radioTipoPessoa.filter(':checked')[0];
		if (tipoPessoaSelecionado){
			aplicarMascara.call(this, $(tipoPessoaSelecionado));
		}
	}
	
	function aplicarMascara(tipoPessoaSelecionada){
		this.inputCnpjCpf.mask(tipoPessoaSelecionada.data("mascara"))
		this.labelCnpjCpf.text(tipoPessoaSelecionada.data("tipo-pessoa"));		
		this.inputCnpjCpf.removeAttr('disabled');
	}
	
	function onChangeTipoPessoa(evento) {
		this.inputCnpjCpf.val('');
		var tipoPessoaSelecionada = $(evento.currentTarget);
		aplicarMascara.call(this, tipoPessoaSelecionada);
	}
	
	return MascaraCnpjCpf;
	
}());

$(function(){
	var mascaraCnpjCpf = new Brewer.MascaraCnpjCpf();
	mascaraCnpjCpf.iniciar();
})