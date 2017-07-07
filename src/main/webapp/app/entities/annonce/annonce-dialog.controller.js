(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceDialogController', AnnonceDialogController);

    AnnonceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Annonce', 'Variete', 'Profil', 'Localite', 'Palier', 'ECommande'];

    function AnnonceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Annonce, Variete, Profil, Localite, Palier, ECommande) {
        var vm = this;

        vm.annonce = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.varietes = Variete.query();
        vm.profils = Profil.query();
        vm.localites = Localite.query();
        vm.paliers = Palier.query();
        vm.ecommandes = ECommande.query();

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
            $scope.$emit('agroBourse360SiApp:annonceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;
        vm.datePickerOpenStatus.lastmodifieddate = false;
        vm.datePickerOpenStatus.dateActivation = false;
        vm.datePickerOpenStatus.dateExpiration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
