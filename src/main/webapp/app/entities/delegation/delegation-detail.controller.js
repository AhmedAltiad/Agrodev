(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DelegationDetailController', DelegationDetailController);

    DelegationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Delegation', 'Gouvernorat', 'Localite'];

    function DelegationDetailController($scope, $rootScope, $stateParams, previousState, entity, Delegation, Gouvernorat, Localite) {
        var vm = this;

        vm.delegation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:delegationUpdate', function(event, result) {
            vm.delegation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
