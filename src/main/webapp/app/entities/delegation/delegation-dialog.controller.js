(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DelegationDialogController', DelegationDialogController);

    DelegationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Delegation', 'Gouvernorat', 'Localite'];

    function DelegationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Delegation, Gouvernorat, Localite) {
        var vm = this;

        vm.delegation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.gouvernorats = Gouvernorat.query();
        vm.localites = Localite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.delegation.id !== null) {
                Delegation.update(vm.delegation, onSaveSuccess, onSaveError);
            } else {
                Delegation.save(vm.delegation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:delegationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
