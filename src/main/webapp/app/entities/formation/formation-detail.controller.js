(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('FormationDetailController', FormationDetailController);

    FormationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Formation', 'EmpCV'];

    function FormationDetailController($scope, $rootScope, $stateParams, previousState, entity, Formation, EmpCV) {
        var vm = this;

        vm.formation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:formationUpdate', function(event, result) {
            vm.formation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
