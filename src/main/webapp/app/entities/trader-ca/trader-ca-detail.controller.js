(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderCADetailController', TraderCADetailController);

    TraderCADetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraderCA', 'Profil', 'Desk', 'Trade'];

    function TraderCADetailController($scope, $rootScope, $stateParams, previousState, entity, TraderCA, Profil, Desk, Trade) {
        var vm = this;

        vm.traderCA = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:traderCAUpdate', function(event, result) {
            vm.traderCA = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
