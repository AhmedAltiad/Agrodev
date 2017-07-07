(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('LocaliteDialogController', LocaliteDialogController);

    LocaliteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Localite', 'Delegation', 'Employer', 'Annonce', 'Produit', 'EmpActualite'];

    function LocaliteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Localite, Delegation, Employer, Annonce, Produit, EmpActualite) {
        var vm = this;

        vm.localite = entity;
        vm.clear = clear;
        vm.save = save;
        vm.delegations = Delegation.query();
        vm.employers = Employer.query();
        vm.annonces = Annonce.query();
        vm.produits = Produit.query();
        vm.empactualites = EmpActualite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.localite.id !== null) {
                Localite.update(vm.localite, onSaveSuccess, onSaveError);
            } else {
                Localite.save(vm.localite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:localiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
