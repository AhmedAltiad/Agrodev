(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GouvernoratDetailController', GouvernoratDetailController);

    GouvernoratDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gouvernorat', 'Region', 'Delegation', 'Desk'];

    function GouvernoratDetailController($scope, $rootScope, $stateParams, previousState, entity, Gouvernorat, Region, Delegation, Desk) {
        var vm = this;

        vm.gouvernorat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:gouvernoratUpdate', function(event, result) {
            vm.gouvernorat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
