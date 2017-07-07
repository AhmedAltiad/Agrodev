(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GPODetailController', GPODetailController);

    GPODetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GPO'];

    function GPODetailController($scope, $rootScope, $stateParams, previousState, entity, GPO) {
        var vm = this;

        vm.gPO = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:gPOUpdate', function(event, result) {
            vm.gPO = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
