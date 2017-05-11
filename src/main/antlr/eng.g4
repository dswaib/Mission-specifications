grammar eng;

@header {
	package co4robots.engparser;
}

eng : mitli | ltl;
mitli : 'mitli';

ltl : globallyp;			



	 

globallyp: universalityp ; 

universalityp:  'Globally, it is always the case that' event 'holds';
event: ID;

OP: '<' | '>' | '==' | '<=' | '>=';


TRUE: 'true';
FALSE: 'false';


COLON: ':';
SEMI: ';';
INT : ('0'..'9')+ ;
fragment ATOM: 'a'..'z';

fragment ATOMInit: 'a'..'z' | 'A'..'Z';

ID:  ATOMInit (ATOMInit | INT | '_' | '.')*;

NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t' | '\n')+ {skip();} ;
COMMENT: '#'~('\r' | '\n')* {skip();} ;
