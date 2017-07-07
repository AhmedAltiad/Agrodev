(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDetailsDetailController', CommandeDetailsDetailController);

    CommandeDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommandeDetails', 'Variete', 'Agriculteur', 'Commande'];

    function CommandeDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, CommandeDetails, Variete, Agriculteur, Commande) {
        var vm = this;

        vm.commandeDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:commandeDetailsUpdate', function(event, result) {
            vm.commandeDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
