angular.module('uploadAppDirectives').directive('uploadBanner', [
function() {

    return {

        restrict: 'E',
        templateUrl: 'directives/banner.html',
        link: function($scope, element, attrs) {

        }
    };

}]);