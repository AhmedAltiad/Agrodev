(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('VarieteDetailController', VarieteDetailController);

    VarieteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Variete', 'Produit', 'Annonce', 'CommandeDetails', 'Trade', 'Production', 'Portefolio'];

    function VarieteDetailController($scope, $rootScope, $stateParams, previousState, entity, Variete, Produit, Annonce, CommandeDetails, Trade, Production, Portefolio) {
        var vm = this;

        vm.variete = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:varieteUpdate', function(event, result) {
            vm.variete = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
