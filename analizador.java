import java.util.List;

public class Analizador{

	private String error = "tas bien wey";
    TipoToken typeToken;

    public Analizador(){
        error = "Syntax error at line: ";
    }

	public void DECLARATION(List<Token> tokens, int iterator){
        System.out.println("Declaration "+ tokens.get(iterator).tipo+"\n");
		switch(tokens.get(iterator).tipo){
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
            default:
                System.out.println("Error sintáctico, esperabamos una CLASE, FUNCION, VARIABLE o ID");
            break;
		}
	}
    //Metodo para cuando entramos en el caso de que sea CLASS_DECL****************************************************
    
    public void CLASE_DECL(List<Token> tokens, int iterator){
        System.out.println("CLASE_DECL "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case CLASE:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    CLASS_INHER(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.LLAVE_ABRE){
                        FUNCIONES(tokens,iterator+1);
                        if(tokens.get(++iterator).tipo!=TipoToken.LLAVE_CIERRA)
                            System.out.println("Error sintático, esperabamos una '}'");
                    }else{
                        System.out.println("Error sintático, esperabamos una '{'");
                    }

                }else
                    System.out.println("Error sintático, esperabamos un ID");
            default:
                System.out.println("Error sintático, esperrabamos una CLASE");
        }
        
    }
    //Metodo para cuando entramos en el caso de que sea CLASS_INHER***************************************************
    public void CLASS_INHER(List<Token> tokens, int iterator){
        System.out.println("CLASS_INHER "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case MENOR_QUE:
                if(tokens.get(++iterator).tipo!=TipoToken.ID){
                    System.out.println("Error sintático, esperabamos un ID");
                    break;
                }
            break;
            default:
                System.out.println("Error sintático, esperabamos un '<'");
            break;
        }
     }
     //Metodo para cuando entramos en el caso de que sea FUNCTIONS***************************************************
     public void FUNCIONES(List<Token> tokens, int iterator){
        System.out.println("funciones "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case FUNCION:
                FUNCION(tokens,iterator);
                FUNCIONES(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUNCTION***************************************************
    public void FUNCION(List<Token> tokens, int iterator){
        System.out.println("funcion "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case ID:
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                    PARAMETERS_OPC(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                        BLOCK(tokens,iterator+1);
                        
                    }else
                        System.out.println("Error sintático, esperebamos un ')'");
                }else
                    System.out.println("Error sintático, esperabamos un '('");
            break;
            default:
                System.out.println("Error sintático, esperabamos un ID");
            break;
        }
        
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS_OPC***************************************************
    public void PARAMETERS_OPC(List<Token> tokens, int iterator){
        System.out.println("par ops "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case ID:
                PARAMETERS(tokens,iterator);
            break;
        }
        
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS***************************************************
    public void PARAMETERS(List<Token> tokens, int iterator){
        System.out.println("parms "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case ID:
                PARAMETERS_2(tokens,iterator+1);
            break;
            default:
                System.out.println("Error sintático, esperabamos un ID");
            break;
        }
        
    }
    //Metodo para cuando entramos en el caso de que sea PARAMETERS2***************************************************
    public void PARAMETERS_2(List<Token> tokens, int iterator){
        System.out.println("parms 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case COMA:
                if(tokens.get(++iterator).tipo==TipoToken.ID)
                    PARAMETERS_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUN_DECL***************************************************
    public void FUN_DECL(List<Token> tokens, int iterator){
        System.out.println("fun decl "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case FUNCION:
                    FUNCION(tokens,iterator);
            break;
            default:
                System.out.println("Error sintático, esperabamos una FUNCION");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FUN_DECL***************************************************
    public void VAR_DECL(List<Token> tokens, int iterator){
        System.out.println("var dcl "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case VARIABLE:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    VAR_INIT(tokens,iterator+1);
                }else
                    System.out.println("Error sintático, esperabamos un ID");
            break;
            default:
                System.out.println("Error sintático, esperabamos una VARIABLE");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea VAR_INIT***************************************************
    public void VAR_INIT(List<Token> tokens, int iterator){
        System.out.println("var init "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case OPERADOR_ASIGNACION:
                EXPRESSION(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EXPRESSION***************************************************
    public void EXPRESSION(List<Token> tokens, int iterator){
        System.out.println("express  "+ tokens.get(iterator).tipo+"\n");
        ASSIGNMENT(tokens,iterator);
    }
    //Metodo para cuando entramos en el caso de que sea ASSIGNMENT***************************************************
    public void ASSIGNMENT(List<Token> tokens, int iterator){
        System.out.println("assigment "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case O:
                LOGIC_OR(tokens,iterator);
                ASSIGNMENT_OPC(tokens,iterator+1);
            break;
            default:
                System.out.println("Error sintático, esperabamos un O");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea ASSIGNMENT_OPC***************************************************
    public void ASSIGNMENT_OPC(List<Token> tokens, int iterator){
        System.out.println("assigment opc "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case OPERADOR_ASIGNACION:
                EXPRESSION(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_OR***************************************************
    public void LOGIC_OR(List<Token> tokens, int iterator){
        System.out.println("logic or "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case Y:
                LOGIC_AND(tokens,iterator);
                LOGIC_OR_2(tokens,iterator+1);
            break;
            default:
                System.out.println("Error sintático, esperabamos una Y");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_OR_2***************************************************
    public void LOGIC_OR_2(List<Token> tokens, int iterator){
        System.out.println("logic or 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case O:
                LOGIC_AND(tokens,iterator+1);
                LOGIC_OR_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_AND***************************************************
    public void LOGIC_AND(List<Token> tokens, int iterator){
        System.out.println("logic and "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case COMPARACION:
                EQUALITY(tokens,iterator);
                LOGIC_AND_2(tokens,iterator+1);
            break;
            default:
                System.out.println("Error sintático, esperabamos una COMPARACION");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea LOGIC_AND_2***************************************************
    public void LOGIC_AND_2(List<Token> tokens, int iterator){
        System.out.println("logic and 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case Y:
                EQUALITY(tokens,iterator+1);
                LOGIC_AND_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EQUALITY***************************************************
    public void EQUALITY(List<Token> tokens, int iterator){
        System.out.println("equal "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case COMPARACION:
                COMPARASION(tokens,iterator);
                EQUALITY_2(tokens,iterator+1);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EQUALITY_2***************************************************
    public void EQUALITY_2(List<Token> tokens, int iterator){
        System.out.println("equal 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
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
    public void COMPARASION(List<Token> tokens, int iterator){
        System.out.println("comparasion "+ tokens.get(iterator).tipo+"\n");
        TERM(tokens,iterator);
        COMPARASION_2(tokens,iterator+1);
        
    }
    //Metodo para cuando entramos en el caso de que sea COMPARASION_2***************************************************
    public void COMPARASION_2(List<Token> tokens, int iterator){
        System.out.println("comparasion 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
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
    public void TERM(List<Token> tokens, int iterator){
        System.out.println("term "+ tokens.get(iterator).tipo+"\n");
        FARCTOR(tokens,iterator);
        TERM_2(tokens,iterator+1);
    }
    //Metodo para cuando entramos en el caso de que sea TERM_2***************************************************
    public void TERM_2(List<Token> tokens, int iterator){
        System.out.println("term 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
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
    public void FARCTOR(List<Token> tokens, int iterator){
        System.out.println("factor "+ tokens.get(iterator).tipo+"\n");
        UNARY(tokens,iterator);
        FACTOR_2(tokens,iterator+1);
    }
    //Metodo para cuando entramos en el caso de que sea FACTOR_2***************************************************
    public void FACTOR_2(List<Token> tokens, int iterator){
        System.out.println("factor 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
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
     public void UNARY(List<Token> tokens, int iterator){
        System.out.println("unary "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case DISTINTO1:
                UNARY(tokens,iterator+1);
            break;
            case MENOS:
                UNARY(tokens,iterator+1);
            break;
            default://npi
                CALL(tokens,iterator);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CALL***************************************************
    public void CALL(List<Token> tokens, int iterator){
        System.out.println("call "+ tokens.get(iterator).tipo+"\n");
        PRIMARY(tokens,iterator);
        CALL_2(tokens,iterator);
    }
    //Metodo para cuando entramos en el caso de que sea CALL_2***************************************************
    public void CALL_2(List<Token> tokens, int iterator){
        System.out.println("call 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case PARENTESIS_ABRE:
                ARGUMENTS_OPC(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    CALL_2(tokens,iterator+1);
                }else
                    System.out.println("Error sintático, esperabamos un ')'");                
            break;
            case PUNTO:
                if(tokens.get(++iterator).tipo==TipoToken.ID){
                    CALL_2(tokens,iterator+1);
                }else
                    System.out.println("Error sintático, esperbamos un ID");                    
            break;
        }
    }

    public void ARGUMENTS_OPC(List<Token> tokens, int iterator){
        System.out.println("arguments opc "+ tokens.get(iterator).tipo+"\n");
        ARGUMENTS(tokens,++iterator);
    }
    public void ARGUMENTS(List<Token> tokens, int iterator){
        System.out.println("arguments "+ tokens.get(iterator).tipo+"\n");
        EXPRESSION(tokens,++iterator);
        ARGUMENTS_2(tokens,++iterator);
    }
    public void ARGUMENTS_2(List<Token> tokens, int iterator){
        System.out.println("arguments 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case COMA:
                EXPRESSION(tokens,++iterator);
                ARGUMENTS_2(tokens,++iterator);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea CALL_OPC***************************************************
    public void CALL_OPC(List<Token> tokens, int iterator){
        System.out.println("call opc "+ tokens.get(iterator).tipo+"\n");
        CALL(tokens,++iterator);
        if(tokens.get(++iterator).tipo==TipoToken.PUNTO){
            
        }else
            System.out.println("Error sintático, esperabamos un '.'");
        
    }
    //Metodo para cuando entramos en el caso de que sea PRIMARY***************************************************
    public void PRIMARY(List<Token> tokens, int iterator){
        System.out.println("primary "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case VERDADERO:
                
            break;
            case FALSO:
                
            break;
            case NULO:
                
            break;
            case ESTE:
                
            break;
            case NUMERO:
                
            break;
            case CADENA:
                
            break;
            case ID:
                
            break;
            case PARENTESIS_ABRE:
                EXPRESSION(tokens,iterator);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    
                }else{
                    System.out.println("Error sintático, esperabamos un ')'");
                }
            break;
            case SUPER:
                if(tokens.get(++iterator).tipo==TipoToken.PUNTO){
                    if(tokens.get(++iterator).tipo==TipoToken.ID){
                        
                    }else{
                        System.out.println("Error sintático, esperabamos un ID");
                    }
                }else{
                    System.out.println("Error sintático, ESPERABAMOS UN '.'");
                } 
            break;
            default:
                System.out.println("Error sintático, esperabamos un '('");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea STATEMENT***************************************************
    public void STATEMENT(List<Token> tokens, int iterator){
        System.out.println("statements "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case PARA:
                FOR_STMT(tokens,iterator);
            break;
            case SI:
                IF_STMT(tokens,iterator);
            break;
            case IMPRIMIR:
                PRINT_STMT(tokens,iterator);
            case RETORNAR:
                RETURN_STMT(tokens,iterator);
            break;
            case MIENTRAS:
                WHILE_STMT(tokens,iterator);
            break;
            case PARENTESIS_ABRE:
                BLOCK(tokens,iterator);
            break;
            default:
                EXPR_STMT(tokens,iterator);
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea EXPR_STMT***************************************************
    public void EXPR_STMT(List<Token> tokens, int iterator){
        System.out.println("expr stmt "+ tokens.get(iterator).tipo+"\n");
        EXPRESSION(tokens,iterator);
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT***************************************************
    public void FOR_STMT(List<Token> tokens, int iterator){
        System.out.println("for stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case PARA:
            if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                FOR_STMT_1(tokens,iterator+1);
                FOR_STMT_2(tokens,iterator+1);
                FOR_STMT_3(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    STATEMENT(tokens,iterator+1);
                }else
                    System.out.println("Error sintático, esperabamos un ')'");
            }else
                System.out.println("Error sintático, esperabamos un '('");                
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT1***************************************************
    public void FOR_STMT_1(List<Token> tokens, int iterator){
        System.out.println("for stmt 1 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case VARIABLE:
                  VAR_DECL(tokens,iterator);            
            break;
            case PUNTO_COMA:
                            
            break;
            default:
                EXPR_STMT(tokens,iterator);            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT2***************************************************
    public void FOR_STMT_2(List<Token> tokens, int iterator){
        System.out.println("for stmt 2 "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case PUNTO_COMA:
                            
            break;
            default:
                EXPRESSION(tokens,iterator);            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea FOR_STMT3***************************************************
    public void FOR_STMT_3(List<Token> tokens, int iterator){
        System.out.println("for stmt 3 "+ tokens.get(iterator).tipo+"\n");
        EXPRESSION(tokens,iterator);            
    }
    //Metodo para cuando entramos en el caso de que sea IF_STMT***************************************************
    public void IF_STMT(List<Token> tokens, int iterator){
        System.out.println("if stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case SI:
            if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                EXPRESSION(tokens,iterator+1);
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                    STATEMENT(tokens,iterator+1);
                    ELSE_STATEMENT(tokens,iterator+1);
                }else
                    System.out.println("Error sintático, esperabamos un ')'");
            }else
                System.out.println("Error sintático, esperabamos un '('");                
            break;
            default:
                System.out.println("Error sintático, esperabamos un SI");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea ELSE_STATEMENT***************************************************
    public void ELSE_STATEMENT(List<Token> tokens, int iterator){
        System.out.println("else stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case OTRO:
                STATEMENT(tokens,iterator+1);            
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea PRINT_STMT***************************************************
    public void PRINT_STMT(List<Token> tokens, int iterator){
        System.out.println("print stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case IMPRIMIR:
                EXPRESSION(tokens,iterator+1);   
                    if(tokens.get(++iterator).tipo==TipoToken.PUNTO_COMA)
                                 
            break;
            default:
                System.out.println("Error sintático, esperabamos un IMPRIMIR");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea RETURN_STMT***************************************************
    public void RETURN_STMT(List<Token> tokens, int iterator){
        System.out.println("return stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case RETORNAR:
                RETURN_EXP_OPC(tokens,iterator+1);     
            break;
            default:
                System.out.println("Error sintático, esperabamos un RETORNAR");
            break;
        }
    }
    //Metodo para cuando entramos en el caso de que sea RETURN_EXP_OPC***************************************************
    public void RETURN_EXP_OPC(List<Token> tokens, int iterator){
        System.out.println("return exp opc "+ tokens.get(iterator).tipo+"\n");
        EXPRESSION(tokens,iterator);     
    }
    //Metodo para cuando entramos en el caso de que sea WHILE_STMT***************************************************
    public void WHILE_STMT(List<Token> tokens, int iterator){
        System.out.println("while stmt "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case MIENTRAS:
                if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_ABRE){
                    EXPRESSION(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.PARENTESIS_CIERRA){
                        STATEMENT(tokens,iterator+1);
                    }else
                        System.out.println("Error sintático, esperabamos un ')'");
                }else
                System.out.println("Error sintático, esperabamos un '('");          
            break;
            default:
                System.out.println("Error sintático, esperabamos un MIENTRAS");
            break;
        }
    }
     //Metodo para cuando entramos en el caso de que sea BLOCK***************************************************
    public void BLOCK(List<Token> tokens, int iterator){
        System.out.println("bloque "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case LLAVE_ABRE:
                BLOCK_DECL(tokens,iterator+1);
                    if(tokens.get(++iterator).tipo==TipoToken.LLAVE_CIERRA){
                        
                    }else
                        System.out.println("Error sintático, esperabamos una '}'");        
            break;
            default:
                System.out.println("Error sintático, esperabamos una '{'");
            break;
        }
    }
     //Metodo para cuando entramos en el caso de que sea BLOCK_DECL***************************************************
    public void BLOCK_DECL(List<Token> tokens, int iterator){
        System.out.println("bloque decl "+ tokens.get(iterator).tipo+"\n");
        switch(tokens.get(iterator).tipo){
            case VARIABLE:
                DECLARATION(tokens,iterator);
                BLOCK_DECL(tokens,iterator+1);                   
            break;
        }
    }
}