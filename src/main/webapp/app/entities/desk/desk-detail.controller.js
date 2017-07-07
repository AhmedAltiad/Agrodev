(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DeskDetailController', DeskDetailController);

    DeskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Desk', 'TraderCA', 'TraderAGB', 'Gouvernorat'];

    function DeskDetailController($scope, $rootScope, $stateParams, previousState, entity, Desk, TraderCA, TraderAGB, Gouvernorat) {
        var vm = this;

        vm.desk = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:deskUpdate', function(event, result) {
            vm.desk = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
