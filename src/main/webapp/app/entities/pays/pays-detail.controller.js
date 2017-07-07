(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PaysDetailController', PaysDetailController);

    PaysDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pays', 'Region'];

    function PaysDetailController($scope, $rootScope, $stateParams, previousState, entity, Pays, Region) {
        var vm = this;

        vm.pays = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:paysUpdate', function(event, result) {
            vm.pays = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
