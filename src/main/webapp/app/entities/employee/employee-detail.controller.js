(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployeeDetailController', EmployeeDetailController);

    EmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employee', 'Profil', 'EmpCV', 'EmpAnnonce'];

    function EmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, Employee, Profil, EmpCV, EmpAnnonce) {
        var vm = this;

        vm.employee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:employeeUpdate', function(event, result) {
            vm.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
