(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProduitDetailController', ProduitDetailController);

    ProduitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Produit', 'Variete', 'Localite', 'Palier', 'Souscategorie'];

    function ProduitDetailController($scope, $rootScope, $stateParams, previousState, entity, Produit, Variete, Localite, Palier, Souscategorie) {
        var vm = this;

        vm.produit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:produitUpdate', function(event, result) {
            vm.produit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
