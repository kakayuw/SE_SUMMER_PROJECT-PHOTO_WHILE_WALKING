$(function() {
	
	$("#pay").click(function(e) {
		var add_date = new Date();
		var month = add_date.getMonth() + 1;
	    var currentdate = add_date.getFullYear() + "-" + month + "-" + add_date.getDate();
		jQuery.ajax({
			url : 'pay',
			processData : true,
			dataType : "text",
			data : {
				date : currentdate
			},
			success : function(data) {
				console.log(data);
				
				bootbox.alert({
					message : 'Pay Successfully!',
					callback : function() {
						location.reload();
					}
				});
			}
		});
	});
	
});
