// JavaScript Document
var maxlistings=5;
var index;
var listing;
var listingCount;
var resultsPage = 0;
var resultsNumPages = 3;
var resultsSearch;
var x;
var id;

function getURLParameter(name) {
  return decodeURI(
   (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
   );
}


function resultsFetch(resource){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"?pageNum="+ resultsPage +"&resultSize=5&search="+resultsSearch, function(data){       
		$('#result').html("");	
		listing = "";
		$.each( data, function( key, val ){ 
			listingCount++;
			
			if(resource == "job") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['description'] + "<br>" + data[key]['city'] + ", " + data[key]['state'];
			if(resource == "course") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['duration'];
			if(resource == "book") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['isbn'] + "<br>" + data[key]['publishDate'] + ", " + data[key]['publisher'];
			$('#result').append('<p>' + listing+ '</p>');
			if(resource == "user/current/recommend/job") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['description'] + "<br>" + data[key]['city'] + ", " + data[key]['state'];
			if(resource == "user/current/recommend/course") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['duration'];
			if(resource == "user/current/recommend/book") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['isbn'] + "<br>" + data[key]['publishDate'] + ", " + data[key]['publisher'];
			if(resource == "user") listing = "<div id='" + data[key]['id'] + "'>" + data[key]['email'] + "<br>" +data[key]['resume']['fileName'] +  "</div>";
		});
		updateSearchPage();
	});

}


function recommendedResultsFetch(resource){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"?pageNum=0&resultSize=1&search="+resultsSearch, function(data){       
		$('#recommendedresultresult').html("");	
		$.each( data, function( key, val ){ 
			listingCount++;
			if(resource == "user/current/recommend/job") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['description'] + "<br>" + data[key]['city'] + ", " + data[key]['state'];
			if(resource == "user/current/recommend/course") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['duration'];
			if(resource == "user/current/recommend/book") listing = "<strong>" + data[key]['title'] + "</strong><br>" + data[key]['isbn'] + "<br>" + data[key]['publishDate'] + ", " + data[key]['publisher'];
			$('#recommendedresult').append('<p>' + listing+ '</p>');
			
			if(resource == "user/current/recommend/job") $('#recommendedresult').append('<p><a href="recommendedjobs.html"> To see more recommended listings click here</a></p>');
			if(resource == "user/current/recommend/course") $('#recommendedresult').append('<p><a href="recommendedcourses.html"> To see more recommended listings click here</a></p>');
			if(resource == "user/current/recommend/book") $('#recommendedresult').append('<p><a href="recommendedbooks.html"> To see more recommended listings click here</a></p>');
			
		});
		updateSearchPage();
	});

}






function resultsManagerFetch(resource){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"?pageNum="+ resultsPage +"&resultSize=5&search="+resultsSearch, function(data){       
		$('#managelisting').html("");	
		$.each( data, function( key, val ){ 
			listingCount++;
			
			if(resource == "job") listing = "<div class='editable' id='" + data[key]['id'] + "'>" + data[key]['title'] + "<br>" +data[key]['city'] + ", " + data[key]['state'] + "</div><div><a href='managerresume.html?id="+ data[key]['id'] +"' >View Matching Resumes</a></div>";
			if(resource == "course") listing = "<div class='editable' id='" + data[key]['id'] + "'>" + data[key]['title'] + "<br>" +data[key]['duration'] +  "</div>";
			if(resource == "book") listing = "<div class='editable' id='" + data[key]['id'] + "'>" + data[key]['title'] + "<br>" +data[key]['publishDate'] +" "+ data[key]['publisher'] +"<br>" +data[key]['isbn'] +  "</div>";
			
			
			$('#managelisting').append( listing);
		});
		updateSearchPage();
	});

}

function resultsManagerResumeFetch(resource, id){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"/"+id+"/recommend/user?pageNum="+ resultsPage +"&resultSize=5&search="+resultsSearch, function(data){       
		$('#result').html("");	
		$.each( data, function( key, val ){ 
			listingCount++;
			
			listing = "<div id='" + data[key]['id'] + "'>" + data[key]['email'] + "<br>" +data[key]['resume']['fileName'] +  "</div>";
			
			
			
			$('#result').append( listing);
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



function loadEditorData(resource, id){
	listingCount=0;
	$.getJSON("/web-service/rest/"+resource+"/"+id, function(data){       
			
	$("#manageedit_id").val(id);
	$("#manageedit_title").val(data['title']);
	$("#manageedit_isbn").val(data['isbn']);
	$("#manageedit_price").val(data['price']);
	$("#manageedit_cost").val(data['cost']);
	$("#manageedit_publisher").val(data['publisher']);
	$("#manageedit_publishDate").val(data['publishDate']);

	$("#manageedit_duration").val(data['duration']);
	$("#manageedit_city").val(data['city']);
	$("#manageedit_state").val(data['state']);
	$("#manageedit_description").val(data['description']);
	$("#manageedit_active").prop( "checked", data['active'] );
	$("#managedelete").prop('disabled', false);
	$("#managesave").prop('disabled', false);
	});

}

function saveEditorData(resource){
	var id = $("#manageedit_id").val();
	var savedata = {};
	
	savedata['id'] = $("#manageedit_id").val();
	savedata['duration'] = $("#manageedit_duration").val();
	savedata['isbn'] = $("#manageedit_isbn").val();
	savedata['publisher'] = $("#manageedit_publisher").val();
	savedata['publishDate'] = $("#manageedit_publishDate").val();
	savedata['price'] = $("#manageedit_price").val();
	savedata['cost'] = $("#manageedit_cost").val();


	savedata['title'] = $("#manageedit_title").val();
	savedata['description'] = $("#manageedit_description").val();
	savedata['location'] = $("#manageedit_location").val();
	savedata['city'] = $("#manageedit_city").val();
	savedata['state'] = $("#manageedit_state").val();
	savedata['active'] = $("#manageedit_active").prop('checked');
	
	$.ajax({
    type: 'POST',
    url: "/web-service/rest/"+resource+"/"+id,
    data: JSON.stringify (savedata),
    success: function(data) { 
		if(resource=="job") $("#"+id).html(savedata['title'] + "<br>" + savedata['city'] + ", " +savedata['state']);
		if(resource=="course") $("#"+id).html(savedata['title'] + "<br>" + savedata['duration']);
		if(resource=="book") $("#"+id).html(savedata['title'] + "<br>" + savedata['publishDate'] + " " +savedata['publisher'] + "<br>" + savedata['isbn']);
		
	 },
	error: function(data){ alert( data['status'] + ": "+ data['statusText'] + "\n\n" + data['responseText'] + "" );  },
    contentType: "application/json",
    dataType: 'json'
});	
}


function saveasnewEditorData(resource){
	//var id = $("#manageedit_id").val();
	var savedata = {};
	
	//savedata['id'] = $("#manageedit_id").val();
	savedata['title'] = $("#manageedit_title").val();
	savedata['description'] = $("#manageedit_description").val();

	savedata['isbn'] = $("#manageedit_isbn").val();
	savedata['publisher'] = $("#manageedit_publisher").val();
	savedata['publishDate'] = $("#manageedit_publishDate").val();
	savedata['price'] = $("#manageedit_price").val();
	savedata['cost'] = $("#manageedit_cost").val();

	
	savedata['duration'] = $("#manageedit_duration").val();
	savedata['location'] = $("#manageedit_location").val();
	savedata['city'] = $("#manageedit_city").val();
	savedata['state'] = $("#manageedit_state").val();
	savedata['active'] = $("#manageedit_active").prop('checked');
	
	$.ajax({
    type: 'POST',
    url: "/web-service/rest/"+resource,
    data: JSON.stringify (savedata),
    success: function(data) { 
		//$("#"+id).html(savedata['title'] + "<br>" + savedata['city'] + ", " +savedata['state'])
	 },
	error: function(data){ alert( data['status'] + ": "+ data['statusText'] + "\n\n" + data['responseText'] + "" );  },
    contentType: "application/json",
    dataType: 'json'
});	
}

function deleteEditorData(resource){
	var id = $("#manageedit_id").val();
	var savedata = {};
	
	
	$.ajax({
    type: 'DELETE',
    url: "/web-service/rest/"+resource+"/"+id,
    data: JSON.stringify (savedata),
    error: function(data) { 
		$("#"+id).remove();
		$("#manageedit_title").val("");
		$("#manageedit_duration").val("");
		$("#manageedit_price").val("");
		$("#manageedit_cost").val("");
		$("#manageedit_isbn").val("");
		$("#manageedit_publisher").val("");
		$("#manageedit_publishDate").val("");
		
		$("#manageedit_description").val("");
		$("#manageedit_location").val("");
		$("#manageedit_city").val("");
		$("#manageedit_state").val("");
		$("#manageedit_active").prop('checked', false);
	 },
	contentType: "application/json",
    dataType: 'json'
});	
}
