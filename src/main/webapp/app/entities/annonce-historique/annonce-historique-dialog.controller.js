(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceHistoriqueDialogController', AnnonceHistoriqueDialogController);

    AnnonceHistoriqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AnnonceHistorique', 'AnnonceChangement', 'View', 'Profil', 'ECommandeHistorique'];

    function AnnonceHistoriqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AnnonceHistorique, AnnonceChangement, View, Profil, ECommandeHistorique) {
        var vm = this;

        vm.annonceHistorique = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.annoncechangements = AnnonceChangement.query();
        vm.views = View.query();
        vm.profils = Profil.query();
        vm.ecommandehistoriques = ECommandeHistorique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.annonceHistorique.id !== null) {
                AnnonceHistorique.update(vm.annonceHistorique, onSaveSuccess, onSaveError);
            } else {
                AnnonceHistorique.save(vm.annonceHistorique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:annonceHistoriqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;
        vm.datePickerOpenStatus.lastmodifieddate = false;
        vm.datePickerOpenStatus.dateactivation = false;
        vm.datePickerOpenStatus.dateexpiration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
