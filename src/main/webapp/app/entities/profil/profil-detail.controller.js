(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProfilDetailController', ProfilDetailController);

    ProfilDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profil', 'Employee', 'Employer', 'Agriculteur', 'TraderAGB', 'TraderCA', 'Annonce', 'AnnonceHistorique', 'View', 'AnnonceChangement', 'ECommande', 'ECommandeHistorique', 'Transaction'];

    function ProfilDetailController($scope, $rootScope, $stateParams, previousState, entity, Profil, Employee, Employer, Agriculteur, TraderAGB, TraderCA, Annonce, AnnonceHistorique, View, AnnonceChangement, ECommande, ECommandeHistorique, Transaction) {
        var vm = this;

        vm.profil = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:profilUpdate', function(event, result) {
            vm.profil = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
