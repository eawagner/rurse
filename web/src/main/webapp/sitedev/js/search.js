// JavaScript Document
var maxlistings=5;
var index;
var listing;
var resultsPage = 1;
var resultsNumPages = 3;
var resultsSearch;
var x;

function getURLParameter(name) {
  return decodeURI(
   (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
   );
}

function resultsFetch(){
		var listingCount=0;

	$.getJSON("http://54.200.170.49:8080/web-service/rest/job?pageNum="+ resultsPage +"&resultSize=5&search="+resultsSearch, function(data){
        
		$('#result').html("");
		
		$.each( data, function( key, val ){ 
			listingCount++ 
		
			listing = key +":"+ val;
		
			$('#result').append('<p>' + listing+ '</p>');
			listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['description'] + "<br>" + data[key]['city'] + ", " + data[key]['state'];
			$('#result').append('<p>' + listing+ '</p>');
		
		});
		
		//for(index=0;index<listingCount;index++){
			
			
			//	listing = "<strong>" +data[index]['title'] + "</strong><br>" + data[index]['description'] + "<br>" + data[index]['city'] + ", " + data[index]['state'] ;
			//	$('#result').append('<p>' + listing+ '</p>');
		//}
		updateSearchPage();
	});

}


function updateSearchPage(){
	var searchPageText = "Page ";
	
	for(x=1; x<=resultsNumPages; x++){
		if(x==resultsPage){
			searchPageText+= " " + x.toString();
		} else {
			searchPageText+= " <a href='?resultsPage=" + x.toString() + "'>" + x.toString() + "</a>";
		}
	}
	$('#page-selector').html( searchPageText);
}
