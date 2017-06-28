$(function() {

	$("#save").click(function(e) {
		var username = $("input[name='reg_username']").val();
		var password = $("input[name='reg_password']").val();
		var role = $("input[name='role']").val();
		var name = $("input[name='name']").val();
		var gender = $("input[name='gender']").val();
		var phonenumber = $("input[name='phonenumber']").val();
		var address = $("input[name='address']").val();
		console.log(username, password, role, name, gender, phonenumber, address);

		var dataset = e.currentTarget.dataset;
		var id = dataset.id;	

		if (id != "") { 

		} else { // Register

		
			jQuery.ajax({
				url : 'addUserPro',
				processData : true,
				dataType : "text",
				data : {
					username : username,
					password : password,
					role : role,
					name : name,
					gender : gender,
					phonenumber : phonenumber,
					address : address
				},
				success : function(data) {
					console.log(data);
					if (data == "duplicate username"){
						bootbox.alert({
							message : 'Duplicate username! '
								+ 'PS: Please input another username.',
							callback : function() {
								location.reload();
							}
						});
					}
					else{
						bootbox.alert({
							message : 'Add Successfully! '
								+ 'PS: No change if foreign key does not exist!',
							callback : function() {
								location.reload();
							}
						});
					}

				}
			})
		}

		$('#modal').modal('hide');
	});
	
	$("#login").click(function(e) {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();		
		
		jQuery.ajax({
			url: 'Login' ,
			processData: true,
			dataType : "text",
			data: {
				username: username,
				password: password,
			},
			success : function(data) {
				console.log(data);
				if (data == "Customer"){
					window.location.href = "homePage";
				}
				else if(data == "Administrator"){
					window.location.href = "allUsersPro";
				}
				else{
					bootbox.alert({
						message : 'Password error! ',
						callback : function() {
							location.reload();
						}
					});				
				}
				
			}



		});
	});
	
	$("#test").click(function(e) {
		var id = $("input[name='username']").val();
		var title = $("input[name='password']").val();		
		console.log(id,title);
		jQuery.ajax({
			url: 'mongo' ,
			processData: true,
			dataType : "text",
			data: {
				id: id,
				title: title,
			},
			success : function(data) {
				alert("ok");
				console.log(data);
				
			}



		});
	});

	$(".delete").click(function(e) {
		bootbox.confirm({
			buttons : {
				confirm : {
					label : 'Delete'
				},
				cancel : {
					label : 'Cancel'
				}
			},
			message : 'Sure to delete?',
			callback : function(result) {
				if (result) {

					var dataset = e.currentTarget.dataset;
					var id = dataset.id;
					jQuery.ajax({
						url : 'deleteUserPro',
						processData : true,
						dataType : "text",
						data : {
							id : id
						},
						success : function(data) {
							console.log(id);
							bootbox.alert({
								message : 'Delete Successfully! '
									+ 'PS: No change if foreign key does not exist!',
								callback : function() {
									location.reload();
								}
							});
						}
					});

				}
			}
		});
	});

	$("#add").click(function(e) {
		$('#modalTitle').html("Add");

		$("input[name='username']").val("");
		$("input[name='password']").val("");
		$("input[name='role']").val("");
		$("input[name='name']").val("");
		$("input[name='gender']").val("");
		$("input[name='phonenumber']").val("");
		$("input[name='address']").val("");

		$("#save").attr("data-id", "");
		$('#modal').modal('show');
	});


});
