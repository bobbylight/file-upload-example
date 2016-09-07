angular.module('uploadAppControllers').controller('MainCtrl', [ '$scope',
function($scope) {

    var vm = this;
    vm.response = {};
    vm.uploadStrategy = 'part';

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

        vm.submittedUrl = '/upload/' + vm.uploadStrategy; // Just so we can display what we did in the UI

        var formData = new FormData();
        formData.append('file', vm.file);

        $.ajax({
            url: vm.submittedUrl,
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(data) {
                $scope.$apply(function() {
                    vm.errorOccurred = false;
                    vm.uploadSuccessful.call(vm, data);
                });
            },
            error: function(xhr, textStatus, errorThrown) {
                $scope.$apply(function() {
                    vm.errorOccurred = true;
                    vm.error = xhr.responseText;
                    var codeBlock = document.getElementsByClassName('error-json')[0];
                    codeBlock.innerHTML = '';
                    var formattedJson = JSON.stringify(JSON.parse(xhr.responseText), null, 2);
                    codeBlock.appendChild(document.createTextNode(formattedJson));
                    hljs.highlightBlock(codeBlock);
                });
            }
        });
    };
    
    vm.uploadSuccessful = function(data) {

        vm.uploadResponse = JSON.stringify(data);

        vm.response.date = new Date();//.toString();
        vm.response.fileSizeThreshold = data.fileSizeThreshold;
        vm.response.inputStreamType = data.inputStreamType;
        vm.response.multipartResolverType = data.multipartResolverType;
        vm.response.requestType = data.requestType;
    };
    
    vm.updateSelectedFileSize(); // Initialize the UI
}]);