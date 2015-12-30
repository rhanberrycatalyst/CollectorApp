$(document).ready(function() {
	console.log("JQUERYCHECK");
	$("#submit").click(function(){
					var collectible = {
						name : $("#title").val(),
						type : $("#type").val(),
						color: $("#color").val(),
						age: $("#age").val(),
						condition: $("#condition").val(),
						catalogId: $("#catalogId").val(),
						keyword1: $("#keyword1").val(),
						keyword2: $("#keyword2").val(),
						keyword3: $("#keyword3").val(),
						description : $("#description").val(),
						forSale: false
					}
					console.log(collectible);
					$.ajax({
						method: 'POST',
						url: '/collectibles',
						contentType: 'application/json',
						data: JSON.stringify(collectible)
					})
				})
			});
