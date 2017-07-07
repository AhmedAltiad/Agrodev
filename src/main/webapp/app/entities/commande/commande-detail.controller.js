(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commande', 'CommandeDetails', 'Currency', 'TraderAGB', 'Agriculteur'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, Commande, CommandeDetails, Currency, TraderAGB, Agriculteur) {
        var vm = this;

        vm.commande = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
