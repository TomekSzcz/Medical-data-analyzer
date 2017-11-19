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

myApp.controller('imported', function ($scope, $http) {
    $http.get('/processed/data').then(function (response) {
        $scope.array = [];
        angular.forEach(response.data, function (element) {
            $scope.array.push(element);
        });
    });

    $scope.showChart = function (dataId, algorithm) {
        $scope.arraySick = [];
        $scope.arrayHealthy = [];
        $(".overlay").show();
        console.log("DATA: " + dataId + " " + algorithm);
        $http.get('/processed/data/chart?importID=' + dataId + '&algorithm=' + algorithm).then(function (response) {
            angular.forEach(response.data, function (element) {
                if (element.diagnosis == 'healthy') {
                    var row = {
                        x: element.axisX,
                        y: element.axisY,
                        r: 7
                    };
                    $scope.arrayHealthy.push(row);
                } else if (element.diagnosis == 'sick') {
                    var row = {
                        x: element.axisX,
                        y: element.axisY,
                        r: 7
                    };
                    $scope.arraySick.push(row);
                }
            });
        });

        $(".chart").show();
        new Chart(document.getElementById("canvasBubble"), {
            type: 'bubble',
            data: {
                labels: "Diagnosis",
                datasets: [{
                    label: ["Sick"],
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
                }
            }
        });

        $(".overlay").hide();

        document.getElementById("light").style.display = 'block';
        document.getElementById("fade").style.display = 'block';

    };

    $scope.hideChart = function () {
        document.getElementById('light').style.display = 'none';
        document.getElementById('fade').style.display = 'none';
    }

});