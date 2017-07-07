(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpActualiteDetailController', EmpActualiteDetailController);

    EmpActualiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmpActualite', 'Localite'];

    function EmpActualiteDetailController($scope, $rootScope, $stateParams, previousState, entity, EmpActualite, Localite) {
        var vm = this;

        vm.empActualite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:empActualiteUpdate', function(event, result) {
            vm.empActualite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
