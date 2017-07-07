(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceChangementDialogController', AnnonceChangementDialogController);

    AnnonceChangementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AnnonceChangement', 'Profil', 'AnnonceHistorique'];

    function AnnonceChangementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AnnonceChangement, Profil, AnnonceHistorique) {
        var vm = this;

        vm.annonceChangement = entity;
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
            if (vm.annonceChangement.id !== null) {
                AnnonceChangement.update(vm.annonceChangement, onSaveSuccess, onSaveError);
            } else {
                AnnonceChangement.save(vm.annonceChangement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:annonceChangementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
