(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployerDetailController', EmployerDetailController);

    EmployerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employer', 'Profil', 'Localite', 'EmpAnnonce'];

    function EmployerDetailController($scope, $rootScope, $stateParams, previousState, entity, Employer, Profil, Localite, EmpAnnonce) {
        var vm = this;

        vm.employer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:employerUpdate', function(event, result) {
            vm.employer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
