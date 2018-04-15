;(function () {

    'use strict';


    var fullHeight = function () {
        $('.js-fullheight').css('height', $(window).height());
        $(window).resize(function () {
            $('.js-fullheight').css('height', $(window).height());
        });
    };

    var parallax = function () {
        $(window).stellar({
            horizontalScrolling: false,
            hideDistantElements: false,
            responsive: true

        });
    };

    var testimonialCarousel = function () {
        var owl = $('.owl-carousel-fullwidth');
        owl.owlCarousel({
            items: 1,
            loop: true,
            margin: 0,
            responsiveClass: true,
            nav: false,
            dots: true,
            smartSpeed: 500,
            autoHeight: true
        });
    };


    // Animations

    var contentWayPoint = function () {
        var i = 0;
        $('.animate-box').waypoint(function (direction) {

            if (direction === 'down' && !$(this.element).hasClass('animated')) {

                i++;

                $(this.element).addClass('item-animate');
                setTimeout(function () {

                    $('body .animate-box.item-animate').each(function (k) {
                        var el = $(this);
                        setTimeout(function () {
                            var effect = el.data('animate-effect');
                            if (effect === 'fadeIn') {
                                el.addClass('fadeIn animated');
                            } else if (effect === 'fadeInLeft') {
                                el.addClass('fadeInLeft animated');
                            } else if (effect === 'fadeInRight') {
                                el.addClass('fadeInRight animated');
                            } else {
                                el.addClass('fadeInUp animated');
                            }

                            el.removeClass('item-animate');
                        }, k * 200, 'easeInOutExpo');
                    });

                }, 100);

            }

        }, {offset: '85%'});
    };

    var counter = function () {
        $('.js-counter').countTo({
            formatter: function (value, options) {
                return value.toFixed(options.decimals);
            },
        });
    };

    var counterWayPoint = function () {
        if ($('#counter-animate').length > 0) {
            $('#counter-animate').waypoint(function (direction) {

                if (direction === 'down' && !$(this.element).hasClass('animated')) {
                    setTimeout(counter, 400);
                    $(this.element).addClass('animated');

                }
            }, {offset: '90%'});
        }
    };

    var burgerMenu = function () {

        $('.js-fh5co-nav-toggle').on('click', function (event) {
            event.preventDefault();
            var $this = $(this);

            if ($('body').hasClass('offcanvas')) {
                $this.removeClass('active');
                $('body').removeClass('offcanvas');
            } else {
                $this.addClass('active');
                $('body').addClass('offcanvas');
            }
        });


    };

    // Document on load.
    $(function () {
        fullHeight();
        parallax();
        testimonialCarousel();
        contentWayPoint();
        counterWayPoint();
        burgerMenu();
    });


}());


var myApp = angular.module('imported', []);

myApp.controller('imported', function ($scope, $sce, $http) {
      $http.get('/processed/data').then(function (response) {
        $scope.array = [];
        angular.forEach(response.data, function (element) {
            $scope.array.push(element);
        });
    });

    function getRandomColor() {
      var letters = '0123456789ABCDEF';
      var color = '#';
      for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      return color;
    }


    $scope.showValues = function (values){
        var valuesArray = values.split(",");
        var valuesTable = '<table style="width:100%"><tr><th>Key</th><th>Value</th></tr>'
        function prettyPrint(element) {
            var keyValue = element.split(":");
            if(keyValue.length >= 2){
               valuesTable += '<tr><th>'+keyValue[0]+'</th><th>'+keyValue[1]+'</th></tr>';
            }
        }
        valuesArray.forEach(prettyPrint);
        valuesTable += '</table>';
        console.log(valuesTable);
        //$scope.ad = { 'dataTooltip' : '<p class="values">'+ values +'</p>'};
        $scope.ad = { 'dataTooltip' : valuesTable };

        $scope.$apply(function () {
               $scope.dataTooltip = $sce.trustAsHtml($scope.ad.dataTooltip);
        });
    }

    $scope.showChart = function (dataId, algorithm) {
        $scope.clusters = [];
        $scope.array1 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.array2 = [];
        $scope.arraySick = [];
        $scope.arrayHealthy = [];
        $(".overlay").show();
        $http.get('/processed/data/chart?importID=' + dataId + '&algorithm=' + algorithm).then(function (response) {
            console.log(response);
            angular.forEach(response.data, function (element) {
                if (element.diagnosis == 'healthy') {
                    var row = {
                        x: element.axisX,
                        y: element.axisY,
                        r: 7,
                        orig: element.originalDataValues
                    };
                    $scope.arrayHealthy.push(row);
                } else if (element.diagnosis == 'sick') {
                    var row = {
                        x: element.axisX,
                        y: element.axisY,
                        r: 7,
                        orig: element.originalDataValues

                    };
                    $scope.arraySick.push(row);
                }
            });
        });

        $scope.clusters.push($scope.arraySick);
        $scope.clusters.push($scope.arrayHealthy);


        $scope.chartBubble = new Chart(document.getElementById("canvasBubble"), {
            type: 'bubble',
            data: {
                labels: "Diagnosis",
                datasets: [{
                    label: ["Sick" + $scope.arraySick],
                    backgroundColor: "rgba(211,50,21,0.6)",
                    borderColor: "rgba(176,34,44,0.9)",
                    title: "Sick",
                    data: $scope.arraySick,
                    display: true
                }, {
                    label: ["Healthy"],
                    backgroundColor: "rgba(60,186,159,0.2)",
                    borderColor: "rgba(60,186,159,1)",
                    title: "Healthy",
                    data: $scope.arrayHealthy,
                    display: true
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Data processed using algorithm: ' + algorithm
                },
                scales: {
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: "Y"
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: "X"
                        }
                    }]
                },
                onClick: function(e) {
                    var element = this.getElementAtEvent(e);
                    if (element.length) {
                        var dataSet = element[0]._datasetIndex;
                        var index = element[0]._index;
                        $scope.showValues(element[0]._chart.config.data.datasets[dataSet].data[index].orig);
                    }
                }

            }
        });
        $(".chart").show();

        $(".overlay").hide();

        document.getElementById("light").style.display = 'block';
        document.getElementById("fade").style.display = 'block';

    };

    $scope.hideChart = function () {
        document.getElementById('light').style.display = 'none';
        document.getElementById('fade').style.display = 'none';
        $scope.chartBubble.destroy();
    }

});