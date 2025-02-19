<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">
    <link rel="icon" type="image/gif" sizes="16x16"
          href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobi</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="mobile-web-app-capable" content="yes">
    <style>

        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
            font-family: "Poppins", sans-serif !important;
        }

        .container {
            text-align: center;
            padding: 40px;
            border-radius: 8px;
            margin-bottom: 90px !important;
        }

        .container img {
            width: 184px;
            height: auto;
        }

        .message {
            margin-top: 20px;
            font-size: 34px;
            color: #333;
            font-weight: 500;
        }

        .sub-message {
            margin-top: 10px;
            font-size: 14px;
            color: #666;
        }

        .sub-message a {
            color: #005baa;
            text-decoration: underline;
        }

    </style>
</head>
<body>
<div class="container" >
    <div style="margin-bottom: -60px;">
        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/link-expired.svg" alt="Link Logo">
    </div>
    <div class="message">This link has already been used.</div>
    <div class="sub-message">Please contact <a href="mailto:csmobi@gomobi.io">csmobi@gomobi.io</a> for assistance.</div>
</div>
</body>
</html>
