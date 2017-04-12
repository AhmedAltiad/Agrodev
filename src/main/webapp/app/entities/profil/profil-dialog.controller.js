(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('ProfilDialogController', ProfilDialogController);

    ProfilDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profil', 'Annonce'];

    function ProfilDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profil, Annonce) {
        var vm = this;

        vm.profil = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.annonces = Annonce.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.profil.id !== null) {
                Profil.update(vm.profil, onSaveSuccess, onSaveError);
            } else {
                Profil.save(vm.profil, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourseApp:profilUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dob = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
