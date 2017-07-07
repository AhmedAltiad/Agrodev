(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceHistoriqueDetailController', AnnonceHistoriqueDetailController);

    AnnonceHistoriqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AnnonceHistorique', 'AnnonceChangement', 'View', 'Profil', 'ECommandeHistorique'];

    function AnnonceHistoriqueDetailController($scope, $rootScope, $stateParams, previousState, entity, AnnonceHistorique, AnnonceChangement, View, Profil, ECommandeHistorique) {
        var vm = this;

        vm.annonceHistorique = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:annonceHistoriqueUpdate', function(event, result) {
            vm.annonceHistorique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
