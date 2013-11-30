// JavaScript Document

function writeHeader() {

    var manStatus = true;

	$('#header').html('<img src="img/logo.png" width="197" height="89" alt=""/><p class="rurse">RURSE </p>');
	$('#header').append('<p>  <a href="resume.html">resume</a> <a href="jobs.html">jobs</a> <a href="courses.html">courses</a> <a href="books.html">books</a> </p>');
	
	$.getJSON( "/web-service/rest/user/current", function(data){
		if( data['manager'] == true ){
			//alert( data['manager'] );
			$('#header').append('<p> <a href="managerjobs.html">manager mode</a> </p>');
		}
	});
	
}


function writeIndexHeader() {

    var manStatus = true;

	$('#header').html('<img src="img/logo.png" width="197" height="89" alt=""/><p class="rurse">RURSE </p>');
	$('#header').append('<p>  <a href="resume.html">resume</a> <a href="jobs.html">jobs</a> <a href="courses.html">courses</a> <a href="books.html">books</a> </p>');
	
	
	
}


function writeManagerHeader() {

    var manStatus = true;

	$('#header').html('<img src="img/logo.png" width="197" height="89" alt=""/><p class="rurse">RURSE </p>');
	$('#header').append('<p>  <a href="managerresume.html">resume</a> <a href="managerjobs.html">jobs</a> <a href="managercourses.html">courses</a> <a href="managerbooks.html">books</a> </p>');
	
	$.getJSON( "/web-service/rest/user/current", function(data){
		if( data['manager'] == true ){
			//alert( data['manager'] );
			$('#header').append('<p> <a href="resume.html">user mode</a> </p>');
		}
	});
	
}







// userType(userId) - check and return if the user is a manager.  Do not pass argument if checking current user.
					  // this function should only be used for single requests as it may be resource intensive.

function isManager(userId) {
	var resource;
	var v = "y";
	
	//userId = typeof userId !== 'undefined' ? userId : 0;
	
	if(userId>0){
		resource = "/web-service/rest/user/" + userId;
	} else {
		resource = "/web-service/rest/user/current";
	}
	//alert(resource);
	$.getJSON( resource, function(data){
		
		//v = data['manager'];
		//alert (data['manager']);
		
		});
		
		return( v );
	
}