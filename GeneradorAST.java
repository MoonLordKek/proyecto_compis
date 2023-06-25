import java.util.List;
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;
    private final Stack<Nodo> pila;

    public GeneradorAST(List<Token> postfija){
        this.postfija = postfija;
        this.pila = new Stack<>();
    }

    public Arbol generarAST() {
        Stack<Nodo> pilaPadres = new Stack<>();
        Nodo raiz = new Nodo(new Token(TipoToken.SUPER,"raiz"));
        pilaPadres.push(raiz);

        Nodo padre = raiz;
        int i = 0;

        for(Token t : postfija){
            i++;
            if(pilaPadres.peek().getValue()!=null){
                //System.out.println("\npilapadres:  "+pilaPadres.peek().getValue().lexema);
            }

            if(t.tipo == TipoToken.EOF){
                break;
            }

            if(t.esPalabraReservada()){
                //System.out.println(i+" palabra reservada "+t.lexema);

                Nodo n = new Nodo(t);

                padre = pilaPadres.peek();
                padre.insertarSiguienteHijo(n);
                
                pilaPadres.push(n);
                padre = n;

            }
            else if(t.esOperando()){
                //System.out.println(i+" operando "+t.lexema);

                Nodo n = new Nodo(t);
                pila.push(n);
            }
            else if(t.esOperador()){
                //System.out.println(i+" operador "+t.lexema);
                
                int aridad = t.aridad();
                Nodo n = new Nodo(t);
                for(int j=1; j<=aridad; j++){
                    Nodo nodoAux = pila.pop();
                    n.insertarHijo(nodoAux);
                    //System.out.println("usando operadores :" +nodoAux.getValue().lexema);
                }
                pila.push(n);
            }
            else if(t.tipo == TipoToken.PUNTO_COMA){
                //System.out.println(i+" punto coma "+t.lexema);
                if (pila.isEmpty()){
                    /*
                    Si la pila esta vacía es porque t es un punto y coma que cierra una estructura de control
                     */
                    pilaPadres.pop();
                    padre = pilaPadres.peek();
                }
                else{
                    Nodo n = pila.pop();
                    //System.out.println("punto coma no es final: "+n.getValue().lexema);
                    if(padre.getValue().tipo == TipoToken.VARIABLE){
                        
                        /*En el caso del VAR, es necesario eliminar el igual que
                        pudiera aparecer en la raíz del nodo n.
                         */
                        if(n.getValue().tipo == TipoToken.OPERADOR_ASIGNACION){
                            padre.insertarHijos(n.getHijos());
                        }
                        else{
                            padre.insertarSiguienteHijo(n);
                        }
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else if(padre.getValue().tipo == TipoToken.IMPRIMIR){
                        padre.insertarSiguienteHijo(n);
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else {
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        //Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz);

        return programa;
    }
}
