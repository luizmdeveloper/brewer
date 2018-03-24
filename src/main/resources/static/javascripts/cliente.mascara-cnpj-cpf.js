var Brewer = Brewer || {};

Brewer.MascaraCnpjCpf = (function(){
	
	function MascaraCnpjCpf(){
		this.inputCnpjCpf = $('#input-cliente-cpfcnpj');
		this.labelCnpjCpf = $('[for=input-cliente-cpfcnpj]');
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
	}
	
	MascaraCnpjCpf.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onChangeTipoPessoa.bind(this));
	}
	
	function onChangeTipoPessoa(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		this.labelCnpjCpf.text(tipoPessoaSelecionada.data("tipo-pessoa"));
		
		this.inputCnpjCpf.val('');
		this.inputCnpjCpf.removeAttr('disabled');
		this.inputCnpjCpf.mask(tipoPessoaSelecionada.data("mascara"))		
	}
	
	return MascaraCnpjCpf;
	
}());

$(function(){
	var mascaraCnpjCpf = new Brewer.MascaraCnpjCpf();
	mascaraCnpjCpf.iniciar();
})