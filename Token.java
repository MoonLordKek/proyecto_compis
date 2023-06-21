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
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VARIABLE:
            case SI:
            case IMPRIMIR:
            case OTRO:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case OTRO:
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
                return 3;
            case MAS:
            case MENOS:
                return 2;
            case OPERADOR_ASIGNACION:
                return 1;
            case MAYOR_QUE:
            case MAYOR_IGUAL:
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
                return 2;
        }
        return 0;
    }

}


