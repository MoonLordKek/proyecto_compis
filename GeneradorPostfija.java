import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneradorPostfija {

    private final List<Token> infija;
    private final Stack<Token> pila;
    private final List<Token> postfija;

    public GeneradorPostfija(List<Token> infija) {
        this.infija = infija;
        this.pila = new Stack<>();
        this.postfija = new ArrayList<>();
    }

    public List<Token> convertir(){

        boolean estructuraDeControl = false;
        Stack<Token> pilaEstructurasDeControl = new Stack<>();

        for(int i=0; i<infija.size(); i++){
            Token t = infija.get(i);

            if(t.tipo == TipoToken.EOF){
                break;
            }

            if(t.esPalabraReservada()){
                //System.out.println("Reservada " +t.lexema);
                /*Si el token actual es una palabra reservada, se va directo a la lista de salida.*/
                postfija.add(t);
                if (t.esEstructuraDeControl()){
                    //System.out.println("\sEstructura de control");
                    estructuraDeControl = true;
                    pilaEstructurasDeControl.push(t);
                }
            }
            else if(t.esOperando()){
                //System.out.println("operando "+t.lexema);
                postfija.add(t);
            }
            else if(t.tipo == TipoToken.PARENTESIS_ABRE){
                //System.out.println("p abre "+t.lexema);
                pila.push(t);
            }
            else if(t.tipo == TipoToken.PARENTESIS_CIERRA){
                //System.out.println("p cierra "+t.lexema);
                while(!pila.isEmpty() && pila.peek().tipo != TipoToken.PARENTESIS_ABRE){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                if(pila.peek().tipo == TipoToken.PARENTESIS_ABRE){
                    pila.pop();
                }
                if(estructuraDeControl){
                    postfija.add(new Token(TipoToken.PUNTO_COMA, ";", null));
                }
            }
            else if(t.esOperador()){
                //System.out.println("operador "+t.lexema);
                while(!pila.isEmpty() && pila.peek().precedenciaMayorIgual(t)){
                    Token temp = pila.pop();
                    //System.out.println("precedenciaMayorIgual "+temp.lexema);
                    postfija.add(temp);
                }
                pila.push(t);
            }
            else if(t.tipo == TipoToken.PUNTO_COMA){
                //System.out.println("punto coma");
                //ignora { y (, en caso de que se trate de un argumento de un for
                while(!pila.isEmpty() && pila.peek().tipo != TipoToken.LLAVE_ABRE && pila.peek().tipo != TipoToken.PARENTESIS_ABRE ){
                    Token temp = pila.pop();
                    postfija.add(temp);
                    //System.out.println("\s"+temp.lexema + " " +temp.tipo);
                }
                postfija.add(t);
            }
            else if(t.tipo == TipoToken.LLAVE_ABRE){
                //System.out.println("llave abre");
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                pila.push(t);
            }
            else if(t.tipo == TipoToken.LLAVE_CIERRA && estructuraDeControl){
                //System.out.println("llave cierra");
                // Primero verificar si hay un else:
                if(i==infija.size()-1){//si no estamos al final entonces no puede haber un else
                    pila.pop();
                    postfija.add(new Token(TipoToken.PUNTO_COMA, ";", null));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    pilaEstructurasDeControl.pop();
                    if(pilaEstructurasDeControl.isEmpty()){
                        //System.out.println("Cierra estructura de control");
                        estructuraDeControl = false;
                    }

                }else{
                    if(infija.get(i + 1).tipo == TipoToken.OTRO){
                        // Sacar el "{" de la pila
                        pila.pop();
                    }
                    else{
                        // En este punto, en la pila sólo hay un token: "{"
                        // El cual se extrae y se añade un ";" a cadena postfija,
                        // El cual servirá para indicar que se finaliza la estructura
                        // de control.
                        pila.pop();
                        postfija.add(new Token(TipoToken.PUNTO_COMA, ";", null));

                        // Se extrae de la pila de estrucuras de control, el elemento en el tope
                        Token aux = pilaEstructurasDeControl.pop();
                        /*
                            Si se da este caso, es necesario extraer el IF de la pila
                            pilaEstructurasDeControl, y agregar los ";" correspondientes
                         */
                        if(aux.tipo == TipoToken.OTRO){
                            pilaEstructurasDeControl.pop();
                            postfija.add(new Token(TipoToken.PUNTO_COMA, ";", null));
                        }
                        if(pilaEstructurasDeControl.isEmpty()){
                            estructuraDeControl = false;
                        }

                    }
                }

            }
        }
        while(!pila.isEmpty()){
            Token temp = pila.pop();
            postfija.add(temp);
        }

        while(!pilaEstructurasDeControl.isEmpty()){
            pilaEstructurasDeControl.pop();
            postfija.add(new Token(TipoToken.PUNTO_COMA, ";", null));
        }

        return postfija;
    }

}
