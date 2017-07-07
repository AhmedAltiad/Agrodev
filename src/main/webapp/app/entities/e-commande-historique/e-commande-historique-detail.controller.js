(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeHistoriqueDetailController', ECommandeHistoriqueDetailController);

    ECommandeHistoriqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ECommandeHistorique', 'AnnonceHistorique', 'Profil', 'TransactionHistorique'];

    function ECommandeHistoriqueDetailController($scope, $rootScope, $stateParams, previousState, entity, ECommandeHistorique, AnnonceHistorique, Profil, TransactionHistorique) {
        var vm = this;

        vm.eCommandeHistorique = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:eCommandeHistoriqueUpdate', function(event, result) {
            vm.eCommandeHistorique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
