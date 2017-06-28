$(function() {

	$(".toclick").click(function(e) {
		var amount = $("input[name='amount']").val();		
		var dataset = e.currentTarget.dataset;
		var bookid = dataset.id;
		var price = dataset.price;
		var title = dataset.title;
		console.log(bookid,amount,price,title);
	//	bootbox.alert({
	//		message : 'OK! ',
	//		callback : function() {
	//			location.reload();
	//		}
	//	});			
		jQuery.ajax({
			url: 'addtoShoppingcart' ,
			processData: true,
			dataType : "text",
			data: {
				bookid: bookid,
				price: price,
				amount:amount,
				title:title,
			},
			success : function(data) {
			//	console.log(data);
					bootbox.alert({
						message : 'Add to shopping cart success! ',
						callback : function() {
							location.reload();
						}
					});				

				
			}

		});   	
		
	});
	


});
