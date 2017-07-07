$(function() {

	$("#save").click(function(e) {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();
		var email = $("input[name='email']").val();
		var phone = $("input[name='phone']").val();

		console.log(username, password, email, phone);

		var dataset = e.currentTarget.dataset;
		var id = dataset.id;

		if (id != "") { // Edit
			jQuery.ajax({
				url : 'user_updateUser',
				processData : true,
				dataType : "text",
				data : {
					uid : id,
					username : encodeURIComponent(username),
					password : password,
					email : email,
					phone : phone
				},
				success : function(data) {
					console.log(id);
					bootbox.alert({
						message : 'Modify Successfully! '
							+ 'PS: No change if foreign key does not exist!',
						callback : function() {
							location.reload();
						}
					});
				}
			});
		} else { // Add
			jQuery.ajax({
				url : 'user_addUser',
				processData : true,
				dataType : "text",
				data : {
					uid : id,
					username : username,
					password : password,
					email : email,
					phone : phone
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
						url : 'user_deleteUser',
						processData : true,
						dataType : "text",
						data : {
							uid : id
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
		$("input[name='email']").val("");
		$("input[name='phone']").val("");

		$("#save").attr("data-id", "");
		$('#modal').modal('show');
	});
	

	$(".edit").click(function(e) {
		$('#modalTitle').html("Edit");
		var dataset = e.currentTarget.dataset;
		var id = dataset.id;
		console.log(id);

		$("input[name='username']").val(dataset.username);
		$("input[name='password']").val(dataset.password);
		$("input[name='email']").val(dataset.email);
		$("input[name='phone']").val(dataset.phone);

		$("#save").attr("data-id", dataset.id);
		$('#modal').modal('show');
	});

});
