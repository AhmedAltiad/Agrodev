(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SouscategorieDialogController', SouscategorieDialogController);

    SouscategorieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Souscategorie', 'Produit', 'Categorie'];

    function SouscategorieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Souscategorie, Produit, Categorie) {
        var vm = this;

        vm.souscategorie = entity;
        vm.clear = clear;
        vm.save = save;
        vm.produits = Produit.query();
        vm.categories = Categorie.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.souscategorie.id !== null) {
                Souscategorie.update(vm.souscategorie, onSaveSuccess, onSaveError);
            } else {
                Souscategorie.save(vm.souscategorie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:souscategorieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
