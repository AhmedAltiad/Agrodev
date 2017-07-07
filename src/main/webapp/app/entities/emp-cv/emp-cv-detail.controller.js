(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpCVDetailController', EmpCVDetailController);

    EmpCVDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmpCV', 'Formation', 'Employee', 'CvSpecialities'];

    function EmpCVDetailController($scope, $rootScope, $stateParams, previousState, entity, EmpCV, Formation, Employee, CvSpecialities) {
        var vm = this;

        vm.empCV = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:empCVUpdate', function(event, result) {
            vm.empCV = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
