(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AgriculteurDetailController', AgriculteurDetailController);

    AgriculteurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agriculteur', 'Profil', 'ActiviteAgricole', 'SpecialiteAgricole', 'CommandeDetails', 'Commande', 'Production'];

    function AgriculteurDetailController($scope, $rootScope, $stateParams, previousState, entity, Agriculteur, Profil, ActiviteAgricole, SpecialiteAgricole, CommandeDetails, Commande, Production) {
        var vm = this;

        vm.agriculteur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:agriculteurUpdate', function(event, result) {
            vm.agriculteur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
