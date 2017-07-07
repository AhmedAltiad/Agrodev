(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployeeDialogController', EmployeeDialogController);

    EmployeeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employee', 'Profil', 'EmpCV', 'EmpAnnonce'];

    function EmployeeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Employee, Profil, EmpCV, EmpAnnonce) {
        var vm = this;

        vm.employee = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profils = Profil.query({filter: 'employee-is-null'});
        $q.all([vm.employee.$promise, vm.profils.$promise]).then(function() {
            if (!vm.employee.profil || !vm.employee.profil.id) {
                return $q.reject();
            }
            return Profil.get({id : vm.employee.profil.id}).$promise;
        }).then(function(profil) {
            vm.profils.push(profil);
        });
        vm.empcvs = EmpCV.query();
        vm.empannonces = EmpAnnonce.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employee.id !== null) {
                Employee.update(vm.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save(vm.employee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:employeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
