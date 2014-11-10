package br.com.ss.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import br.com.ss.enumerado.Meses;
import br.com.ss.enumerado.NumeroUtil;


/** Classe utilitária para auxiliar em operações com data. */
public class DateUtil {
    /**
     * Hora final.
     */
    private static final int HORA_FINAL = 23;
    
    /**
     * Minuto final.
     */
    private static final int MINUTO_FINAL = 59;
    
    /**
     * Segundo final.
     */
    private static final int SEGUNDO_FINAL = 59;
    
    /** Hora inicial do turno da manhã. */
    private static final int HORA_INICIO_TURNO_MANHA = NumeroUtil.SETE;

    /** Hora inicial do turno da manhã. */
    private static final int HORA_INICIO_TURNO_TARDE = NumeroUtil.TREZE;

    /** Hora inicial do turno da manhã. */
    private static final int HORA_INICIO_TURNO_NOITE = NumeroUtil.DEZENOVE;
    
    /** Pattern para exibir datas no formato default. */
    public static final String PATTERN_DATA_DIA_MES_ANO = "dd/MM/yyyy";
    
    /** Pattern para exibir datas no formato default. */
    public static final String PATTERN_DATA_ANO_MES_DIA = "yyyy/MM/dd";
    
    /** Pattern para exibir datas no formato default. */
    public static final String PATTERN_HORA = "HH:mm";
    
    /**
     * Guarda o valor inteiro de cada mes. Caso queira o numero do mes iniciando por zero entao deve usar o ordinal()
     * Caso queira o numero do mes iniciando por 1 entao deve usar toInt()
     * 
     * @author dcoronel
     */
    public static enum MES {
        /** Janeiro. */
        JANEIRO( 1 ),
        /** Fevereiro. */
        FEVEREIRO( 2 ),
        /** Março. */
        MARCO( 3 ),
        /** Abril. */
        ABRIL( 4 ),
        /** Maio. */
        MAIO( 5 ),
        /** Junho. */
        JUNHO( 6 ),
        /** Julho. */
        JULHO( 7 ),
        /** Agosto. */
        AGOSTO( 8 ),
        /** Setembro. */
        SETEMBRO( 9 ),
        /** Outubro. */
        OUTUBRO( 10 ),
        /** Novembro. */
        NOVEMBRO( 11 ),
        /** Dezembro. */
        DEZEMBRO( 12 );

        /** guarda o mês. */
        private final int mes;

        private MES( int mes ) {
            this.mes = mes;
        }

        public int toInt() {
            return this.mes;
        }

    }

    /**
     * Letter Date or Time Component Presentation Examples G Era designator Text AD y Year Year 1996; 96 M Month in year
     * Month July; Jul; 07 w Week in year Number 27 W Week in month Number 2 D Day in year Number 189 d Day in month
     * Number 10 F Day of week in month Number 2 E Day in week Text Tuesday; Tue a Am/pm marker Text PM H Hour in day
     * (0-23) Number 0 k Hour in day (1-24) Number 24 K Hour in am/pm (0-11) Number 0 h Hour in am/pm (1-12) Number 12 m
     * Minute in hour Number 30 s Second in minute Number 55 S Millisecond Number 978 z Time zone General time zone
     * Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822 time zone -0800
     */

    /** Calendar. */
    private static Calendar calendar;

    /** Formatador de data. */
    static SimpleDateFormat formatador = new SimpleDateFormat();

    /** workaround para somar 1 ao mês - quando utiliza a api date do java. */
    public static final int CORRIGE_MES = 1;

    /**
     * Contrutor privado para previnir instanciação.
     */
    private DateUtil() {
    }

    /** Mapa contendo os nomes dos meses do ano. */
    private static HashMap<Integer, String> mapNomeMes;

    /** Mapa contendo os numeros dos meses do ano. */
    private static HashMap<String, Integer> mapNumeroMes;

    static {
        DateUtil.mapNomeMes = new HashMap<Integer, String>();
        DateUtil.mapNumeroMes = new HashMap<String, Integer>();
        for ( Meses mes : Meses.values() ) {
            DateUtil.mapNomeMes.put( mes.getId(), mes.getDescricao() );
            DateUtil.mapNumeroMes.put( mes.getDescricao(), mes.getId() );
        }
    }

    /**
     * Retorna o nome do Mês da data informada. Ex: Se a data informada for um mês de Janeiro, então retorna "Janeiro"
     * 
     * @param data
     * @return String
     */
    public static String lookupNomeMes( Date data ) {
        return DateUtil.mapNomeMes.get( DateUtil.getMes( data ) );
    }

    /**
     * Retorna o número do Mês da data informada. Ex: Se a data informada for um mês de Janeiro, então retorna "1" .
     * 
     * @param data
     * @return Integer
     */
    public static Integer lookupNumeroMes( Date data ) {
        return DateUtil.mapNumeroMes.get( DateUtil.lookupNomeMes( data ) );
    }

    /**
     * .
     * 
     * @return Calendar
     */
    public static Calendar getInstance() {
        if ( DateUtil.calendar == null ) {
            DateUtil.calendar = new GregorianCalendar();
            Locale locale = new Locale( "pt", "BR" );
            DateUtil.calendar = Calendar.getInstance( locale );
        }
        return (Calendar) DateUtil.calendar.clone();
    }

    /**
     * Calcula a Idade baseado em String. Exemplo: calculaIdade("20/08/1977","dd/MM/yyyy");
     * 
     * @return int a idade
     */
    public static int calculaIdade( String dataNasc, String pattern ) {
        DateFormat sdf = new SimpleDateFormat( pattern );
        Date dataNascInput = null;
        try {
            dataNascInput = sdf.parse( dataNasc );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return DateUtil.calculaIdade( dataNascInput );
    }

    /**
     * Calcula a Idade baseado em java.util.Date.
     * 
     * @param dataNasc
     * @return int a Idade
     */
    public static int calculaIdade( java.util.Date dataNasc ) {
        Period periodo = new Period( new DateTime( dataNasc ), new DateTime() );
        return periodo.getYears();
    }

    /**
     * Verifica qual é o último dia do mês informado.
     * 
     * @param mes
     * @return Integer o último dia do mês informado.
     */
    public static Integer getUltimoDiaMes( Integer mes ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.set( Calendar.MONTH, mes - 1 ); // Lista de mês começa em zero
        Integer lastDay = calenda.getActualMaximum( Calendar.DATE );
        return lastDay;
    }

    /**
     * Verifica se o ano informado é um ano bissexto.
     * <p>
     * Ano Bissexto: No mês de Fevereiro tem 29 dias.
     * </p>
     * 
     * @param ano
     * @return boolean se o ano é ou não um ano bissexto
     */
    public static boolean isAnoBissexto( int ano ) {
        GregorianCalendar gcal = new GregorianCalendar();
        return gcal.isLeapYear( ano );
    }

    /**
     * Cria a data com os parâmetros informados.
     * 
     * @param dia
     * @param mes
     * @param ano
     * @return Date a data formada
     */
    public static Date criarData( Integer dia, Integer mes, Integer ano ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.set( Calendar.YEAR, ano );
        calenda.set( Calendar.MONTH, mes - 1 ); // -1: Primeiro mês começa em zero
        calenda.set( Calendar.DAY_OF_MONTH, dia ); // seta o primeiro dia do mês
        return calenda.getTime();
    }
    
    /**
     * Cria a data com os parâmetros informados.
     * 
     * @param dia
     * @param mes
     * @param ano
     * @return Date a data formada
     */
    public static Date criarData( Integer dia, Integer mes, Integer ano, Integer hora, Integer minuto ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.set( Calendar.YEAR, ano );
        calenda.set( Calendar.MONTH, mes ); 
        calenda.set( Calendar.DAY_OF_MONTH, dia ); // seta o primeiro dia do mês
        calenda.set( Calendar.HOUR_OF_DAY, hora );
        calenda.set( Calendar.MINUTE, minuto );
        calenda.set( Calendar.SECOND, 0 );

        return calenda.getTime();
    }

    /**
     * Incrementa a data pela quantidade de dias informado.
     * 
     * @param data
     * @return Date nova data acrescida pela quantidade de dias informado
     */
    public static Date incrementarDia( Date data, int qtDias ) {
        Calendar cl = DateUtil.getInstance();
        if ( data != null ) {
            cl.setTime( data );
        }
        cl.add( Calendar.DATE, qtDias );
        Date proximoDia = cl.getTime();
        return proximoDia;
    }

    /**
     * Retorna o número do dia da semana da data informada.
     * @param data
     * @return int o dia da semana da data informada.
     */
    public static int getDiaSemana( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        return calenda.get( Calendar.DAY_OF_WEEK );
    }
    
    /**
     * Retorna o número do dia da semana da data informada.
     * Diminui 1 (um) do valor para manter a compatibilidade com o domain DiasDaSemanaDomain.
     * @param data
     * @return int
     * @see br.fpf.jdoctor.gerais.constants.DiasDaSemanaDomain
     */
    public static int getDiaSemanaDomain( Date data ) {
        /* -1 : para manter a compatibilidade com DiasDaSemanaDomain */
        return getDiaSemana( data ) - NumeroUtil.UM;
    }
    

    /**
     * Retorna o número da semana do mês da data informada.
     * 
     * @param data
     * @return int o número da semana do mês da data informada.
     */
    public static int getSemanaMes( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        int semana = calenda.get( Calendar.WEEK_OF_MONTH );
        return semana;
    }

    /**
     * Retorna o número do dia do mês da data informada.
     * 
     * @param data
     * @return int o número do dia do mês da data informada.
     */
    public static int getDiaMes( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        return calenda.get( Calendar.DAY_OF_MONTH );
    }

    /**
     * Obtem o número do mês da data informada.
     * 
     * @param data
     * @return Integer o número do mês da data informada.
     */
    public static Integer getMes( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        Integer mes = calenda.get( Calendar.MONTH ); // -1: Mês começa em zero
        return mes;
    }

    /**
     * Obtem o número do ano da data informada.
     * 
     * @param data
     * @return Integer o número do ano da data informada.
     */
    public static Integer getAno( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        Integer ano = calenda.get( Calendar.YEAR );
        return ano;
    }

    /**
     * Compara se o dia das datas informadas são equivalentes. Não considera a hora na comparação, apenas a data.
     * 
     * @param data1
     * @param data2
     * @return boolean se o dia das datas informadas são equivalentes.
     */
    public static boolean compararDiasData( Date data1, Date data2 ) {
        boolean equals = false;

        int diaC1 = DateUtil.getDiaMes( data1 );
        int diaC2 = DateUtil.getDiaMes( data2 );

        int mesC1 = DateUtil.getMes( data1 );
        int mesC2 = DateUtil.getMes( data2 );

        int anoC1 = DateUtil.getAno( data1 );
        int anoC2 = DateUtil.getAno( data2 );
        equals = ( ( diaC1 == diaC2 ) && ( mesC1 == mesC2 ) && ( anoC1 == anoC2 ) );

        return equals;
    }

    /**
     * Formata a data com o pattern informado.
     * 
     * @return String data formatada.
     */
    public static String formatDateToString( Date data, String pattern ) {
        DateUtil.formatador.applyPattern( pattern );
        return DateUtil.formatador.format( data );
    }
    
    /**
     * Formata a data no formato: dd/mm/yyyy.
     * @param data
     * @return
     */
    public static String ddMMyyyy( Date data ) {
        DateUtil.formatador.applyPattern( PATTERN_DATA_DIA_MES_ANO );
        return DateUtil.formatador.format( data );
    }

    
    /**
     * Formata a data no formato: yyyy/MM/dd.
     * @param data
     * @return
     */
    public static String yyyyMMdd( Date data ) {
        DateUtil.formatador.applyPattern( PATTERN_DATA_ANO_MES_DIA );
        return DateUtil.formatador.format( data );
    }

    /**
     * Recupera a data como String no formato yyyy-mm-dd.
     * 
     * @param data
     * @return String
     */
    public static String getDateAsString( Date data, String pattern ) {
        if ( data != null ) {
            DateUtil.formatador.applyPattern( pattern );
            return DateUtil.formatador.format( data );
        }
        return "";
    }

    /**
     * Converte a String para uma data no formato informado.
     * @param data
     * @return String
     * @throws ParseException 
     */
    public static Date getStringAsDate( String data, String pattern ) throws ParseException {
        if ( data != null ) {
            formatador.applyPattern( pattern );
            return formatador.parse( data );
        }
        return null;
    }
    
    /**
     * Atualiza a data acrescentando a hora informada. 
     * @param data Data a ser modificada
     * @param horaData Hora a ser adicionada na data
     * @return Date Data
     */
    public static Date modificarHoraData( Date data, Date horaData ) {
        if ( data != null && horaData != null ) {
            LocalTime ltHora = new LocalTime( horaData );
            DateTime newDate = new DateTime( data );
            
            newDate = newDate.withHourOfDay( ltHora.hourOfDay().get() );
            newDate = newDate.withMinuteOfHour( ltHora.minuteOfHour().get() );
            newDate = newDate.withSecondOfMinute( ltHora.secondOfMinute().get() );
            
            data = newDate.toDate();
            return data;
        }
        return null;
    }
    

    /**
     * Define a data de nascimento do Paciente.
     */
    public static Date sugerirDataNascimento( int nbIdade ) {
        Date data = new Date();
        Calendar calenda = Calendar.getInstance();
        calenda.setTime( data );
        int ano = DateUtil.getAno( calenda.getTime() );
        calenda.set( Calendar.YEAR, ano - nbIdade );
        return calenda.getTime();
    }

    /**
     * Compara se a primeira data informada é anterior a segunda data. A comparação é feita considerando data, hora e
     * minutos.
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataHora1AnteriorDataHora2( Date data1, Date data2 ) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime( data1 );
        c1.set( Calendar.SECOND, 0 );
        c1.set( Calendar.MILLISECOND, 0 );

        c2.setTime( data2 );
        c2.set( Calendar.SECOND, 0 );
        c2.set( Calendar.MILLISECOND, 0 );

        return ( c1.getTime().before( c2.getTime() ) );
    }
    
    /**
     * Compara se a primeira data informada é posterior a segunda data. A comparação é feita considerando data, hora e
     * minutos.
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataHora1PosteriorDataHora2( Date data1, Date data2 ) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime( data1 );
        c1.set( Calendar.SECOND, 0 );
        c1.set( Calendar.MILLISECOND, 0 );

        c2.setTime( data2 );
        c2.set( Calendar.SECOND, 0 );
        c2.set( Calendar.MILLISECOND, 0 );

        return ( c1.getTime().after( c2.getTime() ) );
    }

    /**
     * calcula Tempo entre duas datas.
     * 
     * @param dataMaior
     *            uma data superior.
     * @param dataMenor
     *            uma data inferior.
     * @return
     */
    public static String calculaTempo( Date dataMaior, Date dataMenor ) {
        DateTime dataMenorJodaTime = new DateTime( dataMenor );
        DateTime dataMaiorJodaTime = new DateTime( dataMaior );
        Period periodo = new Period( dataMenorJodaTime, dataMaiorJodaTime, PeriodType.yearMonthDayTime() );

        StringBuilder builder = new StringBuilder();

        if ( periodo.getYears() > 0 ) {
            builder.append( periodo.getYears() );
            builder.append( " ano" );

            if ( periodo.getYears() > 1 ) {
                builder.append( "s" );
            }
        }

        if ( periodo.getMonths() > 0 ) {
            if ( periodo.getYears() > 0 ) {
                builder.append( " e" );
            }

            builder.append( " " );
            builder.append( periodo.getMonths() );
            

            if ( periodo.getMonths() > 1 ) {
                builder.append( " meses" );
            } else {
                builder.append( " mês" );
            }
        }

        if ( ( periodo.getYears() == 0 ) && ( periodo.getDays() > 0 ) ) {
            if ( periodo.getMonths() > 0 ) {
                builder.append( " e" );
            }

            builder.append( " " );
            builder.append( periodo.getDays() );
            builder.append( " dia" );

            if ( periodo.getDays() > 1 ) {
                builder.append( "s" );
            }
        }

        if ( ( periodo.getMonths() == 0 ) && ( periodo.getYears() == 0 ) ) {
            if ( periodo.getDays() > 0 ) {
                builder.append( " e" );
            }
            builder.append( " " );
            builder.append( periodo.getHours() );
            builder.append( " hora" );

            if ( periodo.getHours() > 1 ) {
                builder.append( "s" );
            }
        }

        return builder.toString();
    }

    /**
     * Calcula dia de forma precisa.
     * 
     * @param anoMaior
     * @param anoMenor
     * @return
     */
    public static int calculaDias( Calendar dataMaior, Calendar dataMenor ) {
        return Days.daysBetween( new DateTime( dataMenor ), new DateTime( dataMaior ) ).getDays();
    }

    /**
     * Retorna a hora da data informada.
     * 
     * @param data
     * @return int o número (hora) da data informada. Ex: 22, ou seja, 22:00 hrs.
     */
    public static int getHoraDia( Date data ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( data );
        return calenda.get( Calendar.HOUR_OF_DAY );
    }

    /**
     * Retorna o minuto da data informada.
     * 
     * @param hora
     * @return int o número (minuto) da data informada. Ex: 55, 55 min'.
     */
    public static int getMinutoHora( Date hora ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( hora );
        return calenda.get( Calendar.MINUTE );
    }

    /**
     * Retorna o segundo da hora informada.
     * @param hora
     * @return int o número (segundo) da data informada. Ex: 55, 55 seg'.
     */
    public static int getSegundoHora( Date hora ) {
        Calendar calenda = DateUtil.getInstance();
        calenda.setTime( hora );
        return calenda.get( Calendar.SECOND );
    }

    /**
     * Compara se o as datas informadas são equivalentes. Este método considera o dia, mês, ano, hora e minuto
     * 
     * @param data1
     * @param data2
     * @return boolean se as datas informadas são equivalentes.
     */
    public static boolean compararData( Date data1, Date data2 ) {
        boolean equals = false;

        int diaC1 = DateUtil.getDiaMes( data1 );
        int diaC2 = DateUtil.getDiaMes( data2 );

        int mesC1 = DateUtil.getMes( data1 );
        int mesC2 = DateUtil.getMes( data2 );

        int anoC1 = DateUtil.getAno( data1 );
        int anoC2 = DateUtil.getAno( data2 );

        int horaC1 = DateUtil.getHoraDia( data1 );
        int horaC2 = DateUtil.getHoraDia( data2 );

        int minutoC1 = DateUtil.getMinutoHora( data1 );
        int minutoC2 = DateUtil.getMinutoHora( data2 );

        equals = ( ( diaC1 == diaC2 ) && ( mesC1 == mesC2 ) && ( anoC1 == anoC2 ) && ( horaC1 == horaC2 ) 
                && ( minutoC1 == minutoC2 ) );

        return equals;
    }
    
    /**
     * Compara se o as datas informadas são equivalentes. Este método considera o dia, mês, ano.
     * @param data1
     * @param data2
     * @return boolean se as datas informadas são equivalentes.
     */
    public static boolean compararDiaMesAno( Date data1, Date data2 ) {
        boolean equals = false;

        int diaC1 = DateUtil.getDiaMes( data1 );
        int diaC2 = DateUtil.getDiaMes( data2 );

        int mesC1 = DateUtil.getMes( data1 );
        int mesC2 = DateUtil.getMes( data2 );

        int anoC1 = DateUtil.getAno( data1 );
        int anoC2 = DateUtil.getAno( data2 );

        equals = ( ( diaC1 == diaC2 ) && ( mesC1 == mesC2 ) && ( anoC1 == anoC2 ) );

        return equals;
    }

    /**
     * Compara se o horario informados são equivalentes. Este método considera a hora e minuto
     * 
     * @param data1
     * @param data2
     * @return boolean se os hoarios informadas são equivalentes.
     */
    public static boolean compararHoraMinuto( Date data1, Date data2 ) {

        int horaC1 = DateUtil.getHoraDia( data1 );
        int horaC2 = DateUtil.getHoraDia( data2 );

        int minutoC1 = DateUtil.getMinutoHora( data1 );
        int minutoC2 = DateUtil.getMinutoHora( data2 );
        return ( ( horaC1 == horaC2 ) && ( minutoC1 == minutoC2 ) );
    }

    /**
     * Transforma uma data inicial do tipo date para calendar com valores iniciais. data inicio para hora inicial.
     * 
     * @param dataInicio
     */
    public static Calendar truncateDataInicio( Date dataInicio ) {
        if ( dataInicio != null ) {
            GregorianCalendar data = new GregorianCalendar();
            data.setTime( dataInicio );
            data.set( Calendar.HOUR_OF_DAY, 0 );
            data.set( Calendar.MINUTE, 0 );
            data.set( Calendar.SECOND, 0 );
            data.set( Calendar.MILLISECOND, 0 );
            return data;
        }
        return null;
    }

    /**
     * Transforma uma data final do tipo date para calendar data fim para hora final.
     * 
     * @param dataFim
     */
    public static Calendar truncateDataFim( Date dataFim ) {
        if ( dataFim != null ) {
            GregorianCalendar data = new GregorianCalendar();
            data.setTime( dataFim );
            data.set( Calendar.HOUR_OF_DAY, HORA_FINAL );
            data.set( Calendar.MINUTE, MINUTO_FINAL );
            data.set( Calendar.SECOND, SEGUNDO_FINAL );
            data.set( Calendar.MILLISECOND, 0 );
            return data;
        }
        return null;
    }

    /**
     * Metodo que calcula anos/mes/dia de vida.
     * 
     * @param data
     * @return
     */

    public static String getIdade( Date data ) {
        return DateUtil.calculaTempo( new Date(), data );
    }

    /**
     * Metedo para calcular o tempo vencimento. Meterial fracionado, quando e medicamento.
     * 
     * @param data
     * @return
     */
    public static Date dataFracionamento( Date data ) {
        final int quatro = 4;
        DateTime dt = new DateTime();
        Calendar dateVenc = new GregorianCalendar();
        dateVenc.setTime( data );

        Calendar dataInicio = Calendar.getInstance();
        // Atribui a data de vencimento do item.
        dataInicio.setTime( data );

        // Data de hoje
        Calendar dataFinal = Calendar.getInstance();

        // Calcula a diferença entre hoje e da data de inicio
        long diferenca = dataInicio.getTimeInMillis() - dataFinal.getTimeInMillis();

        // Quantidade de milissegundos em um dia

        long diasDiferenca = ( diferenca / dt.millisOfDay().getMaximumValue() ) / quatro;

        Calendar atualClone = (Calendar) dataFinal.clone();
        atualClone.add( Calendar.DATE, (int) diasDiferenca );

        dateVenc.setTime( atualClone.getTime() );

        return dateVenc.getTime();
    }
    
    /**
     * Compara dois parâmetros de hora atraves do pattern passado.
     * Obs.: Checagem ocorre em cima da String do pattern retornada.
     * @param h1
     * @param h2
     * @return verdadeiro, se pattern(h1) == pattern(h2)
     */
    public static boolean isSamePatternValue( Date h1, Date h2, String pattern ) {
        if ( ( h1 != null ) && ( h2 != null ) ) {
            SimpleDateFormat formatter = new SimpleDateFormat( pattern );
            try {
                return formatter.format( h1 ).equalsIgnoreCase( formatter.format( h2 ) );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * Verifica se a data passada esta dentro do intervalo.
     * @param p1
     * @param p2
     * @return
     */
    @SuppressWarnings( "deprecation" )
    public static boolean isNoPeriodo( Date dataInicial, Date dataFinal, Date dataToCheck ) {
        
        final int tooLongYear = 2200;
        final int startYearDate = 1900;
        
        Date dtFim = dataFinal;
        if ( dtFim == null ) {
            dtFim = new Date( tooLongYear - startYearDate, 1, 1 );
        }
        
        Interval intervalo1 = new Interval( new LocalDate( dataInicial ).toDateTimeAtStartOfDay(), 
                                            new LocalDate( dtFim ).toDateTimeAtCurrentTime() );
        
        Interval intervalo2 = new Interval( new LocalDate( dataToCheck ).toDateTimeAtStartOfDay().plusSeconds( 1 ), 
                                            new LocalDate( dataToCheck ).toDateTimeAtStartOfDay().plusSeconds( 1 ) );
        
        return intervalo1.overlap( intervalo2 ) != null;
    }
    
    /**
     * Verifica se a data informada está no range da hora inicial e final.
     * @param hrInicial
     * @param hrFinal
     * @param data
     * @return boolean
     */
    public static boolean isHoraNoPeriodo( Date hrInicial, Date hrFinal, Date data ) {

        DateTime dtInicio = new DateTime( hrInicial );
        DateTime dtFim = new DateTime( hrFinal );
        
        DateTime dtData = new DateTime( data );
        // mantem a compatibilidade entre as data para validar apenas a hora 
        dtData = dtData.withYear( dtInicio.getYear() );
        dtData = dtData.withMonthOfYear( dtInicio.getMonthOfYear() );
        dtData = dtData.withDayOfMonth( dtInicio.getDayOfMonth() );
        
        Interval intervalo1 = new Interval( dtInicio, dtFim );
        Interval intervalo2 = new Interval( dtData, dtData );

        return intervalo1.overlap( intervalo2 ) != null;
    }
    
    /**
     * Atualiza a data para hora inicial do turno atual.
     * Ex: Caso a hora atual seje 15:15, retorna a data com a hora inicial do turno da tarde. 
     * @param data
     * @return Date
     */
    public static Date truncateDataByPeriodoAtual( Date data ) {
        GregorianCalendar cal = new GregorianCalendar();
        
        int horaToTurno; 
        int horaData = getHoraDia( data );
        if ( horaData <= HORA_INICIO_TURNO_MANHA && horaData < HORA_INICIO_TURNO_TARDE ) {
            horaToTurno = HORA_INICIO_TURNO_MANHA;
        } else if ( horaData >= HORA_INICIO_TURNO_TARDE && horaData < HORA_INICIO_TURNO_NOITE ) {
            horaToTurno = HORA_INICIO_TURNO_TARDE;
        } else {
            horaToTurno = HORA_INICIO_TURNO_NOITE;
        }
        
        cal.setTime( data );
        cal.set( Calendar.HOUR_OF_DAY, horaToTurno );
        cal.set( Calendar.MINUTE, NumeroUtil.UM );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );
        
        return cal.getTime();
    }
    
    
    /**
     * Verifica se a primeira data é menor ou igual, levando em consideracao apenas dias/meses/anos.
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataAnteriorOuIgual( Date data1, Date data2 ) {
        LocalDate l1 = new LocalDate( data1 );
        LocalDate l2 = new LocalDate( data2 );
       
        if ( data1 != null && data2 != null ) {
            return l1.isBefore( l2 ) || l1.isEqual( l2 );    
        }
        
        return false;
    }
    
    /**
     * Verifica se a primeira data é menor, levando em consideracao apenas dias/meses/anos.
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataAnterior( Date data1, Date data2 ) {
        LocalDate l1 = new LocalDate( data1 );
        LocalDate l2 = new LocalDate( data2 );
        
        if ( data1 != null && data2 != null ) {
            return l1.isBefore( l2 );
        }
        
        return false;
    }
    
    /**
     * Verifica se a primeira data é maior, levando em consideracao apenas dias/meses/anos.
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataSuperior( Date data1, Date data2 ) {
        LocalDate l1 = new LocalDate( data1 );
        LocalDate l2 = new LocalDate( data2 );
        
        if ( data1 != null && data2 != null ) {
            return l1.isAfter( l2 );
        }
        
        return false;
    }

   /**
    * Compara se a primeira data informada é anterior a segunda data. A comparação é feita considerando-se apenas os
    * dias. Hora e minuto não são considerados na comparação.
    * 
    * @param data1
    * @param data2
    * @return
    */
   public static boolean isData1AnteriorData2( Date data1, Date data2 ) {
       Calendar c1 = Calendar.getInstance();
       Calendar c2 = Calendar.getInstance();
       c1.setTime( data1 );
       c1.set( Calendar.HOUR_OF_DAY, 0 );
       c1.set( Calendar.MINUTE, 0 );
       c1.set( Calendar.SECOND, 0 );
       c1.set( Calendar.MILLISECOND, 0 );

       c2.setTime( data2 );
       c2.set( Calendar.HOUR_OF_DAY, 0 );
       c2.set( Calendar.MINUTE, 0 );
       c2.set( Calendar.SECOND, 0 );
       c2.set( Calendar.MILLISECOND, 0 );

       return ( c1.getTime().before( c2.getTime() ) );
   }
   
   /**
    * calcula Quantidade de dias entre as datas.
    * 
    * @param dataMaior
    *            uma data superior.
    * @param dataMenor
    *            uma data inferior.
    * @return
    */
   public static Integer calculaPeriodoEmDias( Date dataMenor, Date dataMaior ) {
       if ( dataMenor != null && dataMaior != null ) {
           DateTime dataMenorJodaTime = new DateTime( dataMenor.getTime() );
           DateTime dataMaiorJodaTime = new DateTime( dataMaior.getTime() );
           Period periodo = new Period( dataMenorJodaTime, dataMaiorJodaTime, PeriodType.days() );
           return periodo.getDays();
       }
       return null;
   }
   
   /**
    * Calcula a data final.
    * @param dataInicial
    * @param qtdeDias
    * @return
    */
   public static Date calculaDataFinal( Date dataInicial, int qtdeDias ) {
       return new DateTime( dataInicial ).plusDays( qtdeDias ).toDate();
   }
   
   /**
    * Metodo para zerar a hora de uma data.
    * @param data
    */
   public static Date zeraHora( Date dt ) {
       if ( dt != null ) {
           return truncateDataInicio( dt ).getTime();
       }
       return null;
   }
   
   /**
    * Zera horas.
    * @param dt
    * @return
    */
   public static Date zeraHoraFinal( Date dt ) {
       if ( dt != null ) {
           return truncateDataFim( dt ).getTime();
       }
       return null;
   }
   

	public static java.sql.Date formataDataAtual() throws Exception {
		java.sql.Date localDate = null;
		try {
			
			java.util.Date localDate1 = new java.util.Date( System.currentTimeMillis());
			SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat( );
			SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat( "dd/MM/yyyy");
			localDate = new java.sql.Date(localSimpleDateFormat2.parse( localSimpleDateFormat1.format(localDate1)).getTime());
			
		} catch (ParseException localParseException) {
			throw localParseException;
		}
		return localDate;
	}
   
   /**
    * Metodo para formatar uma data de emissao de relatorio.
    */
   public static String getDataRelatorio() {
       Date d = new Date( System.currentTimeMillis() );
       return "Manaus, " + new SimpleDateFormat( "d" ).format( d ) + " de "
               + new SimpleDateFormat( "MMMM" ).format( d ) + " de "
               + new SimpleDateFormat( "yyyy" ).format( d ) + " - "
               + new SimpleDateFormat( "H:mm" ).format( d );
   }

	/**
     * Metodo para formatar uma data de emissao de relatorio.
     */
    public static String getDataRelatorio( Date date ) {
        return "Manaus, " + new SimpleDateFormat( "d" ).format( date ) + " de "
                + new SimpleDateFormat( "MMMM" ).format( date ) 
                + " de " + new SimpleDateFormat( "yyyy" ).format( date );
    }
    
    /**
     * Converte a hora.
     */
   @SuppressWarnings( "static-access" )
   public static Date converterDataHora( Date data, String hora ) {
        try {            
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime( data );
            
            StringBuilder strData = new StringBuilder();
            strData.append( gc.get( gc.DAY_OF_MONTH ) ).append( "/" );
            strData.append( gc.get( gc.MONTH ) + 1 ).append( "/" );
            strData.append( gc.get( gc.YEAR ) ).append( " " );
            strData.append( hora );
            
            DateFormat format = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
            Date result = format.parse( strData.toString() );            
            return result;
        } catch ( Exception e ) {
            return null;
        }
    }
       
       /**
     * Converte a hora.
     */
    @SuppressWarnings( "static-access" )
    public static Date converterDataHora( Date data, Date hora ) {
        try {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime( data );
            
            GregorianCalendar gchr = new GregorianCalendar();
            gchr.setTime( hora );            

            StringBuilder strData = new StringBuilder();
            strData.append( gc.get( gc.DAY_OF_MONTH ) ).append( "/" );
            strData.append( gc.get( gc.MONTH ) + 1 ).append( "/" );
            strData.append( gc.get( gc.YEAR ) ).append( " " );
            strData.append( gchr.get( gc.HOUR_OF_DAY ) ).append( ":" );
            strData.append( gchr.get( gc.MINUTE ) );

            DateFormat format = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
            Date result = format.parse( strData.toString() );
            return result;
        } catch ( Exception e ) {
            return null;
        }
    }   

	/**
	 * Valida se a data informada está no futuro.
	 * @param date
	 * @return boolean
	 */
	public static boolean isDataFuturo( Date date) {
		return date.getTime() > new Date().getTime();
	}
	
	
	public static Calendar getDataAtual() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		return cal;
	}
	
	
}
