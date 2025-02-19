<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <!-- jQuery and jQuery UI -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    <!-- XSS Security -->
    <%--    <script src="${pageContext.request.contextPath}/resourcesNew/js/purify.min.js"></script>--%>
    <%--    <script src="${pageContext.request.contextPath}/resourcesNew/js/xss_security.js"></script>--%>

    <%--    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>--%>


    <script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chart.js/dist/Chart.umd.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>




    <style>
        .container-fluid, .text-white strong {
            font-family: 'Poppins', sans-serif !important;
        }

        .fs-22 {
            font-size: 22px !important;
        }

        .mb-0 {
            margin-bottom: 0;
        }

        .mb-1 {
            margin-bottom: 4px;
        }

        .color_2d2d2d {
            color: #2d2d2d;
        }

        .payment_heading {
            font-weight: 600;
        }

        .small_texts {
            color: #7B7B7B;
            font-size: 13px;
        }

        /* The Modal (background) */
        .modal {
            display: none;
            position: fixed;
            z-index: 100;
            left: 0;
            top: 0;
            width: 100%;
            height: 100vh;
            overflow: scroll;
            scrollbar-width: none;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
            max-height: 100vh;
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: 5% 8%; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;

        }

        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }



        @media screen and (min-width: 768px){
            .fpx_popup .m10{
                width: 92% !important;
            }
        }

        @media screen and (min-width: 1024px){
            .fpx_popup .l10{
                width: 88% !important;
            }
        }

        .image-button, .image-button-payout{
            float: right;
            background: transparent;
            border: none;
            cursor: pointer;
            display: none;
        }

        .image-button-payout:focus, .image-button:focus{
            float: right;
            background: transparent;
            border: none;
            cursor: pointer;
            box-shadow: none !important;
        }

        #fpxcard:hover .image-button{
            display: inline-block;
        }

        #payoutcard:hover .image-button-payout{
            display: inline-block;
        }

        .notecontent{
            color: #858585;
            font-size: 12px;
            text-align: center !important;
            margin-bottom: 0 !important;
        }

        .star{
            color: red;
        }

        .chart-container {
            width: 100%;
            height: 300px; /* Adjust as needed */
            
        }

        @media (max-width: 600px) {
            .chart-container {
                height: 250px; /* Adjust for smaller screens */
                
            }
        }

        .modal-content, .modal-content .card-content {
            padding: 15px !important;
        }

        .mb-10{
            margin-bottom: 10px;
        }

        #rawpayoutexpand:hover, #rawfpxexpand:hover{
            content: url('${pageContext.request.contextPath}/resourcesNew1/assets/expand-blue.svg');
        }

        #fpxspan img:hover, #payoutspan img:hover{
            content: url('${pageContext.request.contextPath}/resourcesNew1/assets/shrink-blue.svg');
        }

    </style>
</head>

<body>
<div class="container-fluid mb-0" id="pop-bg">
    <div class="row">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class="d-flex align-items-center">
                        <h3 class="text-white mb-0"><strong class="fs-22">Transaction Metrics</strong></h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s12 m12 l6">
            <div class="card blue-bg text-white" id="fpxcard">
                <div class="card-content" style="padding: 15px;">
                    <div style="display: flex;align-items: flex-start;justify-content: space-between;" class="mb-10">
                        <div>
                            <h3 class="mb-1 color_2d2d2d payment_heading">Payins - FPX</h3>
                            <p class="small_texts">Transaction Completion Rate(%) by banks</p>
                        </div>
                        <button class="image-button" id="openModalBtn" >
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/expand.svg" id="rawfpxexpand"/>
                        </button>
                    </div>

                    <div class="chart-container chart-container-fpx">
                    
                     <c:if test="${fpxMetricsList == '[]'}">
							    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
							        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/No Records.svg" alt="No Records Found" style="width: 150px;">
							    </div>
						</c:if>
						
                        <canvas id="myBarChart"></canvas>
                    </div>
<%--                    <canvas id="myBarChart" width="1000" height="650"></canvas> <!-- Adjust width as needed -->--%>

                    <p class="notecontent"><span class="star">*</span>Data represents transactions from the last 30 minutes.</p>
                </div>
            </div>
        </div>

        <div class="col s12 m12 l6">
            <div class="card blue-bg text-white" id="payoutcard">
                <div class="card-content"  style="padding: 15px;">
                    <div style="display: flex;align-items: flex-start;justify-content: space-between" class="mb-10">
                        <div>
                            <h3 class="mb-1 color_2d2d2d payment_heading">Payouts</h3>
                            <p class="small_texts">Transaction Completion Rate(%) by merchants</p>
                        </div>
                        <button class="image-button-payout" id="openPayoutChartBtn" >
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/expand.svg" id="rawpayoutexpand"/>
                        </button>
                    </div>
                    <div class="chart-container chart-container-payout">
                    
                     <c:if test="${payoutMetricsList == '[]'}">
							    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
							        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/No Records.svg" alt="No Records Found" style="width: 150px;">
							    </div>
						</c:if>
                        <canvas id="payoutBarChart"></canvas>
                    </div>

                <%--                    <canvas id="payoutBarChart" width="1000" height="650"></canvas>--%>
                    <p class="notecontent"><span class="star">*</span>Data represents transactions from the last 30 minutes.</p>
                </div>
            </div>
        </div>
    </div>


    <div id="fpxModal" class="modal">
        <!-- Modal content -->
        <div class="fpx_popup">
<!--             <div class="col s12 m12 l12">
 -->                <div class="modal-content card blue-bg text-white">

                    <div class="card-content">
                        <div style="display: flex;align-items: center;justify-content: space-between">
                            <div class="mb-10">
                                <h3 class="mb-1 color_2d2d2d payment_heading">Payins - FPX</h3>
                                <p class="small_texts">Transaction Completion Rate(%) by banks</p>
                            </div>
                            <span class="close" id="fpxspan">
                                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/shrink.svg"/>
                            </span>
                        </div>

                        <div class="chart-container chart-container-fpx">
                        
                           <c:if test="${fpxMetricsList == '[]'}">
							    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
							        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/No Records.svg" alt="No Records Found" style="width: 150px;">
							    </div>
						</c:if>
						
                            <canvas id="myFpxBarChart"></canvas>
                        </div>
<%--                        <canvas id="myFpxBarChart" width="1000" height="350"></canvas>--%>
                    </div>
                </div>
            </div>
<!--         </div>
 -->    </div>

    <%--    payout --%>

    <div id="payoutModal" class="modal">
        <!-- Modal content -->

        <div class="fpx_popup">
<!--             <div class="col s12">
 -->                <div class="modal-content card blue-bg text-white">

                    <div class="card-content">
                        <div style="display: flex;align-items: center;justify-content: space-between">
                            <div class="mb-10">
                                <h3 class="mb-1 color_2d2d2d payment_heading">Payouts</h3>
                                <p class="small_texts">Transaction Completion Rate(%) by merchants</p>
                            </div>
                                <span class="close" id="payoutspan">
                                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/shrink.svg"/>
                                </span>
                        </div>
                        <div class="chart-container chart-container-payout">
                        
                        <c:if test="${payoutMetricsList == '[]'}">
							    <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
							        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/No Records.svg" alt="No Records Found" style="width: 150px;">
							    </div>
						</c:if>

                
                            <canvas id="myPayoutBarChart"></canvas>
                        </div>

<%--                        <canvas id="myPayoutBarChart" width="1000" height="350"></canvas>--%>

                    </div>
                </div>
            </div>
<!--         </div>
 -->    </div>




</div>




<script>


	var fpxMetricsList = JSON.parse('${fpxMetricsList}');
	var payoutMetricsList = JSON.parse('${payoutMetricsList}');

// Now you can access the data as regular JavaScript objects
	//console.log(fpxMetricsList);
	//console.log(payoutMetricsList);

    // Get the modal
    var modal = document.getElementById('fpxModal');
    var payoutModal = document.getElementById('payoutModal');



    // Get the button that opens the modal
    var btn = document.getElementById('openModalBtn');

    var payoutBtn = document.getElementById('openPayoutChartBtn');

    // Get the <span> element that closes the modal
    var fpxspan = document.getElementById('fpxspan');
    var payoutspan = document.getElementById('payoutspan');

    Chart.register(ChartDataLabels);

    <%--const fpxMetricsData = ${fpxmetrics};--%>

    window.onload = fpxChart('myBarChart',0.8,0.6,fpxMetricsList);
    window.onload = payoutchart('payoutBarChart',0.8,0.9,payoutMetricsList);

    btn.onclick = function() {
        modal.style.display = 'block';
        fpxChart('myFpxBarChart',0.8,0.3,fpxMetricsList);
    }
    payoutBtn.onclick = function () {
        payoutModal.style.display = 'block';
        payoutchart('myPayoutBarChart',0.6,0.6,payoutMetricsList);

    }

    payoutspan.onclick = function() {
        payoutModal.style.display = 'none';
    }

    fpxspan.onclick = function() {
        modal.style.display = 'none';
    }

    function fpxChart(myBarChart, barPercentage, categoryPercentage,jsonData) {
        var ctx = document.getElementById(myBarChart).getContext('2d');

        var labels = [];
        var successData = [];
        var failureData = [];
        var bankCodes = [];
        
        //console.log("fpx list size : ",jsonData.length);
        
        if (jsonData.length === 0) {
          
            return; 
        }

      /*  jsonData.forEach(function(item) {
        	
        	 let bankName = item.bankName;
        	 const bankRegex = /bank/i;
        	    if (bankRegex.test(bankName)) {
        	        bankName = bankName.split(bankRegex)[0] + "Bank";
        	    }

        	    if (bankName.length > 10) {
        	        var wrappedLabel = bankName.split(/[\s_]+/);
        	        labels.push(wrappedLabel);
        	    } else {
        	        labels.push(bankName);
        	    }
        	    
            successData.push(item.successRate);
            failureData.push(item.failureRate);
            bankCodes.push(item.bankCode);
        });

      */
      
     
      const fallbackBankNames = {
              "ABB0235": "Affin Bank",
              "ABMB0213": "Alliance Bank",
              "AMBB0208": "AmBank Malaysia",
              "BIMB0340": "Bank Islam Malaysia",
              "BKRM0602": "Bank Kerjasama Rakyat Malaysia",
              "BMMB0342": "Bank Muamalat Malaysia",
              "BCBB0235": "CIMB Bank",
              "CIT0218": "CITIBANK BHD",
              "DBB0199": "Deutsche Bank",
              "HLB0224": "Hong Leong Bank",
              "HSBC0223": "HSBC Bank Malaysia",
              "KFH0346": "Kuwait Finance House(Malaysia)",
              "MBB0228": "Malayan Bank(M2E)",
              "OCBC0229": "OCBC Bank Malaysia",
              "PBB0233": "Public Bank",
              "PBB0234": "Public Bank Enterprise",
              "RHB0218": "RHB Bank",
              "SCB0215": "Standard Chartered Bank",
              "UOB0228": "United Overseas Bank",
              "BNP003": "BNP Parbias Malaysia",
              "AGRO02": "Agro Bank"
          };
      
      jsonData.forEach(function(item) {
     	    let bankName = item.bankName;
     	    const bankRegex = /bank/i;

     	    if (bankRegex.test(bankName)) {
     	        bankName = bankName.split(bankRegex)[0].trim() + "Bank";
     	    }

     	    if (!bankName || bankName === "Bank") {
     	        bankName = fallbackBankNames[item.bankCode] || " "; 
     	    }

     	    // Wrap the label if it’s longer than 10 characters
     	    if (bankName.length > 10) {
     	    	 const words = bankName.split(/\s+/);  
     	         const wrappedLabel = [];

     	         for (let i = 0; i < words.length; i += 2) {
     	             wrappedLabel.push(words.slice(i, i + 2).join(' '));
     	         }
     	         labels.push(wrappedLabel);
     	    } else {
     	        labels.push(bankName);
     	    }

     	    successData.push(item.successRate);
     	    failureData.push(item.failureRate);
     	    bankCodes.push(item.bankCode);
     	});

      
        var myBarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Success',
                    data: successData,
                    backgroundColor: '#005baa',
                    borderColor: '#005baa',
                    borderWidth: 1,
                    borderRadius: 4,
                    barPercentage: barPercentage,
                    categoryPercentage: categoryPercentage,
                    hoverBackgroundColor: '#005baa',
                    hoverBorderColor: '#005baa'

                },
                    {
                        label: 'Failure',
                        data: failureData,
                        backgroundColor: '#E9696A',
                        borderColor: '#E9696A',
                        borderWidth: 1,
                        borderRadius: 4,
                        barPercentage: barPercentage,
                        categoryPercentage: categoryPercentage,
                        hoverBackgroundColor: '#E9696A',
                        hoverBorderColor: '#E9696A'
                    }]
            },
            options: {
            	 animation: {
                     duration: 500 // Set duration to 500ms for faster animation speed
                 },
            	layout: {
        	        padding: {
        	            top: 20 
        	        }
        	 },
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        grid: {
                            display: false, // Hide x-axis grid lines
                        },
                        ticks: {
                            color: '#858585',
                            font: {
                                size: 10,
                                family: 'Poppins',
                                weight: '500'
                            }
                        },
                        min: 0,
                        max: 3,

                    },
                    y: {
                    	min: 0, // Set minimum value to 0
                    	max: 100,
                        grid: {
                            display: true, // Hide y-axis grid line,

                        },
                        border: {
                            dash: [8, 8],
                            borderRadius: 3
                        },
                        ticks: {
                            stepSize: 20, // Set the step size to 20
                            max: 100, // Ensure the maximum value is 100
                            callback: function (value) {
                                return value; // Display percentage on y-axis
                            },
                            font: {
                                size: 11,
                                family: 'Poppins'
                            }
                        },
                    },

                },

                plugins: {
                    datalabels: {
                        display: true, // Enable data labels
                        anchor: 'end',
                        align: 'end',
                        color: '#333', // Data label color
                        font: {
                            family: 'Poppins',
                            size: 11,

                        },
                        clip: false,
                        formatter: function (value, context) {
                            return value + '%';
                        }
                    },
                    legend: {
                        position: 'bottom',
                        labels: {
                            color: '#333',
                            font: {
                                size: 12,
                                family: 'Poppins',

                            },
                            boxWidth: 22,
                            boxHeight: 15,
                            useBorderRadius: 3,
                            padding: 25

                        }
                    },
                    tooltip: {
                        animation: false, 
                        yAlign: 'bottom',

                        callbacks: {
                            title: function () {
                                return ''; // Hide the title (bank names)
                            },
                            // Update the label callback in the tooltip options
                            label: function (context) {
                                const datasetIndex = context.datasetIndex;
                                const index = context.dataIndex;
                                const value = context.parsed.y;

                                const bankCode = bankCodes[index];
                                const bankData = jsonData.find(item => item.bankCode === bankCode);
                             //   //console.log(bankCode+" ::: "+bankData.bankCode);

                                if (datasetIndex === 0) {
                                    return `Success Rate ` + value + `%`+`(`+bankData.totalSuccessTxnCount+`)`;
                                } else if (datasetIndex === 1) {

                                    const failureMetrics = bankData.failureMetricsList;
                                    const metricsLabels = failureMetrics.map(function(metric) {
                                        return '\u2022 ' + metric.reasonText + ' ' + metric.reasonRate + '%';
                                    });


                                    return [
                                        'Failure Rate  ' + value + '% (' + bankData.totalFailureTxnCount + ')',
                                        ...metricsLabels
                                    ];
                                }
                            },

                            labelColor: function (context) {
                                const datasetIndex = context.datasetIndex;

                                return {
                                    borderColor: '#000',
                                    backgroundColor: datasetIndex === 0 ? '#005baa' : '#E9696A', // Color for success or failure
                                    borderRadius: 3
                                };
                            },

                        },
                        titleFont: {
                            family: 'Poppins',
                        },
                        bodyFont: {
                            family: 'Poppins',
                            size: 11
                        },
                        backgroundColor: 'black',
                        titleColor: 'white',
                        bodyColor: 'white',
                        boxWidth: 12,
                        boxHeight: 12,
                        borderColor: 'black',
                        padding: 10,

                    }
                }
            }
        });

        /*  function scroller(scroll, chart) {
        	
        	 scroll.preventDefault();

          //  //console.log(scroll);
            const datalength = myBarChart.data.labels.length;
            if (scroll.deltaY > 0 || scroll.deltaX > 0) {

                if (myBarChart.config.options.scales.x.max >= datalength) {
                    myBarChart.config.options.scales.x.min = datalength - 3;
                    myBarChart.config.options.scales.x.max = datalength;
                } else {
                    myBarChart.config.options.scales.x.min += 1;
                    myBarChart.config.options.scales.x.max += 1;
                }

            } else if (scroll.deltaY < 0 || scroll.deltaX < 0) {
                if (myBarChart.config.options.scales.x.min <= 0) {
                    myBarChart.config.options.scales.x.min = 0;
                    myBarChart.config.options.scales.x.max = 3;
                } else {
                    myBarChart.config.options.scales.x.min -= 1;
                    myBarChart.config.options.scales.x.max -= 1;
                }
            }
            myBarChart.update();
        }

        myBarChart.canvas.addEventListener('wheel', (e) => {
            scroller(e, myBarChart);
        });  */
        
     // Declare global variables to store starting touch positions
        let startY = 0;
        let startX = 0;

        function scroller(scroll, chart) {
            scroll.preventDefault();
            const datalength = chart.data.labels.length;

            let deltaY = 0;
            let deltaX = 0;

            if (scroll.type === 'wheel') {
                // For mouse wheel events
                deltaY = scroll.deltaY;
                deltaX = scroll.deltaX;
            } else if (scroll.type === 'touchmove') {
                // For touch events, calculate delta based on movement from starting positions
                deltaY = scroll.touches[0].clientY - startY;
                // Invert deltaX for a natural right-to-left swipe behavior
                deltaX = -(scroll.touches[0].clientX - startX);

                // Update start positions for the next movement calculation
                startY = scroll.touches[0].clientY;
                startX = scroll.touches[0].clientX;
            }

            if (deltaY !== 0) {
                if (deltaY > 0) {
                    // Scroll down
                    if (chart.config.options.scales.x.max >= datalength) {
                        chart.config.options.scales.x.min = datalength - 3;
                        chart.config.options.scales.x.max = datalength;
                    } else {
                        chart.config.options.scales.x.min += 1;
                        chart.config.options.scales.x.max += 1;
                    }
                } else {
                    // Scroll up
                    if (chart.config.options.scales.x.min <= 0) {
                        chart.config.options.scales.x.min = 0;
                        chart.config.options.scales.x.max = 3;
                    } else {
                        chart.config.options.scales.x.min -= 1;
                        chart.config.options.scales.x.max -= 1;
                    }
                }
            }

            if (deltaX !== 0) {
                if (deltaX > 0) {
                    // Scroll right
                    if (chart.config.options.scales.x.max >= datalength) {
                        chart.config.options.scales.x.min = datalength - 3;
                        chart.config.options.scales.x.max = datalength;
                    } else {
                        chart.config.options.scales.x.min += 1;
                        chart.config.options.scales.x.max += 1;
                    }
                } else {
                    // Scroll left
                    if (chart.config.options.scales.x.min <= 0) {
                        chart.config.options.scales.x.min = 0;
                        chart.config.options.scales.x.max = 3;
                    } else {
                        chart.config.options.scales.x.min -= 1;
                        chart.config.options.scales.x.max -= 1;
                    }
                }
            }

            chart.update();
        }

        // Listen for mouse wheel events
        myBarChart.canvas.addEventListener('wheel', (e) => {
            scroller(e, myBarChart);
        });

        // Track touch start and movement
        myBarChart.canvas.addEventListener('touchstart', (e) => {
            startY = e.touches[0].clientY;  
            startX = e.touches[0].clientX;  
        });

        myBarChart.canvas.addEventListener('touchmove', (e) => {
            scroller(e, myBarChart);
        });
        
     
    }



    function  payoutchart(payoutBarChart,barPercentage, categoryPercentage,payoutMetricsList){

        var ctx = document.getElementById(payoutBarChart).getContext('2d');

        var labels = [];
        var successData = [];
        var onProcessData = [];
        var declinedData = [];
        var merchantIdData = [];
        
        //console.log("payout + : ",payoutMetricsList.length);

        if (payoutMetricsList.length === 0) {
            return; 
        }

        payoutMetricsList.forEach(function(item) {
            if(item.merchantName.length > 10){
                var wrappedLabel = item.merchantName.split(/[\s_]+/);
                labels.push(wrappedLabel);
            } else {
                labels.push(item.merchantName);
            }
            successData.push(item.successRate);
            onProcessData.push(item.onProcessRate);
            declinedData.push(item.failureRate);
            merchantIdData.push(item.merchantId);
        });

        var payoutBarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Paid',
                    data: successData,
                    backgroundColor: '#005baa',
                    borderColor: '#005baa',
                    borderWidth: 1,
                    borderRadius: 4,
                    barPercentage: barPercentage,
                    categoryPercentage: categoryPercentage,
                    hoverBackgroundColor: '#005baa',
                    hoverBorderColor: '#005baa'

                },
                    {
                        label: 'In Process',
                        data: onProcessData,
                        backgroundColor: '#D4EBFF',
                        borderColor: '#D4EBFF',
                        borderWidth: 1,
                        borderRadius: 4,
                        barPercentage: barPercentage,
                        categoryPercentage: categoryPercentage,
                        hoverBackgroundColor: '#D4EBFF',
                        hoverBorderColor: '#D4EBFF'
                    },
                    {
                        label: 'Declined',
                        data: declinedData,
                        backgroundColor: '#E9696A',
                        borderColor: '#E9696A',
                        borderWidth: 1,
                        borderRadius: 4,
                        barPercentage: barPercentage,
                        categoryPercentage: categoryPercentage,
                        hoverBackgroundColor: '#E9696A',
                        hoverBorderColor: '#E9696A'
                    }]
            },
            options: {
            	 animation: {
                     duration: 500 
                 },
            	 layout: {
            	        padding: {
            	            top: 20 
            	        }
            	 },
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        grid: {
                            display: false 
                        },
                        ticks: {
                            color: '#858585',
                            font: {
                                size: 10,
                                family: 'Poppins',
                                weight: '500'
                            },

                            maxRotation: 0,
                            minRotation: 0,
                            autoSkip: false,
                            padding: 10
                        },
                        min: 0,
                        max: 2,

                    },
                    y: {
                    	min: 0, // Set minimum value to 0
                    	max: 100,
                        grid: {
                            display: true, // Hide y-axis grid line,
                        },
                        border: {
                            dash: [8, 8],
                            borderRadius: 3
                        },
                        ticks: {
                            stepSize: 20, // Set the step size to 20
                            max: 100, // Ensure the maximum value is 100
                            callback: function (value) {
                                return value; // Display percentage on y-axis
                            },
                            font: {
                                size: 11,
                                family: 'Poppins'
                            }
                        },
                    },

                },

                plugins: {
                    datalabels: {
                        display: true,
                        anchor: 'end',
                        align: 'end',
                        color: '#333',
                        font: {
                            family: 'Poppins',
                            size: 11,

                        },
                        clip: false,
                        formatter: function (value, context) {
                            return value + '%';  // Show actual data value + percentage
                        }
                    },

                    legend: {
                        position: 'bottom',
                        labels: {
                            color: '#333',
                            font: {
                                size: 11,
                                family: 'Poppins',

                            },
                            boxWidth: 22,
                            boxHeight:15,
                            useBorderRadius:3,
                            padding: 15

                        }
                    },
                    tooltip: {
                        animation: false,
                        yAlign: 'bottom',

                        callbacks: {
                            title: function() {
                                return ''; // Hide the title (bank names)
                            },
                            // Update the label callback in the tooltip options
                            label: function (context) {
                                const datasetIndex = context.datasetIndex;
                                const index = context.dataIndex;
                                const value = context.parsed.y;

                                const MerchantId = merchantIdData[index];
                                const MerchantData = payoutMetricsList.find(item => item.merchantId === MerchantId);
                                if (datasetIndex === 0) {
                                    return `Success `+value+`%`+`(`+MerchantData.totalSuccessTxnCount+`)`;
                                } else if (datasetIndex === 1) {
                                    const labels = [
                                        `In Process ` + value + `%`+`(`+MerchantData.totalOnProcessCount+`)`,
                                        `\u2022 ETC ` + MerchantData.avgProcessingTimeForPp,
                                    ];
                                    return labels;

                                } else if(datasetIndex === 2){
                                    const failureMetrics = MerchantData.failureMetricsList;
                                    const metricsLabels = failureMetrics.map(function(metric) {
                                        return '\u2022 ' + metric.reasonText + ' ' + metric.reasonRate +'%';
                                    });

                                    return [
                                        'Declined ' + value + '% (' + MerchantData.totalFailureTxnCount + ')',
                                        ...metricsLabels
                                    ];
                                }
                            },

                            labelColor: function(context) {
                                const datasetIndex = context.datasetIndex;

                                let backgroundColor;
                                if (datasetIndex === 0) {
                                    backgroundColor = '#005baa';
                                } else if (datasetIndex === 1) {
                                    backgroundColor = '#D4EBFF';
                                } else if (datasetIndex === 2) {
                                    backgroundColor = '#E9696A';
                                }

                                return {
                                    borderColor: '#000',
                                    backgroundColor: backgroundColor,
                                    borderRadius: 3,
                                };
                            },

                        },
                        titleFont: {
                            family: 'Poppins',
                        },
                        bodyFont: {
                            family: 'Poppins',
                            size: 11
                        },
                        backgroundColor: 'black',
                        titleColor: 'white',
                        bodyColor: 'white',
                        boxWidth: 12,
                        boxHeight: 12,
                        borderColor: 'black',
                        padding: 10,

                    }
                }
            }
        });

     /*    function scrollerPayout(scroll, payoutChart) {
        	
        	 scroll.preventDefault();

          //  //console.log(scroll);
            const datalength = payoutBarChart.data.labels.length;
            if (scroll.deltaY > 0 || scroll.deltaX > 0) {

                if(payoutBarChart.config.options.scales.x.max >= datalength){
                    payoutBarChart.config.options.scales.x.min = datalength-2 ;
                    payoutBarChart.config.options.scales.x.max = datalength;
                }else{
                    payoutBarChart.config.options.scales.x.min += 1 ;
                    payoutBarChart.config.options.scales.x.max += 1;
                }

            }else  if (scroll.deltaY < 0 || scroll.deltaX < 0) {
                if (payoutBarChart.config.options.scales.x.min <= 0) {
                    payoutBarChart.config.options.scales.x.min = 0;
                    payoutBarChart.config.options.scales.x.max = 2;
                } else {
                    payoutBarChart.config.options.scales.x.min -= 1;
                    payoutBarChart.config.options.scales.x.max -= 1;
                }
            }
            payoutBarChart.update();
        }

        payoutBarChart.canvas.addEventListener('wheel', (e) => {
            scrollerPayout(e, payoutBarChart);

        });
 */
 
     // Declare global variables to store starting touch positions
        let startY = 0;
        let startX = 0;

        function scrollerPayout(scroll, chart) {
            scroll.preventDefault();
            const datalength = chart.data.labels.length;

            let deltaY = 0;
            let deltaX = 0;

            if (scroll.type === 'wheel') {
                // For mouse wheel events
                deltaY = scroll.deltaY;
                deltaX = scroll.deltaX;
            } else if (scroll.type === 'touchmove') {
                // For touch events, calculate delta based on movement from starting positions
                deltaY = scroll.touches[0].clientY - startY;
                // Invert deltaX for a natural right-to-left swipe behavior
                deltaX = -(scroll.touches[0].clientX - startX);

                // Update start positions for the next movement calculation
                startY = scroll.touches[0].clientY;
                startX = scroll.touches[0].clientX;
            }

            if (deltaY !== 0) {
                if (deltaY > 0) {
                    // Scroll down
                    if (chart.config.options.scales.x.max >= datalength) {
                        chart.config.options.scales.x.min = datalength - 3;
                        chart.config.options.scales.x.max = datalength;
                    } else {
                        chart.config.options.scales.x.min += 1;
                        chart.config.options.scales.x.max += 1;
                    }
                } else {
                    // Scroll up
                    if (chart.config.options.scales.x.min <= 0) {
                        chart.config.options.scales.x.min = 0;
                        chart.config.options.scales.x.max = 3;
                    } else {
                        chart.config.options.scales.x.min -= 1;
                        chart.config.options.scales.x.max -= 1;
                    }
                }
            }

            if (deltaX !== 0) {
                if (deltaX > 0) {
                    // Scroll right
                    if (chart.config.options.scales.x.max >= datalength) {
                        chart.config.options.scales.x.min = datalength - 3;
                        chart.config.options.scales.x.max = datalength;
                    } else {
                        chart.config.options.scales.x.min += 1;
                        chart.config.options.scales.x.max += 1;
                    }
                } else {
                    // Scroll left
                    if (chart.config.options.scales.x.min <= 0) {
                        chart.config.options.scales.x.min = 0;
                        chart.config.options.scales.x.max = 3;
                    } else {
                        chart.config.options.scales.x.min -= 1;
                        chart.config.options.scales.x.max -= 1;
                    }
                }
            }

            chart.update();
        }

        // Listen for mouse wheel events
        payoutBarChart.canvas.addEventListener('wheel', (e) => {
        	scrollerPayout(e, payoutBarChart);
        });

        // Track touch start and movement
        payoutBarChart.canvas.addEventListener('touchstart', (e) => {
            startY = e.touches[0].clientY;  
            startX = e.touches[0].clientX;  
        });

        payoutBarChart.canvas.addEventListener('touchmove', (e) => {
        	scrollerPayout(e, payoutBarChart);
        });
    }
</script>

<style></style>

</body>
</html>
