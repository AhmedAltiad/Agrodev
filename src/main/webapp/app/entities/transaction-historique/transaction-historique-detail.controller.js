(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TransactionHistoriqueDetailController', TransactionHistoriqueDetailController);

    TransactionHistoriqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TransactionHistorique', 'ECommandeHistorique'];

    function TransactionHistoriqueDetailController($scope, $rootScope, $stateParams, previousState, entity, TransactionHistorique, ECommandeHistorique) {
        var vm = this;

        vm.transactionHistorique = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:transactionHistoriqueUpdate', function(event, result) {
            vm.transactionHistorique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
