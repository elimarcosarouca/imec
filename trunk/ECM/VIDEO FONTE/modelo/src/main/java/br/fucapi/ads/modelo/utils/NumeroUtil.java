package br.fucapi.ads.modelo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;


public final class NumeroUtil {


    /**
     * 
     */
    public static final short V_MONETARIO_LENGTH = 14;

    /**
     * 
     */
    public static final short V_MONETARIO_WIDTH = 100;

    public static final short QTDE_WIDTH = 50;

    public static final short QTDE_LENGTH = 9;
    
    /**
     * N�mero 0.
     */
    public static final int ZERO = 0;

    /**
     * N�mero 1.
     */
    public static final int UM = 1;

    /**
     * N�mero 3.
     */
    public static final int TRES = 3;

    /**
     * N�mero 4.
     */
    public static final int QUATRO = 4;

    /**
     * N�mero 5.
     */
    public static final int CINCO = 5;

    /**
     * N�mero 6.
     */
    public static final int SEIS = 6;

    /**
     * N�mero 7.
     */
    public static final int SETE = 7;

    /**
     * N�mero 8.
     */
    public static final int OITO = 8;
    
    /**
     * N�mero 9.
     */
    public static final int NOVE = 9;

    /**
     * N�mero 10.
     */
    public static final int DEZ = 10;
    
    /**
     * N�mero 11.
     */
    public static final int ONZE = 11;
    
    /**
     * N�mero 12.
     */
    public static final int DOZE = 12;

    /**
     * N�mero 13.
     */
    public static final int TREZE = 13;
    
    /**
     * N�mero 14.
     */
    public static final int QUATORZE = 14;
    
    /**
     * N�mero 15.
     */
    public static final int QUINZE = 15;
    
    /**
     * N�mero 16.
     */
    public static final int DEZESSEIS = 16;
    
    /**
     * N�mero 17.
     */
    public static final int DEZESSETE = 17;

    /**
     * N�mero 18.
     */
    public static final int DEZOITO = 18;

    /**
     * N�mero 19.
     */
    public static final int DEZENOVE = 19;
    
    /**
     * N�mero 20.
     */
    public static final int VINTE = 20;


    /**
     * N�mero 21.
     */
    public static final int VINTE_E_UM = 21;

    /**
     * N�mero 22.
     */
    public static final int VINTE_E_DOIS= 22;
    
    /**
     * N�mero 23.
     */
    public static final int VINTE_E_TRES = 23;
    
    /**
     * N�mero 24.
     */
    public static final int VINTE_E_QUATRO = 24;
    
    /**
     * N�mero 25.
     */
    public static final int VINTE_E_CINCO = 25;

    /**
     * N�mero 26.
     */
    public static final int VINTE_E_SEIS = 26;

    /**
     * N�mero 27.
     */
    public static final int VINTE_E_SETE = 27;

    /**
     * N�mero 28.
     */
    public static final int VINTE_E_OITO = 28;
    
    /**
     * N�mero 29.
     */
    public static final int VINTE_E_NOVE = 29;

    /**
     * N�mero 30.
     */
    public static final int TRINTA = 30;

    /**
     * N�mero 31.
     */
    public static final int TRINTA_E_UM = 31;
    
    /**
     * N�mero 32.
     */
    public static final int TRINTA_E_DOIS = 32;

    /**
     * N�mero 33.
     */
    public static final int TRINTA_E_TRES = 33;

    /**
     * N�mero 34.
     */
    public static final int TRINTA_E_QUATRO = 34;
    
    /**
     * N�mero 35.
     */
    public static final int TRINTA_E_CINCO = 35;
    
    /**
     * N�mero 36.
     */
    public static final int TRINTA_E_SEIS = 36;
    
    /**
     * N�mero 37.
     */
    public static final int TRINTA_E_SETE = 37;
    
    /**
     * N�mero 40.
     */
    public static final int QUARENTA = 40;

    /**
     * N�mero 45.
     */
    public static final int QUARENTA_E_CINCO = 45;

    
    /**
     * N�mero 48.
     */
    public static final int QUARENTA_E_OITO = 48;

    /**
     * N�mero 50.
     */
    public static final int CINQUENTA = 50;

    /**
     * N�mero 60.
     */
    public static final int SESSENTA = 60;

    /**
     * N�mero 65.
     */
    public static final int SESSENTA_E_CINCO = 65;

    /**
     * N�mero 75.
     */
    public static final int SETENTA_E_CINCO = 75;

    /**
     * N�mero 76.
     */
    public static final int SETENTA_E_SEIS = 76;

    /**
     * N�mero 77.
     */
    public static final int SETENTA_E_SETE = 77;

    /**
     * N�mero 78.
     */
    public static final int SETENTA_E_OITO = 78;
    
    /**
     * N�mero 80.
     */
    public static final int OITENTA = 80;

    /**
     * N�mero 99.
     */
    public static final int NOVENTA_NOVE = 99;

    /**
     * N�mero 100.
     */
    public static final int CEM = 100;

    /**
     * N�mero 128.
     */
    public static final int CEM_VINTE_OITO = 128;
    
    /**
     * N�mero 150.
     */
    public static final int CENTO_E_CINQUENTA = 150;
    
    
    /**
     * N�mero 255.
     */
    public static final int DOIS_CINCO_CINCO = 255;

    /**
     * N�mero 2.
     */
    public static final int DOIS = 2;

    /**
     * N�mero 200.
     */
    public static final int DUZENTOS = 200;

    /**
     * N�mero 255.
     */
    public static final int DUZENTOS_E_CINQUENTA_CINCO = 255;
    
    /**
     * N�mero 300.
     */
    public static final int TREZENTOS = 300;
    
    /**
     * N�mero 400.
     */
    public static final int QUATROCENTOS = 400;

    /**
     * N�mero 500.
     */
    public static final int QUINHENTOS = 500;

    /**
     * N�mero 1000.
     */
    public static final int MIL = 1000;

    /**
     * N�mero 1024.
     */
    public static final int MIL_VINTE_QUATRO = 1024;
    
    /**
     * N�mero 1900.
     */
    public static final int MIL_NOVECENTOS = 1900;
    
    /**
     * N�mero 2010.
     */
    public static final int DOIS_MIL_DEZ = 2010;
    /**
     * N�mero 1024.
     */
    public static final int DOIS_MIL_QUARENTA_OITO = 2048;

    /**
     * N�mero 10000.
     */
    public static final int DEZ_MIL = 10000;
    
    
    /**
     * N�mero 131089.
     */
    public static final int UM_TRES_UM_ZERO_OITO_NOVE = 131089;

    /**
     * Classe n�o instancia objetos.
     */
    private NumeroUtil() {  }
    
    
    /* ---------- Metodos utilitarios ---------------------- */
    
    /**
     * Faz a soma de dois BigDecimais.
     * 
     * @param vl1
     * @param vl2
     * @return
     */
    public static final BigDecimal somarBigDecimal( BigDecimal vl1, BigDecimal vl2 ) {
        BigDecimal tot = null;
        tot = vl1;
        if ( vl2 != null ) {
            if ( tot == null ) {
                tot = vl2;
            } else {
                tot = tot.add( vl2 );
            }
        }
        return tot;
    }

    /**
     * Faz a soma de tr�s BigDecimais.
     * 
     * @param vl1
     * @param vl2
     * @param vl3
     * @return
     */
    public static final BigDecimal somarBigDecimal( BigDecimal vl1, BigDecimal vl2, BigDecimal vl3 ) {
        BigDecimal tot = null;
        tot = somarBigDecimal( somarBigDecimal( vl1, vl2 ), vl3 );
        return tot;
    }

    /**
     * Faz a soma de quatro BigDecimais.
     * 
     * @param vl1
     * @param vl2
     * @param vl3
     * @param vl4
     * @return
     */
    public static final BigDecimal somarBigDecimal( BigDecimal vl1, BigDecimal vl2, BigDecimal vl3, BigDecimal vl4 ) {
        BigDecimal tot = null;
        tot = somarBigDecimal( somarBigDecimal( vl1, vl2 ), somarBigDecimal( vl3, vl4 ) );
        return tot;
    }

    /**
     * converte para decimal padrao br (string).
     * 
     * @param vl1
     * @return
     */
    public static final String converteDecimalToStringBR( BigDecimal vl1 ) {
        DecimalFormat df = new DecimalFormat( "###,###,##0.00" );
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator( ',' );
        df.setDecimalFormatSymbols( dfs );
        String dx = df.format( vl1.doubleValue() );
        return dx;
    }

    /**
     * converte para decimal padrao br (string).
     * 
     * @param vl1
     * @return
     */
    public static final String converteDecimalToStringBRCents( BigDecimal vl1 ) {
        DecimalFormat df = new DecimalFormat( "###.##" );
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator( ',' );
        df.setDecimalFormatSymbols( dfs );
        String dx = df.format( vl1.doubleValue() );
        return dx;
    }

    public static boolean isSomatoriaValid( List<BigDecimal> listValues, boolean somaNegativos ) {
        if ( listValues == null ) {
            return false;
        }
        BigDecimal sum = somaIgnoreNull( listValues, somaNegativos );
        if ( sum != null ) {
            return ( sum.compareTo( BigDecimal.valueOf( 100 ) ) <= 0 // verifica se eh menor ou igual a 100
            && sum.compareTo( BigDecimal.valueOf( 0 ) ) >= 0 ); // verifica se eh maior ou igual a 0
        }

        return false;

    }

    /**
     * Soma uma lista, ignorando valores nulos.
     * 
     * @param listValues
     * @return
     */
    public static BigDecimal somaIgnoreNull( List<BigDecimal> listValues, boolean somaNegativos, BigDecimal valorInicial ) {
        BigDecimal sum = valorInicial;
        for ( BigDecimal value : listValues ) {
            if ( value != null ) {
                if ( ( value.signum() < 0 ) && !somaNegativos ) {
                    return null;
                }
                if ( sum == null ) {
                    sum = BigDecimal.ZERO;
                }
                sum = sum.add( value );
            }
        }
        return sum; // verifica se eh maior ou igual a 0
    }

    /**
     * Soma uma lista, ignorando valores nulos.
     * 
     * @param listValues
     * @return
     */
    public static BigDecimal somaIgnoreNull( List<BigDecimal> listValues, boolean somaNegativos ) {
        return somaIgnoreNull( listValues, somaNegativos, BigDecimal.ZERO );
    }

    /**
     * @param array
     * @param index
     */
    public static ArrayList<BigDecimal> fillListValues( Object[] values ) {
        List<Object> listaObj = new ArrayList<Object>();
        for ( Object o : values ) {
            listaObj.add( o );
        }
        return fillListValues( listaObj );
    }

    /**
     * @param array
     * @param index
     */
    public static ArrayList<BigDecimal> fillListValues( List<?> values ) {
        ArrayList<BigDecimal> valores = new ArrayList<BigDecimal>();
        for ( Object value : values ) {
            if ( valores != null ) {
                if ( value instanceof Float ) {
                    valores.add( BigDecimal.valueOf( (Float) value ) );
                } else if ( value instanceof Integer ) {
                    valores.add( BigDecimal.valueOf( (Integer) value ) );
                }
            }
        }
        return valores;
    }

    /**
     * @param array
     * @param index
     */
    public static ArrayList<Float> calculaPercentualList( Integer[] values ) {
        return calculaPercentualList( fillListValues( values ) );
    }

    /**
     * @param array
     * @param index
     * @return Percentual
     */
    public ArrayList<Float> calculaPercentualList( Float[] values ) {
        return calculaPercentualList( fillListValues( values ) );
    }

    /**
     * @param array
     * @param index
     * @return Percentual
     */
    public static ArrayList<Float> calculaPercentualList( List<BigDecimal> values ) {
        ArrayList<Float> percentuais = new ArrayList<Float>();
        Float total = somaIgnoreNull( values, true ).floatValue();

        for ( BigDecimal value : values ) {

            if ( value != null ) {
                percentuais.add( (float) ( NumeroUtil.CEM * value.floatValue() ) / total );
            } else {
                percentuais.add( null );
            }
        }

        return percentuais;
    }

    /**
     * @param values
     * @return
     */
    public static Float totalListValues( List<?> values ) {
        List<BigDecimal> valores = fillListValues( values );
        return somaIgnoreNull( valores, true ).floatValue();
    }

    /**
     * @param values
     * @return
     */
    public static Float totalListValues( Integer[] values ) {
        List<BigDecimal> valores = fillListValues( values );
        return somaIgnoreNull( valores, true ).floatValue();
    }

    /**
     * @param values
     * @return
     */
    public static Float totalListValues( Float[] values ) {
        List<BigDecimal> valores = fillListValues( values );
        return somaIgnoreNull( valores, true ).floatValue();
    }

    /**
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal divideBig( BigDecimal d1, BigDecimal d2 ) {
        BigDecimal result = BigDecimal.ZERO;
        if ( d1 != null & d2 != null && d2.compareTo( BigDecimal.ZERO ) > 0 ) {
            d1 = d1.setScale( NumeroUtil.CINCO );
            d2 = d2.setScale( NumeroUtil.CINCO );
            result = result.setScale( NumeroUtil.CINCO );
            result = d1.divide( d2, RoundingMode.HALF_UP );
        }
        return result;
    }

    
    public static Float arredondarValor(Float value) {
    	return arredondarValor(new Double(value) ).floatValue();
    }
    
    public static Double arredondarValor(Double value) {
    	BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN);
		return bd.doubleValue();
    }

    /**
     * Retorna o valor do Bigdecimal ou BigDecimal.ZERO, quando o valor for null.
     * @param nb
     * @return
     */
    public static BigDecimal getBigDecimal( BigDecimal nb ) {
        if ( nb == null ) {
            return BigDecimal.ZERO;
        }
        return nb;
    }
    

    /**
     * Retorna o valor do Float ou ZERO, quando o valor for null.
     * @param value
     * @return Float
     */
    public static Float getFloat( Float value ) {
        if ( value == null ) {
            return 0f;
        }
        return value;
    }

    /**
     * Retorna o valor do integer ou ZERO, quando o valor for null.
     * @param value
     * @return Float
     */
    public static Integer getInteger( Integer value ) {
        if ( value == null ) {
            return 0;
        }
        return value;
    }
    
}
