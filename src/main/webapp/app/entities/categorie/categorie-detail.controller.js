(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CategorieDetailController', CategorieDetailController);

    CategorieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Categorie', 'Souscategorie'];

    function CategorieDetailController($scope, $rootScope, $stateParams, previousState, entity, Categorie, Souscategorie) {
        var vm = this;

        vm.categorie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:categorieUpdate', function(event, result) {
            vm.categorie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
