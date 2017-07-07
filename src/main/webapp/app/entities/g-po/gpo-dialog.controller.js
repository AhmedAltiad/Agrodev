(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GPODialogController', GPODialogController);

    GPODialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GPO'];

    function GPODialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GPO) {
        var vm = this;

        vm.gPO = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gPO.id !== null) {
                GPO.update(vm.gPO, onSaveSuccess, onSaveError);
            } else {
                GPO.save(vm.gPO, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:gPOUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreationEntreprise = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
