(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('AnnonceDialogController', AnnonceDialogController);

    AnnonceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Annonce', 'Profil'];

    function AnnonceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Annonce, Profil) {
        var vm = this;

        vm.annonce = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.profils = Profil.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.annonce.id !== null) {
                Annonce.update(vm.annonce, onSaveSuccess, onSaveError);
            } else {
                Annonce.save(vm.annonce, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourseApp:annonceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datedebut = false;
        vm.datePickerOpenStatus.datefin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
