<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;0,400;1,400&display=swap" rel="stylesheet">
	<title>Withdraw Page</title>
	<style>
	/* Center the card on the page */


.container-fluid {
  background: linear-gradient(135deg, #74C7A5, #3A6EA5);
   height: 70vh;
}

.custom-container {
  background-color: transparent;
}
	
.container {
font-family: 'Poppins', sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
   height: 60vh;
 
/*   background: linear-gradient(135deg, #74C7A5, #3A6EA5); */
}

/* Style the card */
.card {
  width: 40rem;
  height: 20rem;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  background-color: #f1f1f1;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  background-color: #fff;
  background-image: linear-gradient(to right bottom, #3A6EA5, #74C7A5);
  color: #fff;
}

/* Customize the input box */
input[type=number] {
  padding: 10px;
  border: none;
   border-bottom: 2px solid #4CAF50;
  font-size: 30px;
  margin-bottom: 20px;
  text-align: center;
  outline: none;
  width:4rem;
  background-color: #f1f1f1;
  color: #fff;
  height:3rem;
  font-family: 'Poppins', sans-serif;
  
}

input::placeholder {
  color: #e7e7e6;
}
input:focus::placeholder {
  opacity: 0;
}
/* Style the button */
button {
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  background-color: #3A6EA5;
  color: white;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-family: 'Poppins', sans-serif;
  
}

/* Change button color on hover */
button:hover {
  background-color: #326195;
}
label{
color:#ffe;
font-size:30px;
}

	</style>
</head>
<body>
<div class="container-fluid">
  <div class="row custom-container">
   <div class="col-md-12">
	<div class="container">
		<div class="card">
		<!-- <form> -->
			<label for="withdraw-amount">Withdraw Amount</label>
			<input type="number" placeholder= "Enter the WithDraw Amount (RM)" id="withdraw-amount" name="withdraw-amount" min="0" step="0.5" style="width:20rem;font-size:16.5px;" required >

			<button onclick="withdraw()">Withdraw</button>
		<!-- </form> -->
		</div>
		</div>
	</div>
	</div>
	</div>

	<script>
		function withdraw() {
			var amount = document.getElementById("withdraw-amount").value;

			if (isNaN(amount)) {
				alert("Please enter a valid number.");
				return;
			}

			// Do whatever you want with the amount here
			alert("Withdraw amount: " + amount);
			
			
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/withDraw?withdrawAmount='
				+amount;
		form.submit;
		}
	</script>
</body>
</html>
