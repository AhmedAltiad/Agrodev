(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeDetailController', ECommandeDetailController);

    ECommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ECommande', 'Annonce', 'Profil', 'Transaction'];

    function ECommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, ECommande, Annonce, Profil, Transaction) {
        var vm = this;

        vm.eCommande = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:eCommandeUpdate', function(event, result) {
            vm.eCommande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
