(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ViewDialogController', ViewDialogController);

    ViewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'View', 'Profil', 'AnnonceHistorique'];

    function ViewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, View, Profil, AnnonceHistorique) {
        var vm = this;

        vm.view = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.profils = Profil.query();
        vm.annoncehistoriques = AnnonceHistorique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.view.id !== null) {
                View.update(vm.view, onSaveSuccess, onSaveError);
            } else {
                View.save(vm.view, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:viewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.duration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
