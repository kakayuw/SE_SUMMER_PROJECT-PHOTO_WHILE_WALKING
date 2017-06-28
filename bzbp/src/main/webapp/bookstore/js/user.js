$(function() {

	$("#save").click(function(e) {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();
		var role = $("input[name='role']").val();
		var name = $("input[name='name']").val();
		var gender = $("input[name='gender']").val();
		var phonenumber = $("input[name='phonenumber']").val();
		var address = $("input[name='address']").val();
		console.log(username, password, role, name, gender, phonenumber, address);

		var dataset = e.currentTarget.dataset;
		var id = dataset.id;

		if (id != "") { // Edit
			jQuery.ajax({
				url : 'updateUserPro',
				processData : true,
				dataType : "text",
				data : {
					id : id,
					username : username,
					password : password,
					role : role,
					name : name,
					gender : gender,
					phonenumber : phonenumber,
					address : address
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

	$(".edit").click(function(e) {
		$('#modalTitle').html("Edit");
		var dataset = e.currentTarget.dataset;
		var id = dataset.id;
		console.log(id);

		$("input[name='username']").val(dataset.username);
		$("input[name='password']").val(dataset.password);
		$("input[name='role']").val(dataset.role);
		$("input[name='name']").val(dataset.name);
		$("input[name='gender']").val(dataset.gender);
		$("input[name='phonenumber']").val(dataset.phonenumber);
		$("input[name='address']").val(dataset.address);

		$("#save").attr("data-id", dataset.id);
		$('#modal').modal('show');
	});

});
