var myApp = angular.module('uploader', []);

myApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

myApp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
                headers: {'Content-Type': undefined}
            })
            .success(function(){
                $(".overlay").hide();
                alert("File uploaded correctly!");
            })
            .error(function(){
                $(".overlay").hide();
                alert("Some error occurred!");
            });
    }
}]);

myApp.controller('uploaderCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
    $scope.uploadFile = function(){
        $(".overlay").show();
        var file = $scope.myFile;
        var uploadUrl = "/import/fileUpload";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };

}]);