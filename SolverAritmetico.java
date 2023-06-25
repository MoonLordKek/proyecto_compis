public class SolverAritmetico {

    private final Nodo nodo;
    private final TablaSimbolos tab;

    public SolverAritmetico(Nodo nodo,TablaSimbolos tab) {
        this.nodo = nodo;
        this.tab = tab;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        System.out.println("Solver aritmetico");
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.ID){
                return tab.obtener(n.getValue().lexema);
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
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
            if (n.getValue().tipo == TipoToken.MAS){
                // Ejecutar la concatenación
                    return "#";
            }
        }
        else{
            // Error por diferencia de tipos
            System.out.println("Error por diferencia de tipos");
        }

        return null;
    }

    public Object resolverLogica(Nodo n){

        return null;
    }
}
