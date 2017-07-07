(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('LocaliteDetailController', LocaliteDetailController);

    LocaliteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Localite', 'Delegation', 'Employer', 'Annonce', 'Produit', 'EmpActualite'];

    function LocaliteDetailController($scope, $rootScope, $stateParams, previousState, entity, Localite, Delegation, Employer, Annonce, Produit, EmpActualite) {
        var vm = this;

        vm.localite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:localiteUpdate', function(event, result) {
            vm.localite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
