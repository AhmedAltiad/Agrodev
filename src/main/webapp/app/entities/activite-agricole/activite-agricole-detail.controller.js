(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ActiviteAgricoleDetailController', ActiviteAgricoleDetailController);

    ActiviteAgricoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ActiviteAgricole', 'Agriculteur'];

    function ActiviteAgricoleDetailController($scope, $rootScope, $stateParams, previousState, entity, ActiviteAgricole, Agriculteur) {
        var vm = this;

        vm.activiteAgricole = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:activiteAgricoleUpdate', function(event, result) {
            vm.activiteAgricole = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
