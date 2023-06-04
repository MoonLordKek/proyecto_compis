public class analizador(){

	private String error = "tas bien wey";

	public String DECLARATION(List<Token> tokens, int iterator){
		switch(tokens(iterator).tipoToken){
			case CLASE:
				CLASE_DECL(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
			case FUNCION:
				FUNCION(tokens,iterator);
				DECLARATION(tokens,iterator+1);
			break;
			case VAR:
				DECLARATION(tokens,iterator+1);
			break;
			case STATEMENT:
				DECLARATION(tokens,iterator+1);
			break;
		}
	}

	public String CLASE_DECL(List<Token> tokens, int iterator){
		switch(tokens(iterator).tipoToken){
			case CLASE:
				if(tokens(++iterator).tipoToken==ID){
					CLASS_INHER(tokens,iterator+1);
					if(tokens(++iterator).tipoToken==LLAVE_ABRE){
						FUNCIONES(tokens,iterator+1);
						if(tokens(++iterator).tipoToken==LLAVE_CIERRA)
							return;
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

	public void CLASS_INHER((List<Token> tokens, int iterator){
		switch(tokens(iterator).tipoToken)
			case MENOR_QUE:
	}
}

