(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpAnnonceDetailController', EmpAnnonceDetailController);

    EmpAnnonceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmpAnnonce', 'Employer', 'Employee'];

    function EmpAnnonceDetailController($scope, $rootScope, $stateParams, previousState, entity, EmpAnnonce, Employer, Employee) {
        var vm = this;

        vm.empAnnonce = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:empAnnonceUpdate', function(event, result) {
            vm.empAnnonce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
