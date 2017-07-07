(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SouscategorieDetailController', SouscategorieDetailController);

    SouscategorieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Souscategorie', 'Produit', 'Categorie'];

    function SouscategorieDetailController($scope, $rootScope, $stateParams, previousState, entity, Souscategorie, Produit, Categorie) {
        var vm = this;

        vm.souscategorie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:souscategorieUpdate', function(event, result) {
            vm.souscategorie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
