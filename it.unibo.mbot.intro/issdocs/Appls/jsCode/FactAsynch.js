/*
* =====================================
* FactAsynch.js
* =====================================
*/
factIter = function( n ){
var res = 1;	//ACCUMULATOR
 	for( i=n; i>=1; i-- ) res = res * i;
 	return "factIter of:" + n + " = " + res;
}
var curOutStr = "<font size='2'><pre></pre></font>";

function println( v ){
	showMsg( 'main', v ); 
}
function factAsynch( n ){
  	return factIterAsynch(n, 1, println);
}
function factIterAsynch( n, v, callback ){
var res = n*v;	//ACCUMULATOR
	callback("factIterAsynch of " + n + " v= " + v  + " res=" +  res);	
 	if( n <= 1 ){
  	 	return res;
	} else{ 
		return factIterAsynch(n-1, res, callback) ;
	}
	//else process.nextTick( function(){  factIterAsynch(n-1, res, callback) ; } );
}


 