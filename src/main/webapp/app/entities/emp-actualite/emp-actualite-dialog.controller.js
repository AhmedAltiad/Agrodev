(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpActualiteDialogController', EmpActualiteDialogController);

    EmpActualiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmpActualite', 'Localite'];

    function EmpActualiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmpActualite, Localite) {
        var vm = this;

        vm.empActualite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.localites = Localite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empActualite.id !== null) {
                EmpActualite.update(vm.empActualite, onSaveSuccess, onSaveError);
            } else {
                EmpActualite.save(vm.empActualite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:empActualiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.initDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
