import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public Nodo getRaiz(){
        return this.raiz;
    }

    public void recorrer(){
        TablaSimbolos tab = new TablaSimbolos();
        System.out.println("\narbol\n");
        Nodo raizAux = raiz;
        for(Nodo n : raizAux.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case MULTIPLICACION:
                case DIVISION:
                    SolverAritmetico solver = new SolverAritmetico(n,tab);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                // Comparaciones
                /*
                case MENOR_QUE:
                case MENOR_IGUAL:
                case MAYOR_QUE
                case MAYOR_IGUAL:
                case COMPARACION:
                case DISTINTO1:
                case DISTINTO2:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolverLogica();
                    System.out.println(res);
                break;*/
                case VARIABLE:
                    System.out.println("crear variable");
                    // Crear una variable. Usar tabla de simbolos
                    Nodo aux1 = n.getHijos().get(0);
                    Nodo aux2 = n.getHijos().get(1);
                    if(!tab.existeIdentificador(aux1.getValue().lexema)){
                        tab.asignar(aux1.getValue().lexema,aux1.getValue().literal);
                    }else{
                        System.out.println("La variable "+n.getValue().lexema+" ya ha sido declarada");
                    }
                    break;
                case SI:
                    System.out.println(t.lexema);
                    break;
                case FUNCION:
                    System.out.println("crear variable");
                    // Crear una variable. Usar tabla de simbolos
                    Nodo aux1 = n.getHijos().get(0);
                    Nodo aux2 = n.getHijos().get(1);
                    if(!tab.existeIdentificador(aux1.getValue().lexema)){
                        tab.asignar(aux1.getValue().lexema,aux1.getValue().literal);
                    }else{
                        System.out.println("La variable "+n.getValue().lexema+" ya ha sido declarada");
                    }
                    System.out.println(t.lexema);
                    break;
                case IMPRIMIR:
                    System.out.println(n.getHijos().get(0).getValue().lexema);
                    break;
                default:
                    System.out.println("deafult " + n.getValue().lexema);
                    raizAux = n.getHijos().get(0);
                    break;
            }
        }
    }

    public void imprimirArbol(Nodo hijo){
        if(hijo.getHijos()==null){
            System.out.println("final: "+hijo.getValue().lexema);
        }else{
            for(Nodo n : hijo.getHijos()){
                System.out.println("hijo: "+hijo.getValue().lexema);
                imprimirArbol(n);
            }   
            System.out.println(hijo.getValue().lexema);
        }
        
    }
}

