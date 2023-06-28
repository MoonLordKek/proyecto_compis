public class SolverAritmetico {

    private final Nodo nodo;
    private TablaSimbolos tab;

    public SolverAritmetico(Nodo nodo,TablaSimbolos tab) {
        this.nodo = nodo;
        this.tab = tab;
    }

    public void setTabla(TablaSimbolos tab){
        this.tab = tab;
    }

    public Object resolver(){
        return resolver(nodo);
    }

    public Object resolverLogica(){
        return resolverLogica(nodo);
    }
    private Object resolver(Nodo n){

        //System.out.println("Solver aritmetico " + n.getValue().lexema);

        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            //System.out.println("no tiene hijos: " + n.getValue().tipo);
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
                //System.out.println("literal "+ n.getValue().literal);
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.ID){
                if(tab.existeIdentificador(n.getValue().lexema)){
                    //System.out.println("obtener id: " + tab.obtener(n.getValue().lexema));
                    return tab.obtener(n.getValue().lexema);
                }else{
                    System.out.println("La variable '"+n.getValue().lexema+"' no ha sido declarada");
                    System.exit(0);
                }
            }else{
                System.out.println("Error por diferencia de tipos");
                System.exit(0);
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        //System.out.println("izq: "+ resultadoIzquierdo.getClass());
        //System.out.println("der: "+ resultadoDerecho.getClass());

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            //System.out.println("Son 2 numeros");
            switch (n.getValue().tipo){
                case MAS:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case MULTIPLICACION:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIVISION:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            //System.out.println("son 2 cadenas");
            if (n.getValue().tipo == TipoToken.MAS){
                // Ejecutar la concatenación
                    return (String) resultadoIzquierdo + (String)resultadoDerecho;
            }
        }
        else{
            // Error por diferencia de tipos
            System.out.println("Error por diferencia de tipos");
            System.exit(0);
        }

        return null;
    }

    private Object resolverLogica(Nodo n){
         //System.out.println("Solver lógico " + n.getValue().lexema);
         // No tiene hijos, es un operando
        if(n.getHijos() == null){
            //System.out.println("no tiene hijos: " + n.getValue().tipo);
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.VERDADERO || n.getValue().tipo == TipoToken.FALSO){
                //System.out.println("literal "+ n.getValue().literal);
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.ID){
                if(tab.existeIdentificador(n.getValue().lexema)){
                    //System.out.println("obtener id: " + tab.obtener(n.getValue().lexema));
                    return tab.obtener(n.getValue().lexema);
                }else{
                    System.out.println("La variable '"+n.getValue().lexema+"' no ha sido declarada");
                    System.exit(0);
                }
            }else{
                //System.out.println("Tipo incorrecto");
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = null;
        Object resultadoIzquierdo;
        Object resultadoDerecho=null;
        if(izq.getValue().esOperador() && izq.getValue().esOperadorL()){
            resultadoIzquierdo = resolverLogica(izq);
        }else{
            resultadoIzquierdo = resolver(izq);
        }

        if(n.getValue().tipo != TipoToken.DISTINTO1){
            der = n.getHijos().get(1); 
            if(der.getValue().esOperador() && der.getValue().esOperadorL()){
                resultadoDerecho = resolverLogica(der);
            }else{
                resultadoDerecho = resolver(der);
            }
        }

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch(n.getValue().tipo){
                case COMPARACION: 
                    return ((Double)resultadoIzquierdo == (Double) resultadoDerecho);
                case MENOR_QUE:
                    return ((Double)resultadoIzquierdo < (Double) resultadoDerecho);
                case MENOR_IGUAL:
                    return ((Double)resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYOR_QUE:
                    return ((Double)resultadoIzquierdo > (Double) resultadoDerecho);
                case MAYOR_IGUAL:
                    return ((Double)resultadoIzquierdo >= (Double) resultadoDerecho);
                case DISTINTO2:
                    return ((Double)resultadoIzquierdo != (Double) resultadoDerecho);
                case DISTINTO1:
                    
            }
        }else if(resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean){
            switch(n.getValue().tipo){
                case COMPARACION:
                    return ((Boolean)resultadoIzquierdo == (Boolean) resultadoDerecho);
                case DISTINTO1:
                    return ((Boolean)resultadoIzquierdo != (Boolean) resultadoDerecho);
                case Y:
                    return ((Boolean)resultadoIzquierdo && (Boolean) resultadoDerecho);
                case O:
                    return ((Boolean)resultadoIzquierdo ||(Boolean) resultadoDerecho);  
            }
        }else if(resultadoDerecho instanceof Double){
            return (Double)resultadoIzquierdo==0;
        }else if(resultadoIzquierdo instanceof Boolean){
            return !(Boolean)resultadoIzquierdo;
        }else{
            System.out.println("Error por diferencia de tipos"); 
            System.exit(0);
        }

        return null;
    }
}
