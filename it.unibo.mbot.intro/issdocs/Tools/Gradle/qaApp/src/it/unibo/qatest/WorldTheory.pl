%-------------------------------------------------
%  FACTS ABOUT THE WORLD of a QActor  
%-------------------------------------------------
p(1).

hellosSentence("from the world Theory").

test :-
	stdout <- println(  "hello from world theory" ). 
	
 
%==============================================
% GUARD-RELATED RULES
%==============================================
addRule( Rule ) :-
	stdout <- println( addRule( Rule ) ),
	assert( Rule ).	
removeRule( Rule ) :-
	retract( Rule ),
	!.
removeRule( A  ) :- 
	stdout <- println( remove(A) ),
	retract( A :- B ).
	