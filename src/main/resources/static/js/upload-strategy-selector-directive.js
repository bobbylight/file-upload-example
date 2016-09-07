angular.module('uploadAppDirectives').directive('uploadStrategySelector', [
function() {

    return {

        restrict: 'E',
        templateUrl: 'directives/uploadStrategySelector.html',

        scope: {
            uploadStrategy: '='
        },

        link: function(scope, element, attrs) {

            scope.setUploadStrategy = function(uploadStrategy) {
                scope.uploadStrategy = uploadStrategy;
            };

            if (!scope.uploadStrategy) {
                scope.uploadStrategy = 'part';
            }
        }
    };

}]);