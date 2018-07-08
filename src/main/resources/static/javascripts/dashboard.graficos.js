var Brewer = Brewer || {};

Brewer.GraficosVendaProMes = (function(){
	
	function GraficosVendaProMes(){
		this.ctx = $('#graficoVendaPorMes')[0].getContext('2d');
	}
	
	GraficosVendaProMes.prototype.iniciar = function(){
		var graficoVendaPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
		    	datasets: [{
		    		label: 'Venda por mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
		    		pointBorderColor: "rgba(26,179,148,1)",
		    		pointBackgroundColor: "#fff",
		    		data: [5, 1, 19, 30, 0, 50]
		    	}]
		    }
		});
	}	
	
	return GraficosVendaProMes;
	
}());

$(function(){
	var graficoVendaPorMes = new Brewer.GraficosVendaProMes();
	graficoVendaPorMes.iniciar();
})