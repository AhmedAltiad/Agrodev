(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceChangementDetailController', AnnonceChangementDetailController);

    AnnonceChangementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AnnonceChangement', 'Profil', 'AnnonceHistorique'];

    function AnnonceChangementDetailController($scope, $rootScope, $stateParams, previousState, entity, AnnonceChangement, Profil, AnnonceHistorique) {
        var vm = this;

        vm.annonceChangement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:annonceChangementUpdate', function(event, result) {
            vm.annonceChangement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
