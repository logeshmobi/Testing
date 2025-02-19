<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container-fluid">    
  <div class="row">
	 <div class="card border-radius">
        <div class="card-content padding-card">
<div>
	<div class="col-xs-12 padding-10">
		<div class="pull-left h2">VoidPayment Details</div>
		</div>
		<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="active"><a href="#" data-toggle="tab">Review and Confirm</a>
				<div class="nav-arrow"></div></li>
		    <li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
         </div>
		<div class="form-body">
	<div class="padding-10 col-xs-12">
		<div class="h3">Transaction Details</div>

		<div class="row static-info">
			<div class="col-md-5 name">Transaction ID:</div>
			<div class="col-md-7 value">${transaction.transactionId}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Transaction Date/Time:</div>
			<div class="col-md-7 value">${transaction.transactionDate}/${transaction.transactionTime}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Invoice Batch No:</div>
			<div class="col-md-7 value">${transaction.batchNo}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Amount:</div>
			<div class="col-md-7 value">${transaction.amount}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Card Holder Name</div>
			<div class="col-md-7 value">${transaction.cardHolderName}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Primary Account Number:</div>
			<div class="col-md-7 value">${transaction.pan}</div>
		</div>
</div>
	<div class="btn btn-primary pull-left ">
	<input type="submit" value="submit"></div>
	</div>
	</div></div></div></div>
	</div>
	
	