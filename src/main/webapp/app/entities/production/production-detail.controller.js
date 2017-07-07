(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProductionDetailController', ProductionDetailController);

    ProductionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Production', 'Agriculteur', 'Variete'];

    function ProductionDetailController($scope, $rootScope, $stateParams, previousState, entity, Production, Agriculteur, Variete) {
        var vm = this;

        vm.production = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:productionUpdate', function(event, result) {
            vm.production = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
