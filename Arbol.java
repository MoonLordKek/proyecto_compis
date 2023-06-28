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
        //System.out.println("\narbol\n");
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
                    //System.out.println(obj);
                break;
                case MENOR_QUE:
                case MENOR_IGUAL:
                case MAYOR_QUE:
                case MAYOR_IGUAL:
                case COMPARACION:
                case DISTINTO1:
                case DISTINTO2:
                case Y:
                case O:
                    solver = new SolverAritmetico(n,tab);
                    obj = solver.resolverLogica();
                    //System.out.println(obj);
                    break;
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
                            //System.out.println("hijo resuelto: " + obj);
                            tab.asignar(auxV1.getValue().lexema,obj);    
                        }else{
                            tab.asignar(auxV1.getValue().lexema,auxV2.getValue().literal);    
                        }
                    }else{
                        System.out.println("La variable "+n.getValue().lexema+" ya ha sido declarada");
                    }
                    break;
                case OPERADOR_ASIGNACION:
                    Nodo auxA1 = n.getHijos().get(0);
                    Nodo auxA2 = n.getHijos().get(1);
                    if(tab.existeIdentificador(auxA1.getValue().lexema)){
                        if(auxA2.getValue().esOperador()){
                            solver = new SolverAritmetico(auxA2,tab);
                            obj = solver.resolver();
                            //System.out.println("Asignar valor : "+obj);
                            tab.remover(auxA1.getValue().lexema);
                            tab.asignar(auxA1.getValue().lexema,obj);
                            //System.out.println("luego de asignar + " + tab.obtener(auxA1));
                        }else{
                            tab.remover(auxA1.getValue().lexema);
                            tab.asignar(auxA1.getValue().lexema,auxA2.getValue().literal);
                        }
                    }else{
                        System.out.println("La variable '"+auxA1.getValue().lexema+"' no ha sido declarada");
                    }
                    break;
                case SI:
                    controlSi(n);
                    break;
                case PARA:
                    //System.out.println("For:");
                    controlFor(n);
                    //System.out.println(t.lexema);
                    break;
                case IMPRIMIR:
                    imprimir(n);
                    break;
                case MIENTRAS:
                    controlWhile(n);
                    break;
                case HACER:
                    controlDoWhile(n);
                    break;
                default:
                    //System.out.println("deafult " + n.getValue().lexema);
                    raizAux = n.getHijos().get(0);
                    break;
            }
        }
    }

    public void recorrerAux(Nodo n){
        Object obj;
        SolverAritmetico solver;
        
        Token t = n.getValue();
        //System.out.println("\narbol aux "+t.tipo+"\n");
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case MULTIPLICACION:
                case DIVISION:
                    solver = new SolverAritmetico(n,tab);
                    obj = solver.resolver();
                    //System.out.println(obj);
                break;
                case MENOR_QUE:
                case MENOR_IGUAL:
                case MAYOR_QUE:
                case MAYOR_IGUAL:
                case COMPARACION:
                case DISTINTO1:
                case DISTINTO2:
                case Y:
                case O:
                    solver = new SolverAritmetico(n,tab);
                    obj = solver.resolverLogica();
                    //System.out.println(obj);
                break;
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
                            //System.out.println("hijo resuelto: " + obj);
                            tab.asignar(auxV1.getValue().lexema,obj);    
                        }else{
                            tab.asignar(auxV1.getValue().lexema,auxV2.getValue().literal);    
                        }
                    }else{
                        //System.out.println("La variable "+n.getValue().lexema+" ya ha sido declarada");
                    }
                    break;
                case OPERADOR_ASIGNACION:
                    Nodo auxA1 = n.getHijos().get(0);
                    Nodo auxA2 = n.getHijos().get(1);
                    if(tab.existeIdentificador(auxA1.getValue().lexema)){
                        if(auxA2.getValue().esOperador()){
                            solver = new SolverAritmetico(auxA2,tab);
                            obj = solver.resolver();
                            //System.out.println("Asignar valor : "+obj);
                            tab.remover(auxA1.getValue().lexema);
                            tab.asignar(auxA1.getValue().lexema,obj);
                            //System.out.println("luego de asignar + " + tab.obtener(auxA1));
                        }else{
                            tab.remover(auxA1.getValue().lexema);
                            tab.asignar(auxA1.getValue().lexema,auxA2.getValue().literal);
                        }
                    }else{
                        //System.out.println("La variable no ha sido declarada");
                    }
                    break;
                case SI:
                    //System.out.println(t.lexema);
                    controlSi(n);
                    break;
                case PARA:
                    //System.out.println("For:");
                    controlFor(n);
                    //System.out.println(t.lexema);
                    break;
                case IMPRIMIR:
                    imprimir(n);
                    break;
                case MIENTRAS:
                    controlWhile(n);
                    break;
                case HACER:
                    controlDoWhile(n);
                    break;
                default:
                    System.out.println("deafult " + t.lexema);
                    break;
            }
        
    }

    public void controlSi(Nodo n){
        List<Nodo> hijos = n.getHijos();
        SolverAritmetico solver = new SolverAritmetico(hijos.get(0),tab);
        Nodo instruccion;

        if((Boolean)solver.resolverLogica()){
            for (int j=1; j<hijos.size(); j++) {
                instruccion = hijos.get(j);
                //System.out.println("instrucciones if: " + instruccion.getValue().lexema); 
                if(instruccion.getValue().tipo!=TipoToken.OTRO){
                    recorrerAux(instruccion);
                }
            }
        }else{
            instruccion = hijos.get(hijos.size()-1);
            //System.out.println("otro: " + instruccion.getValue().lexema);
            if(instruccion.getValue().tipo==TipoToken.OTRO){
                controlOtro(instruccion);
            }
        }
    }

    public void controlOtro(Nodo n){
        List<Nodo> hijos = n.getHijos();
        SolverAritmetico solver = new SolverAritmetico(hijos.get(0),tab);
        for(Nodo instruccion : hijos){
            recorrerAux(instruccion);
        }
    }

    public void controlWhile(Nodo n){
        List<Nodo> hijos = n.getHijos();
        SolverAritmetico solver = new SolverAritmetico(hijos.get(0),tab);
        int j = 0;
        //System.out.println(solver.resolverLogica());
        while((Boolean)solver.resolverLogica()) {
            solver.setTabla(tab);
            for(j=1; j<hijos.size();j++){
                //System.out.println(hijos.get(j).getValue().tipo);
                recorrerAux(hijos.get(j));    
            }
            //System.out.println("Resolviendo logica kek");
        }
    }

    public void controlDoWhile(Nodo n){
        List<Nodo> hijos = n.getHijos();
        int c = hijos.size();
        SolverAritmetico solver = new SolverAritmetico(hijos.get(c-1).getHijos().get(0),tab);
        int j = 0;
        //System.out.println(solver.resolverLogica());
        do {
            solver.setTabla(tab);
            for(j=0; j<hijos.size()-1;j++){
                //System.out.println(hijos.get(j).getValue().tipo);
                recorrerAux(hijos.get(j));    
            }
            //System.out.println("iterando ando");
        }while((Boolean)solver.resolverLogica());
    }

    public void controlFor(Nodo n){
        List<Nodo> hijos = n.getHijos();
        int len = hijos.size();
        SolverAritmetico solver;
        /*
        suponiendo que tiene un hijo tendríamos una estructura de cualquiera del siguiente tipo
            for( ; ; )   for( var a = b ; ; )   for( ;a<b; )    for( ; ; a=a+b)
                stmt;
        stmt puede tomar la forma de 2 de los 3 tipos (declaracion,asignacion)
        y el árbol generado será el mismo para esos casos, i.e. no es posible diferenciarlos.
        por simplicidad, si hay un solo hijo, se ejecturán los casos en que este no sea del mismo tipo que los 2 mencionados

        Suponiendo que hay 2 hijos, puede haber una combinación de 2 de los 3 argumentos de for, 
        el problema que se presentan con el caso de un hijo puede repetirse
            for(var a = b; a<b ; )   for(var a = b ; ; a=a+b)    for( ;a<b; a=a+b)    

            for(var a = b ; ;)     for( ;a<b;)     for( ; ; a=a+b) 
                stmt;               stmt;               stmt;
            for( ; ; )
                stmt1; stmt2;
        
        Suponiendo 3 hijos
            
        */
        switch(len){
            case 1:
                switch(hijos.get(0).getValue().tipo){
                    //en caso de asignación o declaración se ignorará
                    case VARIABLE:
                    case OPERADOR_ASIGNACION:
                        break;
                    //en caso de operador lógico, entonces se iterará
                    case MAYOR_QUE:
                    case MENOR_QUE:
                    case MAYOR_IGUAL:
                    case MENOR_IGUAL:
                    case COMPARACION:
                    case DISTINTO1:
                    case DISTINTO2:
                        solver = new SolverAritmetico(hijos.get(0),tab);
                        while((Boolean)solver.resolverLogica()){}
                    break;
                    default:
                        while(true){
                            recorrerAux(hijos.get(0));    
                        }
                        
                }
            break;
            case 2:
                switch(hijos.get(0).getValue().tipo){
                    case VARIABLE:
                    case OPERADOR_ASIGNACION:
                        recorrerAux(hijos.get(0));
                        switch(hijos.get(1).getValue().tipo){
                            case MAYOR_QUE:
                            case MENOR_QUE:
                            case MAYOR_IGUAL:
                            case MENOR_IGUAL:
                            case COMPARACION:
                            case DISTINTO1:
                            case DISTINTO2:
                                solver = new SolverAritmetico(hijos.get(1),tab);
                                while((Boolean)solver.resolverLogica()){}
                            break;
                            default:
                                while(true){recorrerAux(hijos.get(1));}
                        }
                    break;
                    //en caso de operador lógico, entonces se iterará
                    case MAYOR_QUE:
                    case MENOR_QUE:
                    case MAYOR_IGUAL:
                    case MENOR_IGUAL:
                    case COMPARACION:
                    case DISTINTO1:
                    case DISTINTO2:
                        solver = new SolverAritmetico(hijos.get(0),tab);
                        while((Boolean)solver.resolverLogica()){
                            System.out.println("for 2 args, log, smtelse: "+ hijos.get(1).getValue().lexema);
                            recorrerAux(hijos.get(1));
                        }
                    break;
                    default:
                        while(true){
                            for(Nodo k : hijos){
                                recorrerAux(k);
                            }
                        }
                }
            break;
            //3 o más hijos sigue la misma lógica
            default:
                switch(hijos.get(0).getValue().tipo){
                    case VARIABLE:
                    case OPERADOR_ASIGNACION:
                        recorrerAux(hijos.get(0));
                        switch(hijos.get(1).getValue().tipo){
                            case MAYOR_QUE:
                            case MENOR_QUE:
                            case MAYOR_IGUAL:
                            case MENOR_IGUAL:
                            case COMPARACION:
                            case DISTINTO1:
                            case DISTINTO2:
                                solver = new SolverAritmetico(hijos.get(1),tab);
                                while((Boolean)solver.resolverLogica()){
                                    for(int k = 2;k<hijos.size();k++){
                                        recorrerAux(hijos.get(k));
                                    }
                                }
                            break;
                            default:
                                while(true){recorrerAux(hijos.get(1));}
                        }
                    break;
                    //en caso de operador lógico, entonces se iterará
                    case MAYOR_QUE:
                    case MENOR_QUE:
                    case MAYOR_IGUAL:
                    case MENOR_IGUAL:
                    case COMPARACION:
                    case DISTINTO1:
                    case DISTINTO2:
                        solver = new SolverAritmetico(hijos.get(0),tab);
                        while((Boolean)solver.resolverLogica()){
                            for(int k = 1;k<hijos.size();k++){
                                recorrerAux(hijos.get(k));
                            }
                        }
                    break;
                    default:
                        while(true){
                            for(Nodo k : hijos){
                                recorrerAux(k);
                            }
                        }
                }
            break;
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
                //System.out.println("id: ");
                Object obj = tab.obtener(t.lexema);
                System.out.println(obj);
            }else{
                //System.out.println("no id:");
                System.out.println(t.literal);    
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

