angular.module('uploadAppControllers').controller('MainCtrl', [ '$scope',
function($scope) {

    var vm = this;
    vm.response = {};

    vm.fileSelected = function(e) {

        // NOTE: "this" scope is the file input here, not the controller
        vm.file = e.target.files[0];

        // Run in $apply since this is a DOM callback and not presently in a digest cycle
        $scope.$apply(function() {
            vm.updateSelectedFileSize();
        });
    };

    vm.updateSelectedFileSize = function() {
        if (vm.file) {
            vm.selectedFileSize = vm.file.size + ' bytes';
        }
        else {
            vm.selectedFileSize = '<nothing selected>';
        }
    };

    vm.uploadFile = function() {

        var formData = new FormData();
        formData.append('file', vm.file);

        $.ajax({
            url: '/upload/commonsFileUploadStreamingApi',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(data) {
                $scope.$apply(function() {
                    vm.uploadSuccessful.call(vm, data);
                });
            }
        });
    };
    
    vm.uploadSuccessful = function(data) {

        vm.uploadResponse = JSON.stringify(data);

        vm.response.date = new Date();//.toString();
        vm.response.partInputStreamType = data.partInputStreamType;
        vm.response.requestType = data.requestType;
    };
    
    vm.name = 'Robert';
    vm.updateSelectedFileSize(); // Initialize the UI
}]);