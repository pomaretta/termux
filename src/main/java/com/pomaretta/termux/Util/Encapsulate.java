/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Util
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2020-12-09

    DESCRIPTION
    
*/

package com.pomaretta.termux.Util;

/**
 * @author Carlos Pomares
 */

public class Encapsulate {

    /**
     *
     * Permite "encapsular", mostrar por pantalla la cadena de texto pasada
     * por parámetros de forma que se rodea por carácteres.
     *
     * @param toEncapsulate la cadena de texto que tendra en su interior.
     * @param escapeCharacter si necesitas poner carácteres de escape por ejemplo, \t
     */
    public static void encapsulateString(String toEncapsulate, String escapeCharacter){
        int lengthOfString = toEncapsulate.length();
        encapsulate(toEncapsulate,"-",escapeCharacter,lengthOfString);
    }

    public static void encapsulateString(String toEncapsulate, String escapeCharacter, String spaceCharacter){
        int lengthOfString = toEncapsulate.length();
        encapsulate(toEncapsulate,spaceCharacter,escapeCharacter,lengthOfString);
    }

    public static String inlineEncapsulate(String s, int lenght, int spacing){
        double firstStage = Math.ceil(((double)(lenght - s.length()) / 2)) - Math.ceil((double)spacing / 2);
        double secondStage = Math.floor(((double)(lenght - s.length()) / 2)) - Math.floor((double)spacing / 2);
        double firstSpacing = Math.ceil((double)spacing / 2);
        double secondSpacing = Math.floor((double)spacing / 2);
        return StringGenerator.generateStringByChar("-",firstStage)
                + StringGenerator.generateStringByChar(" ",firstSpacing)
                + s
                + StringGenerator.generateStringByChar(" ",secondSpacing)
                + StringGenerator.generateStringByChar("-",secondStage);
    }

    private static void encapsulate(String s, String caracters, String escape, int lengthOfString){

        /*
            Basado en fases, según en que fase se encuentre realizara un relleno o no.

            -- Primera fase
            Rellena el número x de carácteres "#"(por defecto), a la longitud de la cadena de texto pasada,
            mas un número de espacios.

            -- Segunda fase
            Rellena el primer e último carácter con una barra vertical |, dejando en su interior la cadena de texto.

            -- Tercera fase
            Rellena el mismo número de carácteres que en la primera fase.

         */

        boolean firstPhaseCheck = false, secondPhaseCheck = false;
        boolean stringCheck = false;

        for (int i = 0; i < ((lengthOfString + 1) * 3); i++) {

            // First phase
            if(!firstPhaseCheck){
                if(i == 0){
                    System.out.print("\n" + escape);
                }
                System.out.print(caracters);
                if(i == (lengthOfString + 1)){
                    firstPhaseCheck = true;
                }
            } else if(!secondPhaseCheck){
                if(i == (lengthOfString + 2)){
                    System.out.print("\n" + escape +"|");
                } else if(i == ((lengthOfString * 2) + 1)){
                    System.out.print("|");
                    secondPhaseCheck = true;
                } else if(!stringCheck){
                    System.out.print(s);
                    stringCheck = true;
                }
            } else {
                if(i == ((lengthOfString * 2) + 2)){
                    System.out.print("\n" + escape + caracters);
                }
                System.out.print(caracters);
            }
        }
    }

}