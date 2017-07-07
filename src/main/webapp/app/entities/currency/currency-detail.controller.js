(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CurrencyDetailController', CurrencyDetailController);

    CurrencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Currency', 'Commande', 'Trade'];

    function CurrencyDetailController($scope, $rootScope, $stateParams, previousState, entity, Currency, Commande, Trade) {
        var vm = this;

        vm.currency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:currencyUpdate', function(event, result) {
            vm.currency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
