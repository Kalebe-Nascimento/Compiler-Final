import java_cup.runtime.Symbol;

%%
%class scanner
%unicode

%cup
%line
%column

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

WhiteSpace = [ \t\f\r\n]
Digit = [0-9]
Number = [0-9]+ ("." [0-9]+)?
Letter = [a-zA-Z]
Special = [ "\@""\!""\#""\$""\%""\^""\&""\*""\(""\)""\/""\\""\.""\>""\<""\?""\;""\:""\-""\=""\-""\_""\+""\}""\~""\]""\[""\´""\`""\|""\'""\s" ]

ID = {Letter}({Letter}|{Digit}|"_")*
CHAR = "\'"({Letter} | {Number} | {Digit} | {Special})"\'"
STRING = "\""({Letter} | {Number} | {Digit} | {Special})*"\""

%%
// Relacional
"==" { return symbol(sym.EQUAL); }
">=" { return symbol(sym.B_EQUAL_THAN); }
"<=" { return symbol(sym.L_EQUAL_THAN); }
">" { return symbol(sym.B_THAN); }
"<" { return symbol(sym.L_THAN); }
"!=" { return symbol(sym.DIF); }

// Lógicos
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }
"!" { return symbol(sym.NEGATIVE); }
"||" { return symbol(sym.OR); }
"&&" { return symbol(sym.AND); }

// ESTRUTURAS
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"for" { return symbol(sym.FOR); }
"while" { return symbol(sym.WHILE); }
"function" { return symbol(sym.FUNCTION); }

// Blocos
"{" { return symbol(sym.START_BLOCK); }
"}" { return symbol(sym.END_BLOCK); }

// Interações
"printf" { return symbol(sym.PRINTF); }
"scanf" { return symbol(sym.SCANF); }

// Símbolos para declaração e atribuição de variáveis
"int" { return symbol(sym.T_INT); }
"real" { return symbol(sym.T_REAL); }
"string" { return symbol(sym.T_STRING); }
"char" { return symbol(sym.T_CARACTERE); }
{ID} { return symbol(sym.ID, yytext()); }
"=" { return symbol(sym.ASSIGNMENT); }

// Símbolos para contas, utilidades e tipos
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIVIDE); }
"%" { return symbol(sym.MOD); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
{Number} { return symbol(sym.NUMBER, new Double(yytext())); }
{CHAR} { return symbol(sym.CHAR, yytext()); }
{STRING} { return symbol(sym.STRING, yytext()); }
"," { return symbol(sym.COMMA); }
"&" { return symbol(sym.ADDRESS_SYMBOL); }


// Símbolos utilizados na construção
";" { return symbol(sym.SEMI); }

// IGNORA
{WhiteSpace} { /* ignore */ }
. { return symbol(sym.error, yytext()); }
