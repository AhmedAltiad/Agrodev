(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TradeDetailController', TradeDetailController);

    TradeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trade', 'Currency', 'Variete', 'TraderAGB', 'TraderCA'];

    function TradeDetailController($scope, $rootScope, $stateParams, previousState, entity, Trade, Currency, Variete, TraderAGB, TraderCA) {
        var vm = this;

        vm.trade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:tradeUpdate', function(event, result) {
            vm.trade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
