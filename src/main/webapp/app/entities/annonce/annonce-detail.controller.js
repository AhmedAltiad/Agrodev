(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceDetailController', AnnonceDetailController);

    AnnonceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Annonce', 'Variete', 'Profil', 'Localite', 'Palier', 'ECommande'];

    function AnnonceDetailController($scope, $rootScope, $stateParams, previousState, entity, Annonce, Variete, Profil, Localite, Palier, ECommande) {
        var vm = this;

        vm.annonce = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:annonceUpdate', function(event, result) {
            vm.annonce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
