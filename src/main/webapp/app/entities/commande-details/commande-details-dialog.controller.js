(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDetailsDialogController', CommandeDetailsDialogController);

    CommandeDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CommandeDetails', 'Variete', 'Agriculteur', 'Commande'];

    function CommandeDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CommandeDetails, Variete, Agriculteur, Commande) {
        var vm = this;

        vm.commandeDetails = entity;
        vm.clear = clear;
        vm.save = save;
        vm.varietes = Variete.query();
        vm.agriculteurs = Agriculteur.query();
        vm.commandes = Commande.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commandeDetails.id !== null) {
                CommandeDetails.update(vm.commandeDetails, onSaveSuccess, onSaveError);
            } else {
                CommandeDetails.save(vm.commandeDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:commandeDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
