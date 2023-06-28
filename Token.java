public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }

    public String toString(){
        return tipo + " " + lexema + " " + literal;
    }

    public boolean esOperando(){
        switch (this.tipo){
            case ID:
            case NUMERO:
            case CADENA:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case MULTIPLICACION:
            case DIVISION:
            case OPERADOR_ASIGNACION:
            case MAYOR_QUE:
            case MAYOR_IGUAL:
            case MENOR_QUE:
            case MENOR_IGUAL:
            case Y:
            case O:
                return true;
            default:
                return false;
        }
    }
    public boolean esOperadorL(){
        switch (this.tipo){
            case MAYOR_QUE:
            case MAYOR_IGUAL:
            case MENOR_QUE:
            case MENOR_IGUAL:
            case DISTINTO1:
            case DISTINTO2:
            case Y:
            case O:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case CLASE:
            case ADEMAS:
            case FALSO:
            case PARA:
            case SI:
            case OTRO:
            case NULO:
            case IMPRIMIR:
            case RETORNAR:
            case SUPER:
            case ESTE:
            case VERDADERO:
            case VARIABLE:
            case MIENTRAS:
            case HACER:
            case FUNCION:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case OTRO:
            case PARA:
            case MIENTRAS:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case MULTIPLICACION:
            case DIVISION:
                return 7;
            case MAS:
            case MENOS:
                return 6;
            case MAYOR_QUE:
            case MAYOR_IGUAL:
            case MENOR_IGUAL:
            case MENOR_QUE:
                return 5;
            case DISTINTO1:
            case DISTINTO2:
                return 4;
            case Y:
                return 3;
            case O:
                return 2;
            case OPERADOR_ASIGNACION:
                return 1;
            
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case MULTIPLICACION:
            case DIVISION:
            case MAS:
            case MENOS:
            case OPERADOR_ASIGNACION:
            case MAYOR_QUE:
            case MAYOR_IGUAL:
            case MENOR_QUE:
            case MENOR_IGUAL:
            case O:
            case Y:
                return 2;
        }
        return 1;
    }

}


