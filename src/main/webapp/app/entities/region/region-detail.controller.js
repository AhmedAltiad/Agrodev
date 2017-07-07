(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('RegionDetailController', RegionDetailController);

    RegionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Region', 'Pays', 'Gouvernorat'];

    function RegionDetailController($scope, $rootScope, $stateParams, previousState, entity, Region, Pays, Gouvernorat) {
        var vm = this;

        vm.region = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:regionUpdate', function(event, result) {
            vm.region = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
