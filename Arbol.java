import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;
    TablaSimbolos tab;
    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void setTabla(TablaSimbolos tab){
        this.tab=tab;
    }

    public Nodo getRaiz(){
        return this.raiz;
    }

    public void recorrer(){
        Object obj;
        SolverAritmetico solver;
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
                    solver = new SolverAritmetico(n,tab);
                    obj = solver.resolver();
                    System.out.println(obj);
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
                    //System.out.println("crear variable");
                    // Crear una variable. Usar tabla de simbolos
                    Nodo auxV1 = n.getHijos().get(0);
                    Nodo auxV2 = n.getHijos().get(1);
                    //System.out.println("\shijo 1: "+auxV1.getValue().tipo +" "+ auxV1.getValue().lexema);
                    //System.out.println("\shijo 1: "+auxV2.getValue().tipo +" "+ auxV2.getValue().lexema);
                    if(!tab.existeIdentificador(auxV1.getValue().lexema)){
                        if(auxV2.getValue().esOperador()){

                            solver = new SolverAritmetico(auxV2,tab);
                            obj = solver.resolver();
                            System.out.println("hijo resuelto: " + obj);
                            tab.asignar(auxV1.getValue().lexema,obj);    
                        }else{
                            tab.asignar(auxV1.getValue().lexema,auxV2.getValue().literal);    
                        }
                    }else{
                        System.out.println("La variable "+n.getValue().lexema+" ya ha sido declarada");
                    }
                    break;
                case SI:
                    System.out.println(t.lexema);
                    break;
                case PARA:
                    System.out.println("For:");
                    controlFor(n);
                    //System.out.println(t.lexema);
                    break;
                case IMPRIMIR:
                    imprimir(n);
                    break;
                default:
                    System.out.println("deafult " + n.getValue().lexema);
                    raizAux = n.getHijos().get(0);
                    break;
            }
        }
    }

    public void controlFor(Nodo n){
        //tiene al menos 3 hijos, el primero será 
        int i = 0;
        for ( ;i<1 ;i++ ) {//este tipo de for se comporta como un while(1)
            System.out.println("ejemplo for " +i );
        }
    }

    public void imprimir(Nodo n){
        Nodo hijos = n.getHijos().get(0);
        Token t = hijos.getValue();
        SolverAritmetico solver = new SolverAritmetico(hijos,tab);
        if(t.esOperador()){
            Object res = solver.resolver();
            System.out.println(res);
        }else if(t.esOperando()){
            if(t.tipo==TipoToken.ID){
                System.out.println("id: ");
                Object obj = tab.obtener(t.lexema);
                System.out.println(obj);
            }else{
                System.out.println("no id:");
                System.out.println(t.lexema);    
            }
        }
    }
    
    public void imprimirArbol(Nodo hijo,int i){
        int a = 1;
        if(hijo.getHijos()==null){
            System.out.println(i +"."+a+ " final: "+hijo.getValue().lexema);
        }else{
            for(Nodo n : hijo.getHijos()){
                System.out.println(i +"."+a+" hijo: "+hijo.getValue().lexema);
                a++;
                imprimirArbol(n,i+1);
            }   
            //System.out.println(hijo.getValue().lexema);
        }
        
    }
}

