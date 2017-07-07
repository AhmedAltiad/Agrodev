(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PalierDetailController', PalierDetailController);

    PalierDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Palier', 'Produit', 'Annonce'];

    function PalierDetailController($scope, $rootScope, $stateParams, previousState, entity, Palier, Produit, Annonce) {
        var vm = this;

        vm.palier = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:palierUpdate', function(event, result) {
            vm.palier = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
