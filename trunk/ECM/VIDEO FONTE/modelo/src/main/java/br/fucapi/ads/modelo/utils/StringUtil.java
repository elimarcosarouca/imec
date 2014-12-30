package br.fucapi.ads.modelo.utils;

import java.text.Normalizer;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Classe utilitaria de strings.
 */
public class StringUtil {

    /** Constant para spa�o em branco. */
    public static final String SPACE = " ";

    /**
     * Construtor privado.
     */
    private StringUtil() {
    }

    /**
     * Tira todos os acentos de um texto.
     * 
     * @param texto
     * @return
     */
    public static String tiraAcentos( String texto ) {
        texto = texto.replaceAll( "\\�", "a" );
        texto = texto.replaceAll( "\\�", "a" );
        texto = texto.replaceAll( "\\�", "a" );
        texto = texto.replaceAll( "\\�", "a" );
        texto = texto.replaceAll( "\\�", "e" );
        texto = texto.replaceAll( "\\�", "e" );
        texto = texto.replaceAll( "\\�", "i" );
        texto = texto.replaceAll( "\\�", "o" );
        texto = texto.replaceAll( "\\�", "o" );
        texto = texto.replaceAll( "\\�", "o" );
        texto = texto.replaceAll( "\\�", "u" );
        return texto;
    }

    /**
     * M�todo utilizado para remover acentos.
     * 
     * @param nome
     * @return
     */
    public static String removerAcento(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
    
    /**
     * Identifica se a string nao é nula e nem vazia.
     * 
     * @param str
     * @return
     */
    public static boolean notEmpty( String str ) {
        return ( ( str != null ) && ( !"".equals( str.trim() ) ) );
    }

    /**
     * Verifica se as duas strings sao iguais, ja valiando valores nulos.
     * 
     * @param tx1
     * @param tx2
     * @return
     */
    public static boolean isSameString( String tx1, String tx2 ) {
        if ( StringUtil.notEmpty( tx1 ) ) {
            return tx1.equalsIgnoreCase( tx2 );
        } else if ( StringUtil.notEmpty( tx2 ) ) {
            return false;
        }
        return true;

    }

    /**
     * Remove todos os caracteres especiais da string.
     * 
     * @param tx
     * @return
     */
    public static String toAscii( String tx ) {
        tx = StringUtil.tiraAcentos( tx );
        tx = tx.replaceAll( "\\�", "c" );
        return tx;
    }

    /**
     * Retorna a string com o tamanho limitado de acordo com o valor informado.
     * 
     * @param texto
     * @param tamanho
     * @return String
     */
    public static String limitarTamanho( String texto, int tamanho ) {
        final int tamReservadoReticencia = 3;
        if ( texto.length() > tamanho ) {
            return texto.substring( 0, ( tamanho - tamReservadoReticencia ) ) + "...";
        }
        return texto;
    }

    /**
     * Retorna a string com o tamanho limitado de acordo com o valor informado.
     * 
     * @param texto
     * @param tamanho
     * @return String
     */
    public static String limitarTamanhoSimples( String texto, int tamanho ) {
        if ( texto.length() > tamanho ) {
            return texto.substring( 0, ( tamanho ) );
        }
        return texto;
    }

    /**
     * Retorna String com apenas a primeira letra em mai�sculo. Ex: RICARDO DA COSTA -> Ricardo da Costa
     * 
     * @param line
     * @return String
     */
    public static String toUpperJustFirstChar( String line ) {
        StringTokenizer tok = new StringTokenizer( line );

        StringBuffer res = new StringBuffer();

        while ( tok.hasMoreElements() ) {
            String tmp = tok.nextToken();
            tmp = tmp.toLowerCase();
            if ( tmp.length() > 2 ) {
                String part1 = tmp.substring( 0, 1 ).toUpperCase();
                String part2 = tmp.substring( 1, tmp.length() );
                tmp = part1 + part2;
            }
            res.append( tmp );
            if ( tok.hasMoreElements() ) {
                res.append( " " );
            }
        }
        return res.toString();
    }

    /**
     * Transforma os caracteres especiais em hexadecimal para serem visualizados no html.
     * 
     * @param tx
     * @return
     */
    public static String escapeHtml( String tx ) {
        StringBuilder builder = new StringBuilder();
        int charAtual = 0;
        final int proximoElemento = 1;
        final int elementoAnterior = -1;
        for ( Character c : tx.toCharArray() ) {
            if ( Character.getType( c ) == Character.MATH_SYMBOL ) {
                // Caso seja o sinal de menor, verificar se nao ta abrindo uma tag.
                if ( c == '<' ) {
                    if ( ( ( charAtual + proximoElemento ) < tx.length() )
                            && StringUtil.isLetter( tx.charAt( charAtual + proximoElemento ) ) ) {
                        builder.append( c );
                    } else {
                        builder.append( StringEscapeUtils.escapeHtml( c.toString() ) );
                    }
                } else if ( c == '>' ) {
                    // Caso seja o sinal de menor, verificar se nao ta fechando uma tag.
                    if ( ( ( charAtual + elementoAnterior ) < tx.length() )
                            && !StringUtil.isLetter( tx.charAt( charAtual + elementoAnterior ) ) ) {
                        builder.append( c );
                    } else {
                        builder.append( StringEscapeUtils.escapeHtml( c.toString() ) );
                    }
                } else {
                    builder.append( c );
                }
            } else {
                builder.append( c );
            }
            charAtual++;
        }
        return builder.toString();
    }

    /**
     * Converte o texto para o padrao LATIN1.
     * 
     * @param tx
     * @return
     */
    public static String toLatin1( String tx ) {
        if ( tx == null ) {
            return null;
        }

        return tx.replaceAll( "\\�", "+" ).replaceAll( "�", "\"" ).replaceAll( "�", "\"" ).replaceAll( "�", "\"" );
    }

    /**
     * Transforma todas as acentuações do html no padrao ascii.
     * 
     * @return
     */
    public static String getAcentosFromHtmlToAscii( String texto ) {
        return texto.replaceAll( "\\&oacute;", "ó" ).replaceAll( "\\&aacute;", "á" ).replaceAll( "\\&eacute;", "é" )
                .replaceAll( "\\&iacute;", "í" ).replaceAll( "\\&uacute;", "ú" ).replaceAll( "\\&ccedil;", "ç" )
                .replaceAll( "\\&atilde;", "ã" ).replaceAll( "\\&otilde;", "õ" );
    }

    /**
     * Identifica se o character � uma letra.
     * 
     * @param c
     * @return
     */
    private static boolean isLetter( char c ) {
        return Character.isLetter( c );
    }

    /**
     * Recupera a string representada pelo objeto informado, ou "", caso o objeto esteje nulo.
     * 
     * @param ob
     * @return Stirng
     */
    public static String getObjectAsString( Object ob ) {
        if ( ob != null ) {
            return ob.toString();
        }
        return "";
    }

    /**
     * Retorna um vetor de strings da string informada fazendo split utilizando os espa�oes em branco da mesma.
     * 
     * @param str
     * @return String[]
     */
    public static String[] getStringsAsArray( String str ) {
        return str.split( StringUtil.SPACE );
    }
    
    /**
     * Metodo utilizado para retirar de um texto todas tags html.
     * @param value
     * @return
     */
    public static String retirarHtml( String value ) {
        if ( value != null ) {
            value = value.replaceAll( "\\<.*?\\>", "" );
            value = value.replaceAll( "&nbsp;", "" );
            value = value.replaceAll( "&#32;", "" );
        }
        return value;
    }
    
    /**
     * Metodo utilizado para retirar acentuacao.
     * 
     * @return
     */
    public static String getTextoSemAcento( String str ) {
        String input = str;
        input = Normalizer.normalize( input, Normalizer.Form.NFD );
        input = input.replaceAll( "[^\\p{ASCII}]", "" );
        return input;
    }
}