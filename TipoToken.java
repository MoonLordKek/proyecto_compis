

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)

    ERROR,
    // Palabras clave:
    ID, Y, CLASE, ADEMAS, FALSO, PARA, FUNCION, SI, NULO, O, IMPRIMIR, RETORNAR, SUPER, ESTE, VERDADERO, VARIABLE, MIENTRAS, HACER,

    // CADENA Y NUMERO
    CADENA, NUMERO,

    // SIGNOS DEL LENGUAJE
    OPERADOR_ARITMETICO, OPERADOR_LOGICO, OPERADOR_ASIGNACION,CARACTERES_ESPECIALES, 
    PARENTESIS_ABRE, PARENTESIS_CIERRA, LLAVE_ABRE, LLAVE_CIERRA, COMA, PUNTO, PUNTO_COMA,

    // Final de cadena
    EOF
}
