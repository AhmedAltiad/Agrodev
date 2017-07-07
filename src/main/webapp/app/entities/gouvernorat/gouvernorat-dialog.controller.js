(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GouvernoratDialogController', GouvernoratDialogController);

    GouvernoratDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gouvernorat', 'Region', 'Delegation', 'Desk'];

    function GouvernoratDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gouvernorat, Region, Delegation, Desk) {
        var vm = this;

        vm.gouvernorat = entity;
        vm.clear = clear;
        vm.save = save;
        vm.regions = Region.query();
        vm.delegations = Delegation.query();
        vm.desks = Desk.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gouvernorat.id !== null) {
                Gouvernorat.update(vm.gouvernorat, onSaveSuccess, onSaveError);
            } else {
                Gouvernorat.save(vm.gouvernorat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:gouvernoratUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
