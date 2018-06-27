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


var myApp = angular.module('configuration', []);

myApp.controller('configuration', function ($scope, $http) {
    $http.get('/config/algorithm/data').then(function (response) {
        $scope.config = [];
        angular.forEach(response.data, function (element) {
            $scope.config.push(element);
        });
        $scope.tsneConfig = $scope.config.filter(function(item) {
            return item.algorithmName === 'tsne';
        })[0];
    });

    $scope.submitForm = function () {
        console.log($scope.tsneConfig);
        $http.post('/config/algorithm',
            JSON.stringify($scope.tsneConfig)).success(function(){
                alert("Configurations saved correctly!")
            });
    }

});