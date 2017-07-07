(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('VarieteDialogController', VarieteDialogController);

    VarieteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Variete', 'Produit', 'Annonce', 'CommandeDetails', 'Trade', 'Production', 'Portefolio'];

    function VarieteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Variete, Produit, Annonce, CommandeDetails, Trade, Production, Portefolio) {
        var vm = this;

        vm.variete = entity;
        vm.clear = clear;
        vm.save = save;
        vm.produits = Produit.query();
        vm.annonces = Annonce.query();
        vm.commandedetails = CommandeDetails.query();
        vm.trades = Trade.query();
        vm.productions = Production.query();
        vm.portefolios = Portefolio.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.variete.id !== null) {
                Variete.update(vm.variete, onSaveSuccess, onSaveError);
            } else {
                Variete.save(vm.variete, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:varieteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
