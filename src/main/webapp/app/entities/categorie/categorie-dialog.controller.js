(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CategorieDialogController', CategorieDialogController);

    CategorieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Categorie', 'Souscategorie'];

    function CategorieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Categorie, Souscategorie) {
        var vm = this;

        vm.categorie = entity;
        vm.clear = clear;
        vm.save = save;
        vm.souscategories = Souscategorie.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categorie.id !== null) {
                Categorie.update(vm.categorie, onSaveSuccess, onSaveError);
            } else {
                Categorie.save(vm.categorie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:categorieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
