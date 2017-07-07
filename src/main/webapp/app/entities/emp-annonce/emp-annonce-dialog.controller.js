(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpAnnonceDialogController', EmpAnnonceDialogController);

    EmpAnnonceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmpAnnonce', 'Employer', 'Employee'];

    function EmpAnnonceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmpAnnonce, Employer, Employee) {
        var vm = this;

        vm.empAnnonce = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employers = Employer.query();
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empAnnonce.id !== null) {
                EmpAnnonce.update(vm.empAnnonce, onSaveSuccess, onSaveError);
            } else {
                EmpAnnonce.save(vm.empAnnonce, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:empAnnonceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.initDate = false;
        vm.datePickerOpenStatus.finDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
