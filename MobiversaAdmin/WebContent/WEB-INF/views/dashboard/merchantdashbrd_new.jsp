<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  
<style>
.c-0e89e3{color:#0e89e3;}
.fs-80-pt{font-size:80%;}
.w15p{width:15%;}


.col-1 {width: 8.33%;float:left;padding:0 0.75rem}
.col-2 {width: 16.66%;float:left;padding:0 0.75rem}
.col-3 {width: 25%;float:left;padding:0 0.75rem}
.col-4 {width: 33.33%;float:left;padding:0 0.75rem}
.col-5 {width: 41.66%;float:left;padding:0 0.75rem}
.col-6 {width: 50%;float:left;padding:0 0.75rem}
.col-7 {width: 58.33%;float:left;padding:0 0.75rem}
.col-8 {width: 66.66%;float:left;padding:0 0.75rem}
.col-9 {width: 75%;float:left;padding:0 0.75rem}
.col-10 {width: 83.33%;float:left;padding:0 0.75rem}
.col-11 {width: 91.66%;float:left;padding:0 0.75rem}
.col-12 {width: 100%;float:left;padding:0 0.75rem}
.hide_key{display:none;}



@media only screen and (max-width: 768px) {

[class*="col-"] {
width: 100%;float:left;padding:0 0.75rem
}
}




.table-new thead tr{background:#d7e7ea;}
.table-new thead tr th{border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;padding:10px !important;}
.table-new tbody tr td{padding:10px !important;}
.table-new tbody tr {border-bottom:none !important;}
.bg-f3fbfd{background-color:#f3fbfd;}
.br-5{border-radius:5px;}
.c-092540{color:#092540;}
.c-0e89e3{color:#0e89e3;}
.c-151931{color:#151931;}
.mb-40{margin-bottom:40px;}
.chart_div svg{display:block !important;}
.chart_div figure{margin:0px;}
.btccc{border-top: solid 1px #ccc;}
.align-items-center .c-151931{color:#151931 !important;z-index:2;}
.back-color{background-color:#fff;}
.table-head{background-color:#005baa !important;}



</style>

       <script src="https://code.highcharts.com/highcharts.js"></script>
       
<style>
         td, th { padding: 0px 8px; }
         thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
         
      </style>
     
      </head>
    
      <body>
      
<div class="container-fluid">
   <div class="row">
      <div class="col s12">
         <div class="d-flex align-items-center">
            <h5 style="color:#fff">Dashboard </h5>
         </div>
      </div>
   </div>
   <div class="row">
      <div class="col-8 mb-40">
         <div class="chart_div">
            <figure class="highcharts-figure">
               <div id="column_chart_container"></div>
            </figure>
         </div>
      </div>
      <div class="col-4 mb-40">
         <div class="table-responsive m-b-20 m-t-15">
            <table class="table-new bg-f3fbfd">
               <thead>
                  <tr class="table-head">
                     <th class="c-151931">Type</th>
                     <th class="c-151931">Amount (RM)</th>
                  </tr>
               </thead>
               <tbody class="back-color">
            
                  <tr>
                  
						<td class="c-151931">EZYWAY</td>
						
					<c:set var = "waycount" value = "${waycount}"/>
						<c:if test = "${waycount == 'findoutmore'}">
						 <td class="c-0e89e3"> <a href='https://gomobi.io' target="_blank">Find out more</a></td>
						</c:if>
						
						<c:if test = "${waycount != 'findoutmore'}">
						 <td class="c-151931">${waycount}</td>
						</c:if>
                  </tr>
              <tr>
                     <td class="c-151931">EWALLET</td>
                     <c:set var = "walletcount" value = "${walletcount}"/>
						<c:if test = "${walletcount == 'findoutmore'}">
						 <td class="c-0e89e3"> <a href='https://gomobi.io' target="_blank">Find out more</a></td>
						</c:if>
						
						<c:if test = "${walletcount != 'findoutmore'}">
						 <td class="c-151931"> ${walletcount}</td>
						</c:if>
                     
                  </tr>
                  <tr>
                     <td class="c-151931">EZYWIRE</td>
                    <c:set var = "wirecount" value = "${wirecount}"/>
						<c:if test = "${wirecount == 'findoutmore'}">
						 <td class="c-0e89e3"> <a href='https://gomobi.io' target="_blank">Find out more</a></td>
						</c:if>
						
						<c:if test = "${wirecount != 'findoutmore'}">
						 <td class="c-151931"> ${wirecount}</td>
						</c:if>
                  </tr>
                  <tr>
                     <td class="c-151931">EZYREC</td>
                 <c:set var = "reccount" value = "${reccount}"/>
						<c:if test = "${reccount == 'findoutmore'}">
						 <td class="c-0e89e3"> 
						 <a href='https://gomobi.io' target="_blank">Find out more</a>
						 </td>
						</c:if>
						
						<c:if test = "${reccount != 'findoutmore'}">
						 <td class="c-151931"> ${reccount}</td>
						</c:if>
                  </tr>
                  <tr>
                     <td class="c-151931">EZYLINK</td>
                    <c:set var = "linkcount" value = "${linkcount}"/>
						<c:if test = "${linkcount == 'findoutmore'}">
						 <td class="c-0e89e3">
						 <a href='https://gomobi.io' target="_blank">Find out more</a>
						 </td>
						</c:if>
						
						<c:if test = "${linkcount != 'findoutmore'}">
						 <td class="c-151931">${linkcount}</td>
						</c:if>
                  </tr>
                  <tr>
                     <td class="c-151931">EZYMOTO</td>
                     <c:set var = "motocount" value = "${motocount}"/>
						<c:if test = "${motocount == 'findoutmore'}">
						 <td class="c-0e89e3"> 
						 <a href='https://gomobi.io' target="_blank">Find out more</a>
						 </td>
						</c:if>
						
						<c:if test = "${motocount != 'findoutmore'}">
						 <td class="c-151931">${motocount}</td>
						</c:if>
                  </tr>
                  
                  <tr class="">
                     <td class=""></td>
                     <td class=""></td>
                  </tr>
                  <tr class="btccc">
                     <td class="c-151931">TOTAL</td>
                     <td class="c-151931">${total}</td>
                  </tr>
               </tbody>
            </table>
         </div>
      </div>
   </div>
   <div class="row ">
      <div class="col-8 mb-40">
         <div class="">
            <h6 class="c-092540">  </h6>
            <div class="table-responsive m-b-20 m-t-15">
               <table class="table-new bg-f3fbfd">
                  <thead>
                     <tr class="table-head">
                        <th class="c-151931">App Username</th>
                        <th class="c-151931">TID</th>
                        <th class="c-151931">API key </th>
                        <th class="c-151931">Expiry Date</th>
                     </tr>
                  </thead>
                  <tbody class="back-color">
                  
                      <c:forEach items="${paginationBean2.itemList}" var="dtoo" varStatus="count">
                     <tr>
                        <td class="c-151931">${dtoo.mobileUserName}</td>
                        <td class="c-151931">${dtoo.tid}</td>
                        <td class="c-0e89e3 fs-80-pt">
                           <span onclick="show_key('${paginationBean2.itemList[count.index]}')" id="hide_key_${paginationBean2.itemList[count.index]}"> View key <img  src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAPBAMAAADjSHnWAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAwUExURUdwTCCP7x+S5B6S5h6S5h6R5B+S5R2S5BuP5B+R5RyT5x2T5hiP5x6R5R+S5R+S5dsDYYsAAAAPdFJOUwAQ8HifkM1gMN9AUCCw4M+dzv0AAACUSURBVAjXY2BgYOCxnf8/ZQMDBPD4/weCT4YQXvz/fxZcQf9/gmXF/v+/yMDAof//KwOY+icg7M6w/v9/BwYGof//PzPO/x/A9v//FwaG/P//f7H///+BGWhSAZzHCuQZgFR+Z3wPUwk05SeD8HWG/WBTGI6BKagNDIzx/38WcsX//wRxG8d9ZJcx8Jjp/08pZMAAAGB3VY53Q2EuAAAAAElFTkSuQmCC"> </span>
                           <span id="show_key_${paginationBean2.itemList[count.index]}" class="hide_key">${dtoo.motoapikey}</span>
                        </td>
                        <td class="c-151931">${dtoo.expiryDate}</td>
                     </tr>
                     </c:forEach>
                    
                     
                    
                  </tbody>
               </table>
            </div>
         </div>
      </div>
      <div class="col-4 mb-40">
         <h6 class="c-092540">Success / Failure Ratio Breakdown</h6>
         <div class="chart_div">
            <figure class="highcharts-figure">
               <div id="pie_chart_container"></div>
            </figure>
         </div>
      </div>

      <script>
         function show_key(id){
         
         	document.getElementById('show_key_'+id).style.display ="block";
         	document.getElementById('hide_key_'+id).style.display ="none";
         
         }
          var monthlist = [${threeMonthTxn.month}];
         var amountlist = [${threeMonthTxn.amountData}];
      
      //   var amountlist = [0,23232,10000,566,2323,1232];
         
         var labels = [
             {
                 name:  monthlist[0],
                 y: amountlist[0],
                 drilldown: monthlist[0]
             },
             {
                 name: monthlist[1],
                 y: amountlist[1],
                 drilldown: monthlist[1]
             },
             {
            	 name: monthlist[2],
                 y: amountlist[2],
                 drilldown: monthlist[2]
             },
             {
            	 name: monthlist[3],
                 y: amountlist[3],
                 drilldown: monthlist[3]
             },
             {
            	 name: monthlist[4],
                 y: amountlist[4],
                 drilldown: monthlist[4]
             },
             {
            	 name: monthlist[5],
                 y: amountlist[5],
                 drilldown: monthlist[5]
             }
         ];
         // Create the chart
         Highcharts.chart('column_chart_container', {
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            accessibility: {
                announceNewData: {
                    enabled: true
                }
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: ''
                }
         
            },
            legend: {
                enabled: false
            },
         credits: {
             enabled: false
         },
            plotOptions: {
                series: {
                    borderWidth: 0,
         		color: {
                            linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
                            stops: [
                                [0, '#005baa'],
                                [1, '#008bec']
                            ]
                        },
                    dataLabels: {
                        enabled: true,
                        format: 'RM {point.y:.0f}'
                    }
                }
            },
         
            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>RM {point.y:.0f}</b> <br/>'
            },
         
            series: [
                {
                    name: "",
                    colorByPoint: false,
                    data: labels
                }
            ],
            
                
         });
         
         
         Highcharts.chart('pie_chart_container', {
            chart: {
                type: 'pie',
         	
                options3d: {
                    enabled: true,
                    alpha: 45,
         		
                }
         	 
                    
            },
                 
         colors: ['#005baa', '#a1a0a2'],
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
         credits: {
             enabled: false
         },
         
            plotOptions: {
                pie: {
                    innerSize:  150,
                    depth: 45
                }
            },
         
          tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> <br/>'
            },
         
         
            series: [{
                name: '',
                data: 
                	[
                     ['Success', ${sucess}],
                    ['Failure', ${failure}],
//                        ['Success', 0],
//                         ['Failure', 0],
                    
                ]
            }]
         });
      </script>
      
   </div>
</div>
</body>
</html>
