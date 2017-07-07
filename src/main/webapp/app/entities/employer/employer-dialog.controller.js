(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployerDialogController', EmployerDialogController);

    EmployerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employer', 'Profil', 'Localite', 'EmpAnnonce'];

    function EmployerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Employer, Profil, Localite, EmpAnnonce) {
        var vm = this;

        vm.employer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profils = Profil.query({filter: 'employer-is-null'});
        $q.all([vm.employer.$promise, vm.profils.$promise]).then(function() {
            if (!vm.employer.profil || !vm.employer.profil.id) {
                return $q.reject();
            }
            return Profil.get({id : vm.employer.profil.id}).$promise;
        }).then(function(profil) {
            vm.profils.push(profil);
        });
        vm.localites = Localite.query();
        vm.empannonces = EmpAnnonce.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employer.id !== null) {
                Employer.update(vm.employer, onSaveSuccess, onSaveError);
            } else {
                Employer.save(vm.employer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:employerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
