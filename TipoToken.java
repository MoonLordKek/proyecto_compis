public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)

    ERROR,
    // Palabras clave:
    ID, Y, CLASE, ADEMAS, FALSO, PARA, FUNCION, SI,OTRO, NULO, O, IMPRIMIR, RETORNAR, SUPER, ESTE, VERDADERO, VARIABLE, MIENTRAS, HACER,

    // CADENA Y NUMERO
    CADENA, NUMERO,

    // SIGNOS DEL LENGUAJE
    OPERADOR_ARITMETICO,MAS,MENOS,DIVISION,MULTIPLICACION,OPERADOR_ASIGNACION,CARACTERES_ESPECIALES,
    DISTINTO1,DISTINTO2, COMPARACION,MENOR_QUE,MENOR_IGUAL,MAYOR_QUE,MAYOR_IGUAL,
    PARENTESIS_ABRE, PARENTESIS_CIERRA, LLAVE_ABRE, LLAVE_CIERRA, COMA, PUNTO, PUNTO_COMA,

    // Final de cadena
    EOF
}
