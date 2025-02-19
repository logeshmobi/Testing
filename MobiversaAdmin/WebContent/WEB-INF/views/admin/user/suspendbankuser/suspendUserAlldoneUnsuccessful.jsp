<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Suspend User All Done Unsuccessful</title>
</head>
<body style="
    border: 1px solid;
    height: 641px;
">
<div><jsp:include page="<%="/admin/user/suspendbankuser/bankUsersideBlock"%>"/></div>
<form:form action="" method="post" commandName="bankUser">
<div>
<!-- Header Start-->
<div style="width:100%; height:60px; background-color:#D8D8D8">
	<div style="width:40%; float:left; height:100%">
		<div style="width:40%; height:100%; float:left"><img src="TheBanksLogo.jpg" height="100px" width="100%"></div>
		<div><p>Good Day, John Smith<br>Wednesday, 01 August 2012, 00:35:56<br></p></div>
	</div>
	<div><input name="logout" type="button" value="logout" style="float: right;top: 30px;position: relative;right: 20px;background-color: #FAF7F7;width: 80px;height: 25px;">
	</div>
</div>
<p>       </p>
<!-- Header End-->

<!-- Header2 Start-->



<div style="width:100%; height:50px; background-color:#D8D8D8">

	<div style="width:150px; float:left"><a href="/index.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color: #A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0 0 30px; position: relative;top:10px;float: right;right: -40px">My Home</a></div>
	
<div style="width:150px; float:left"><a href="/Features.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color:#A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0 0 20px; position: relative;top:10px">All Companies</a></div>
	
<div style="width:150px; float:left"><a href="/How_It_Works.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color: #A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0px 0 20px; position: relative;top:10px">Manage Mobile Users</a></div>
	
<div style="width:150px; float:left"><a href="/About_Us.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color: #A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0 0 20px; position: relative;top:10px">Manage Readers</a></div>
	
<div style="width:150px; float:left"><a href="/Contact_Us.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color: #A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0 0 20px; position: relative;top:10px">Manage Transactions</a></div>
	
<div style="width:150px; float:left"><a href="/Contact_Us.php" style="height: 30px;  width: 150px;  display: block;  border-radius: 30px;  background-color: #A4A4A4;  text-decoration: none;  font-size: 16px;  color: black;  padding: 9px 0 0 20px; position: relative;top:10px">Manage Credit Settlements</a></div>
	
<div style="width:150px; float:left"><a href="/Contact_Us.php" style="height: 30px;width: 90px;display: block;background-color: #FFFFFF;text-decoration: none;font-size: 16px;color: black;padding: 9px 0 0 20px;position: relative;top:10px;float: right;right: -180px;">Admin</a></div>
</div>
<!-- Header2 ends-->
<p>               </p>
<!-- Body Start-->

<div style="width:10%; height:180px; border:1px solid;background-color:#FFFFFF; float:left">
<div style="height: 50%;float: left;background-color: #FFFFFF;">
	<div style="height: 12px;width: 120px;float:left;display: block;border-style: solid:width:0px;background-color: #FFFFFF;text-decoration: none;font-size: 12px;color: black;padding: 9px 0 0 25px;position: relative;">  </div>

        <div style="height: 15px;width: 120px;float:left;display: block;border-style: solid:width:0px;background-color: #424242;text-decoration: none;font-size: 12px;color: black;padding: 9px 0 0 25px;position: relative;">Manage Bank Users</div>

	<div style="height: 15px;width: 120px;float:left;display: block;border-style: solid:width:0px;background-color: #FFFFFF;text-decoration: none;font-size: 12px;color: black;padding: 9px 0 0 25px;position: relative;"> Add a Bank User</div>

	<div style="height: 15px;width: 120px;float:left;display: block;border-style:solid:width:0px;background-color: #FFFFFF;text-decoration: none;font-size: 12px;color: black;padding: 9px 0 0 25px;position: relative;">System error log</div>

	<div style="height: 15px;width: 120px;float:left;display: block; border-style:solid:width:0px;background-color: #FFFFFF;text-decoration: none;font-size: 12px;color: black;padding: 9px 0 0 25px;position: relative;">Audit trail</div>

</div>
</div>
<div style="background-color: #D8D8D8;height: 45px;line-height: 2;">
You're currently viewing ADID :  ${bankUser.username }

<div style="height: 30px;width: 100%;line-height: 2; float:right;">
<input type="Button" name="View bank User Details" value="View bank User Details">
</div></div>
<div style="height: 30px;width: 100%;line-height: 2;"><b>Suspend Bank User</b></div>
<div style="height: 45px;width: 100%;background-color: #D8D8D8;line-height: 2;">
	<div style=" float: left;width: 175px; border: 1px solid;background-color: white;">Suspension Details</div>
    <div style=" border: 1px solid;width: 126px;float: left;">All Done</div>
</div>
</div>
<div style="height: 320px;width: 89%;border:1px solid;float: left;">
	<div style="border:1px solid;height: 257px;width: 91%;margin-left: 85px;">
		<div><b>Whoops..Something is wrong!</b></div>
		<div>________________________________________________________________________________</div>
		<div>Your suspension request is un-successful.</div>
                <div style="float: left;left: 200px;position: relative;width: 100%;line-height: 2;font-size: 20px;">ErrorCode: XXX00123456</b></div>
        </div>
	<div style="float: right;left: 40px;top: 15px;position: relative;">
		<div><b>What You should do:</b></div>
		<input type="Button" name="submit" value="Try Again" height="100px" width="100%" style="margin-left: 24px;">
                <div> If you need help or support, please call X.XXX.XXX.XXXX.</div>
		</div>
	</div>
</div>
<div style="
    background-color: rgb(182, 175, 175);
    float: left;
    width: 100%;
    height: 30px;
    margin-top: 75px;
">
	<div style="
    float: left;
">Terms &amp; Conditions | Privacy Policy</div>
	<div style="
    float: right;
">Copyright ©2013 The Bank.All Rights Reserved.</div>
</div>
</form:form>
</body>
</html>