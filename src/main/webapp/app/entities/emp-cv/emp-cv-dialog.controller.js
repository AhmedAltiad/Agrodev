(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpCVDialogController', EmpCVDialogController);

    EmpCVDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmpCV', 'Formation', 'Employee', 'CvSpecialities'];

    function EmpCVDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmpCV, Formation, Employee, CvSpecialities) {
        var vm = this;

        vm.empCV = entity;
        vm.clear = clear;
        vm.save = save;
        vm.formations = Formation.query();
        vm.employees = Employee.query();
        vm.cvspecialities = CvSpecialities.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empCV.id !== null) {
                EmpCV.update(vm.empCV, onSaveSuccess, onSaveError);
            } else {
                EmpCV.save(vm.empCV, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:empCVUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
