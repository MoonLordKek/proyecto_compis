

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("Y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUNCION); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("otro", TipoToken.OTRO);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("O", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VARIABLE); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
        palabrasReservadas.put("hacer", TipoToken.HACER);
    }

    private static final Map<String, TipoToken> simbolosDelSistema;
    static{
        simbolosDelSistema = new HashMap<>();
        simbolosDelSistema.put("(",TipoToken.PARENTESIS_ABRE);
        simbolosDelSistema.put(")",TipoToken.PARENTESIS_CIERRA);
        simbolosDelSistema.put("{",TipoToken.LLAVE_ABRE);
        simbolosDelSistema.put("}",TipoToken.LLAVE_CIERRA);
        simbolosDelSistema.put(",",TipoToken.COMA);
        simbolosDelSistema.put(".",TipoToken.PUNTO);
        simbolosDelSistema.put(";",TipoToken.PUNTO_COMA);
        simbolosDelSistema.put("-",TipoToken.MENOS);
        simbolosDelSistema.put("+",TipoToken.MAS);
        simbolosDelSistema.put("*",TipoToken.MULTIPLICACION);
        simbolosDelSistema.put("/",TipoToken.DIVISION);
        simbolosDelSistema.put("!",TipoToken.DISTINTO1);
        simbolosDelSistema.put("!=",TipoToken.DISTINTO2);
        simbolosDelSistema.put("=",TipoToken.OPERADOR_ASIGNACION);
        simbolosDelSistema.put("==",TipoToken.COMPARACION);
        simbolosDelSistema.put("<",TipoToken.MENOR_QUE);
        simbolosDelSistema.put("<=",TipoToken.MENOR_IGUAL);
        simbolosDelSistema.put(">",TipoToken.MAYOR_QUE);
        simbolosDelSistema.put(">=",TipoToken.MAYOR_IGUAL);
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        String token = "";
        String car;
        int i = 0;
        while(i < source.length()){
            car = source.substring(i,i+1);
            //System.out.println("\n"+car+"out of "+source.length()+": " + i);

            if(car.equals(String.valueOf('"'))){//detecta cadenas
                token = "";
                do{
                    i++;
                    car = source.substring(i,i+1);
                    token+=car;
                }while(!(car.equals(String.valueOf('"'))));
                tokens.add(new Token(TipoToken.CADENA,'"'+token.substring(0,token.length()-1)+'"',token.substring(0,token.length()-1)));
            }else if(car.charAt(0)==47){
                //System.out.println("soy un / : "+i);
                i++;
                car =source.substring(i,i+1);
                if(car.charAt(0)==47){//comentario de linea
                    while(car.charAt(0)!=10 && i<source.length()){//nada
                        //System.out.println("Comentario");
                        i++;
                        car =source.substring(i,i+1);
                    }
                }else if(car.charAt(0)==42){
                    //System.out.println("comentario largo  "+(source.substring(i,i+1)).charAt(0)+" kek\n");
                    i++;
                    car = source.substring(i,i+1);
                    while(car.charAt(0)!=42 && (source.substring(i+1,i+2)).charAt(0)!=47 && i<source.length()){
                        //System.out.println("datos  "+(source.substring(i+1,i+2)).charAt(0)+"\n");
                        i++;
                        car =source.substring(i,i+1);
                    }
                    i++;
                    car = source.substring(i,i+1);
                }else{
                    car = source.substring(i-1,i);
                    //System.out.println("no es comentario: "+car+" i:"+i+"\n");
                    tokens.add(new Token(simbolosDelSistema.get(car),car));
                    i--;
                }
            }else if (simbolosDelSistema.containsKey(car)) {//detecta símbolos del sistema
                //System.out.println("\nsimbolo del sistema: " + car);
                
                if ((i+1)<source.length()){
                    token = car+source.substring(i+1,i+2);
                    if(simbolosDelSistema.containsKey(token)){
                        tokens.add(new Token(simbolosDelSistema.get(token),token));
                        i++;
                    }else{
                        tokens.add(new Token(simbolosDelSistema.get(car),car));
                    }
                }else{
                    tokens.add(new Token(simbolosDelSistema.get(car),car));
                }

            }else if (((car.charAt(0) < 123 && car.charAt(0)>96) || (car.charAt(0) < 91 && car.charAt(0)>64))) { //detecta identificadores o palabras reservadas
                token="";
                //System.out.println("soy una ide\n");
                while(!car.equals(" ") && !simbolosDelSistema.containsKey(car) && i<source.length() && car.charAt(0)!=10){//cualquier caracter a excepción de un espacio, salto de linea o un simbolo del sistema
                    //System.out.println("\ncadena no cadena: "+car+" "+i);
                    token+=car;
                    i++;
                    if((i)==source.length()){
                        break;
                    }else{
                        car = source.substring(i,i+1);
                    }
                } i--;
                if(palabrasReservadas.containsKey(token)){
                    //System.out.println("token_palabra: "+ token+"\n");
                    if(palabrasReservadas.get(token)==TipoToken.VERDADERO){
                        tokens.add(new Token(palabrasReservadas.get(token),token,true));    
                    }else if(palabrasReservadas.get(token)==TipoToken.FALSO){
                        tokens.add(new Token(palabrasReservadas.get(token),token,false));    
                    }else{
                        tokens.add(new Token(palabrasReservadas.get(token),token));    
                    }
                }else{
                    //System.out.println("token: "+ token+"1\n");
                    tokens.add(new Token(TipoToken.ID,token));    
                }
                
            }else if(car.charAt(0) < 58 && car.charAt(0)>47){
                token="";
                do{
                    //System.out.println("soy un numero: "+ car + " i: "+i +"\n");
                    if ((i+1)<source.length()){
                        i++;
                        token += car;
                    }else{
                        token += car;
                        break;
                    }
                    //puede haber un error por aquí
                    car = source.substring(i,i+1);
                    if (((car.charAt(0) < 58 && car.charAt(0)>47) || car.charAt(0)==46 )) {
                        //System.out.println(" el siguiente también es un número: "+car);
                    }else{
                        i--;
                    }
                }while((car.charAt(0) < 58 && car.charAt(0)>47 || car.charAt(0)==46 ) && i<source.length()) ;
                tokens.add(new Token(TipoToken.NUMERO,token,Double.parseDouble(token)));    
            }else if((car.charAt(0) == 10)){ //niu lain
                linea++;
            }else if((car.charAt(0) == 32)){
                //espacio Xd
                linea++;
            }else{//exception???
                /*
                token = "erro";
                List<Token> err = new ArrayList<>();
                err.add(new Token(TipoToken.ERROR,token));
                return err;*/
            }
            i++;
            //System.out.println("Value of i at the end: "+i);
        }
        return tokens;
    }
}


// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */