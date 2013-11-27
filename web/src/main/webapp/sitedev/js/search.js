// JavaScript Document
var maxlistings=5;
var index;
var listing;
var listingCount;
var resultsPage = 0;
var resultsNumPages = 3;
var resultsSearch;
var x;

function getURLParameter(name) {
  return decodeURI(
   (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
   );
}


function resultsFetch(resource){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"?pageNum="+ resultsPage +"&resultSize=5&search="+resultsSearch, function(data){       
		$('#result').html("");	
		$.each( data, function( key, val ){ 
			listingCount++;
			listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['description'] + "<br>" + data[key]['city'] + ", " + data[key]['state'];
			$('#result').append('<p>' + listing+ '</p>');
		});
		updateSearchPage();
	});

}


function updateSearchPage(){
	var searchPageText = "";	
	if(resultsPage > 0 || listingCount > 4) 
		searchPageText = "Page: ";
	if(resultsPage > 0 && resultsSearch=="") 
		searchPageText+= " <a href='?resultsPage=" + (parseInt(resultsPage)-1) + "'> Previous </a>";
	if(resultsPage > 0 && resultsSearch!="") 
		searchPageText+= " <a href='?search=" + resultsSearch + "&resultsPage=" + (parseInt(resultsPage)-1) + "'> Previous </a>";
	if(listingCount > 4 && resultsSearch=="") 
		searchPageText+= " <a href='?resultsPage=" + (parseInt(resultsPage)+1) + "'> Next </a>";
	if(listingCount > 4 && resultsSearch!="") 
		searchPageText+= " <a href='?search=" + resultsSearch + "&resultsPage=" + (parseInt(resultsPage)+1) + "'> Next </a>";
	// check for no records
	if(resultsPage == 0 && listingCount == 0) 
		searchPageText = "Sorry there are no records.";
	// check for no records on next page
	if(resultsPage > 0 && listingCount == 0) 
		searchPageText = "Sorry there are no additional Records.<br><br> Page: <a href='?resultsPage=" + (parseInt(resultsPage)-1) + "'> Previous </a>";
	
	$('#page-selector').html( searchPageText);
}










