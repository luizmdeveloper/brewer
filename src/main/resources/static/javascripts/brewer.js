var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	
	function MaskMoney(){
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');		
	}
	
	MaskMoney.prototype.enable = function(){
		this.decimal.maskMoney({decimal : ',', thousands : '.'});
		this.plain.maskMoney({precision : 0, thousands : '.'});		
	}
		
	return MaskMoney;
}());


Brewer.PhoneNumberMask = (function(){
	
	function PhoneNumberMask (){
		this.inputPhoneNumber = $('.js-phone-number')
	}
	
	PhoneNumberMask.prototype.enable = function(){
		var maskBehavior = function (val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		}
		var options = {
				onKeyPress: function(val, e, field, options) {
					field.mask(maskBehavior.apply({}, arguments), options);
			    }
		};

		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return PhoneNumberMask; 
	
}());

Brewer.CepMask = (function(){
	
	function CepMask(){
		this.inputCep = $('.js-cep');
	}
	
	CepMask.prototype.enable = function(){
		var maskBehavior = function (val) {
			return '00.000-000';
		}
		var options = {
				onKeyPress: function(val, e, field, options) {
					field.mask(maskBehavior.apply({}, arguments), options);
			    }
		};

		this.inputCep.mask(maskBehavior, options);
	}
	
	
	return CepMask;
})();

Brewer.DateMask = (function(){
	
	function DateMask(){
		this.inputDateMask = $('.js-date');
	}
	
	DateMask.prototype.enable = function() {
		this.inputDateMask.mask('00/00/0000');
		this.inputDateMask.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true				
		});
	}
	
	return DateMask;
	
})();


Brewer.Security = (function(){
	
	function Security(){
		this.token = $('input[name=token]').val();
		this.header = $('input[name=headerName]').val();
	}
	
	Security.prototype.enable = function(){
		$(document).ajaxSend(function(event,jqxhr,settings ) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

numeral.language('pt-br');

Brewer.formatarMoeda = function(valor){
	return numeral(valor).format('0,0.00'); 
}

Brewer.removerMascara = function(valor){
	return numeral().unformat(valor);
}

$(function(){	
	var maskMoney =  new Brewer.MaskMoney();
	maskMoney.enable();	
	
	var phoneNumberMask = new Brewer.PhoneNumberMask();
	phoneNumberMask.enable();
	
	var cepMask = new Brewer.CepMask();
	cepMask.enable();
	
	var dateMask = new Brewer.DateMask();
	dateMask.enable();
	
	var security = new Brewer.Security();
	security.enable();
});