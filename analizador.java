import java.util.List;

public class Analizador{

    private String kek = "";
	private String error = "tas bien wey";
    TipoToken tipoToken;

    public Analizador(){
        error = "tas bien wey";
    }

	public String DECLARATION(List<Token> tokens, int iterator){
        System.out.println("pruebas kek: " + tipoToken);
		switch(tipoToken){
			case CLASE:
				CLASE_DECL(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
			case FUNCION:
				FUN_DECL(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
			case VARIABLE:
                VAR_DECL(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
			case ID:
                STATEMENT(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
		}
        
	}
    //Metodo para cuando entramos en el caso de que sea CLASS_DECL****************************************************
    
    public String CLASE_DECL(List<Token> tokens, int iterator){
        switch(tipoToken){
            case CLASE:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    CLASS_INHER(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.LLAVE_ABRE){
                        FUNCIONES(tokens,iterator+1);
                        if(tokens.get(++iterator).tipo==TipoToken.LLAVE_CIERRA)
                            return "correcto";
                        else
                            return error;
                    }else{
                        return error;
                    }

                }else
                    return error;
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CLASS_INHER***************************************************
    public void CLASS_INHER(List<Token> tokens, int iterator){
        switch(tipoToken){
            case MENOR_QUE:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    return;
                }
            break;
        }
     }
     //Metodo para cuando entramos en el caso de que sea FUNCTIONS***************************************************
     public String FUNCIONES(List<Token> tokens, int iterator){
        switch(tipoToken){
            case FUNCION:
                FUNCION(tokens,iterator);
                FUNCIONES(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUNCTION***************************************************
    public String FUNCION(List<Token> tokens, int iterator){
        switch(tipoToken){
            case ID:
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                    PARAMETERS_OPC(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                        BLOCK(tokens,iterator+1);
                        return "correcto";
                    }else
                        return error;
                }else
                    return error;
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS_OPC***************************************************
    public String PARAMETERS_OPC(List<Token> tokens, int iterator){
        switch(tipoToken){
            case ID:
                PARAMETERS(tokens,iterator);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS***************************************************
    public String PARAMETERS(List<Token> tokens, int iterator){
        switch(tipoToken){
            case ID:
                PARAMETERS_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS2***************************************************
    public String PARAMETERS_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case COMA:
                if(tokens.get(++iterator).tipo==TipoToken.ID)
                    PARAMETERS_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUN_DECL***************************************************
    public String FUN_DECL(List<Token> tokens, int iterator){
        switch(tipoToken){
            case FUNCION:
                    FUNCION(tokens,iterator);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUN_DECL***************************************************
    public String VAR_DECL(List<Token> tokens, int iterator){
        switch(tipoToken){
            case VARIABLE:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    VAR_INIT(tokens,iterator+1);
                }else
                    return error;
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea VAR_INIT***************************************************
    public String VAR_INIT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case OPERADOR_ASIGNACION:
                EXPRESSION(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EXPRESSION***************************************************
    public String EXPRESSION(List<Token> tokens, int iterator){
        switch(tipoToken){
            case OPERADOR_ASIGNACION:
                ASSIGNMENT(tokens,iterator);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea ASSIGNMENT***************************************************
    public String ASSIGNMENT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case O:
                LOGIC_OR(tokens,iterator);
                ASSIGNMENT_OPC(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea ASSIGNMENT_OPC***************************************************
    public String ASSIGNMENT_OPC(List<Token> tokens, int iterator){
        switch(tipoToken){
            case OPERADOR_ASIGNACION:
                EXPRESSION(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_OR***************************************************
    public String LOGIC_OR(List<Token> tokens, int iterator){
        switch(tipoToken){
            case Y:
                LOGIC_AND(tokens,iterator);
                LOGIC_OR_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_OR_2***************************************************
    public String LOGIC_OR_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case O:
                LOGIC_AND(tokens,iterator+1);
                LOGIC_OR_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_AND***************************************************
    public String LOGIC_AND(List<Token> tokens, int iterator){
        switch(tipoToken){
            case COMPARACION:
                EQUALITY(tokens,iterator);
                LOGIC_AND_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_AND_2***************************************************
    public String LOGIC_AND_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case Y:
                EQUALITY(tokens,iterator+1);
                LOGIC_AND_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EQUALITY***************************************************
    public String EQUALITY(List<Token> tokens, int iterator){
        switch(tipoToken){
            case COMPARACION:
                COMPARASION(tokens,iterator);
                EQUALITY_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EQUALITY_2***************************************************
    public String EQUALITY_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case DISTINTO2:
                COMPARASION(tokens,iterator+1);
                EQUALITY_2(tokens,iterator+1);
            break;
            case COMPARACION:
                COMPARASION(tokens,iterator+1);
                EQUALITY_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea COMPARASION***************************************************
    public String COMPARASION(List<Token> tokens, int iterator){
        switch(tipoToken){
            case TERMINO:
                TERM(tokens,iterator);
                COMPARASION_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea COMPARASION_2***************************************************
    public String COMPARASION_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case MAYOR_QUE:
                TERM(tokens,iterator+1);
                COMPARASION_2(tokens,iterator+1);
            break;
            case MAYOR_IGUAL:
                TERM(tokens,iterator+1);
                COMPARASION_2(tokens,iterator+1);
            break;
            case MENOR_QUE:
                TERM(tokens,iterator+1);
                COMPARASION_2(tokens,iterator+1);
            break;
            case MENOR_IGUAL:
                TERM(tokens,iterator+1);
                COMPARASION_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea TERM***************************************************
    public String TERM(List<Token> tokens, int iterator){
        switch(tipoToken){
            case FACTOR:
                FARCTOR(tokens,iterator);
                TERM_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea TERM_2***************************************************
    public String TERM_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case MENOS:
                FARCTOR(tokens,iterator+1);
                TERM_2(tokens,iterator+1);
            break;
            case MAS:
                FARCTOR(tokens,iterator+1);
                TERM_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FACTOR***************************************************
    public String FARCTOR(List<Token> tokens, int iterator){
        switch(tipoToken){
            case UNARY:
                UNARY(tokens,iterator);
                FACTOR_2(tokens,iterator+1);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FACTOR_2***************************************************
    public String FACTOR_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case DIVISION:
                UNARY(tokens,iterator+1);
                FACTOR_2(tokens,iterator+1);
            break;
            case MULTIPLICACION:
                UNARY(tokens,iterator+1);
                FACTOR_2(tokens,iterator+1);
            break;
        }
    }
     //Metodo para cuando entramos en el caso de que sea UNARY***************************************************
     public String UNARY(List<Token> tokens, int iterator){
        switch(tipoToken){
            case OPERADOR_LOGICO:
                UNARY(tokens,iterator+1);
            break;
            case MENOR:
                UNARY(tokens,iterator+1);
            break;
            case CALL:
                CALL(tokens,iterator);
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CALL***************************************************
    public String CALL(List<Token> tokens, int iterator){
        switch(tipoToken){
            case PRIMARY:
                PRIMARY(tokens,iterator);
                CALL_2(tokens,iterator);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CALL_2***************************************************
    public String CALL_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case PARENTESIS_ABRE:
                ARGUMENTS_OPC(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    CALL_2(tokens,iterator+1);
                }else
                    return error;                
            break;
            case PUNTO:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    CALL_2(tokens,iterator+1);
                }else
                    return error;                    
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CALL_OPC***************************************************
    public String CALL_OPC(List<Token> tokens, int iterator){
        switch(tipoToken){
            case CALL:
                if(tokens.get(++iterator).tipo==TipoToken.PUNTO){
                    return;
                }else
                    return error;                
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PRIMARY***************************************************
    public String PRIMARY(List<Token> tokens, int iterator){
        switch(tipoToken){
            case VERDADERO:
                return "VERDADERO";                
            break;
            case FALSO:
                return "FALSO";                
            break;
            case NULO:
                return "NULL";                
            break;
            case ESTE:
                return "THIS";                
            break;
            case NUMERO:
                return "NUMBER";                
            break;
            case CADENA:
                return "STRING";                
            break;
            case ID:
                return "ID";                
            break;
            case PARENTESIS_ABRE:
                if(tokens.get(++iterator).tipo==TipoToken.EXPRESSION){
                    EXPRESSION(tokens,iterator);
                    if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                        return tokens.get(iterator).lexema;
                    }else{
                        return error;
                    }
                }else{
                    return error;
                }             
            break;
            case SUPER:
                if(tokens.get(++iterator).tipo==TipoToken.PUNTO){
                    if(tokens.get(++iterator).tipo==TipoToken.ID){
                        return "SUPER . ID";
                    }else{
                        return error;
                    }
                }else{
                    return error;
                } 
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea STATEMENT***************************************************
    public String STATEMENT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case EXPR:
                EXPR_STMT(tokens,iterator);
            case PARA:
                FOR_STMT(tokens,iterator);
            break;
            case SI:
                IF_STMT(tokens,iterator);
            break;
            case IMPRIMIR:
                PRINT_STMT(tokens,iterator);
            case REGRESAR:
                RETURN_STMT(tokens,iterator);
            break;
            case MIENTRAS:
                WHILE_STMT(tokens,iterator);
            break;
            case BLOCK:
                BLOCK(tokens,iterator);
            break;
             DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EXPR_STMT***************************************************
    public String EXPR_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case EXPRESSION:
                  EXPRESSION(tokens,iterator);            
            break;
             DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT***************************************************
    public String FOR_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case PARA:
            if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                FOR_STMT_1(tokens,iterator+1);
                FOR_STMT_2(tokens,iterator+1);
                FOR_STMT_3(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    STATEMENT(tokens,iterator+1);
                }else
                    return error;
            }else
                return error;                
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT1***************************************************
    public String FOR_STMT_1(List<Token> tokens, int iterator){
        switch(tipoToken){
            case VARIABLE:
                  VAR_DECL(tokens,iterator);            
            break;
            case EXPR_STMT:
                EXPR_STMT(tokens,iterator);            
            break;
            case PUNTO_COMA:
                return;            
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT2***************************************************
    public String FOR_STMT_2(List<Token> tokens, int iterator){
        switch(tipoToken){
            case EXPRESSION:
                EXPRESSION(tokens,iterator);            
            break;
            case PUNTO_COMA:
                return;            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT3***************************************************
    public String FOR_STMT_3(List<Token> tokens, int iterator){
        switch(tipoToken){
            case EXPRESSION:
                EXPRESSION(tokens,iterator);            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea IF_STMT***************************************************
    public String IF_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case SI:
            if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                EXPRESSION(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    STATEMENT(tokens,iterator+1);
                    ELSE_STATEMENT(tokens,iterator+1);
                }else
                    return error;
            }else
                return error;                
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea ELSE_STATEMENT***************************************************
    public String ELSE_STATEMENT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case OTRO:
                STATEMENT(tokens,iterator+1);            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PRINT_STMT***************************************************
    public String PRINT_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case IMPRIMIR:
                EXPRESSION(tokens,iterator+1);   
                    if(tokens.get(++iterator).tipo==TipoToken.PUNTO_COMA)
                        return;         
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea RETURN_STMT***************************************************
    public String RETURN_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case RETORNAR:
                RETURN_EXP_OPC(tokens,iterator+1);     
            break;
            DEFAULT:
                return error;
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea RETURN_EXP_OPC***************************************************
    public String RETURN_EXP_OPC(List<Token> tokens, int iterator){
        switch(tipoToken){
            case EXPRESSION:
                EXPRESSION(tokens,iterator);     
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea WHILE_STMT***************************************************
    public String WHILE_STMT(List<Token> tokens, int iterator){
        switch(tipoToken){
            case MIENTRAS:
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                    EXPRESSION(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                        STATEMENT(tokens,iterator+1);
                    }else
                        return error;
                }else
                return error;          
            break;
            DEFAULT:
                return error;
            break;
        }
    }
     //Metodo para cuando entramos en el caso de que sea BLOCK***************************************************
    public String BLOCK(List<Token> tokens, int iterator){
        switch(tipoToken){
            case LLAVE_ABRE:
                BLOCK_DECL(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.LLAVE_CIERRA){
                        return;
                    }else
                        return error;        
            break;
            DEFAULT:
                return error;
            break;
        }
    }
     //Metodo para cuando entramos en el caso de que sea BLOCK_DECL***************************************************
    public String BLOCK_DECL(List<Token> tokens, int iterator){
        switch(tipoToken){
            case VARIABLE:
                DECLARATION(tokens,iterator);
                BLOCK_DECL(tokens,iterator+1);                   
            break;
        }
    }
    
}