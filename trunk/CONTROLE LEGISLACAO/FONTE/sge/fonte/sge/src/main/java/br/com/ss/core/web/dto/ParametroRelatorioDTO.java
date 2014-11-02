package br.com.ss.core.web.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.ss.academico.enumerated.TipoPesquisaData;

public class ParametroRelatorioDTO {

	// FIXME #Peninha remover classe se nao estiver sendo utilizada
	
	private Date dataInicio;

	private Date dataFim;

	private TipoPesquisaData dataPesquisa;

	private TipoPesquisaData[] dataPesquisas;

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public TipoPesquisaData getDataPesquisa() {
		return dataPesquisa;
	}

	public void setDataPesquisa(TipoPesquisaData dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
	}

	public TipoPesquisaData[] getDataPesquisas() {
		return this.dataPesquisa.values();
	}

	public void pegarMesCorrente() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mes = (cal.get(Calendar.MONDAY) + 1);
		int ano = cal.get(Calendar.YEAR);

		try {
			this.dataInicio = (new SimpleDateFormat("dd/MM/yyyy")).parse("01/"
					+ mes + "/" + ano);
			this.dataFim = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/"
					+ mes + "/" + ano);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
