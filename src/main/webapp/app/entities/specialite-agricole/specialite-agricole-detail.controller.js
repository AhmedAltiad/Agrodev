(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SpecialiteAgricoleDetailController', SpecialiteAgricoleDetailController);

    SpecialiteAgricoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SpecialiteAgricole', 'Agriculteur'];

    function SpecialiteAgricoleDetailController($scope, $rootScope, $stateParams, previousState, entity, SpecialiteAgricole, Agriculteur) {
        var vm = this;

        vm.specialiteAgricole = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:specialiteAgricoleUpdate', function(event, result) {
            vm.specialiteAgricole = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
