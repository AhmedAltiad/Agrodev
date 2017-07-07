(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProduitDialogController', ProduitDialogController);

    ProduitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Produit', 'Variete', 'Localite', 'Palier', 'Souscategorie'];

    function ProduitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Produit, Variete, Localite, Palier, Souscategorie) {
        var vm = this;

        vm.produit = entity;
        vm.clear = clear;
        vm.save = save;
        vm.varietes = Variete.query();
        vm.localites = Localite.query();
        vm.paliers = Palier.query();
        vm.souscategories = Souscategorie.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produit.id !== null) {
                Produit.update(vm.produit, onSaveSuccess, onSaveError);
            } else {
                Produit.save(vm.produit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:produitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
