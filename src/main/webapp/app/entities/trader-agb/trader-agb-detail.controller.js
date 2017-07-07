(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderAGBDetailController', TraderAGBDetailController);

    TraderAGBDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraderAGB', 'Profil', 'Desk', 'Commande', 'Trade', 'Portefolio'];

    function TraderAGBDetailController($scope, $rootScope, $stateParams, previousState, entity, TraderAGB, Profil, Desk, Commande, Trade, Portefolio) {
        var vm = this;

        vm.traderAGB = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:traderAGBUpdate', function(event, result) {
            vm.traderAGB = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
