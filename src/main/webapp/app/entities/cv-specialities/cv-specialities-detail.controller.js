(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CvSpecialitiesDetailController', CvSpecialitiesDetailController);

    CvSpecialitiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CvSpecialities', 'EmpCV'];

    function CvSpecialitiesDetailController($scope, $rootScope, $stateParams, previousState, entity, CvSpecialities, EmpCV) {
        var vm = this;

        vm.cvSpecialities = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:cvSpecialitiesUpdate', function(event, result) {
            vm.cvSpecialities = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
