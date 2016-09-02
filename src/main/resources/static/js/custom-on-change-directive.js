// Adds a custom on-change handler to an input via a "custom-on-change" attribute.  This is used to
// work around the lack of ng-model support for file inputs.
// The custom change handler receives the file import event.
// See:
// http://stackoverflow.com/questions/17922557/angularjs-how-to-check-for-changes-in-file-input-fields/
angular.module('uploadAppDirectives').directive('customOnChange', [
function() {

    return {

        restrict: 'A',
        link: function(scope, element, attrs) {
            var onChangeHandler = scope.$eval(attrs.customOnChange);
            element.bind('change', onChangeHandler);
        }
    };

}]);