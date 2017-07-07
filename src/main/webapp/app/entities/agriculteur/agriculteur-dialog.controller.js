(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AgriculteurDialogController', AgriculteurDialogController);

    AgriculteurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Agriculteur', 'Profil', 'ActiviteAgricole', 'SpecialiteAgricole', 'CommandeDetails', 'Commande', 'Production'];

    function AgriculteurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Agriculteur, Profil, ActiviteAgricole, SpecialiteAgricole, CommandeDetails, Commande, Production) {
        var vm = this;

        vm.agriculteur = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profils = Profil.query({filter: 'agriculteur-is-null'});
        $q.all([vm.agriculteur.$promise, vm.profils.$promise]).then(function() {
            if (!vm.agriculteur.profil || !vm.agriculteur.profil.id) {
                return $q.reject();
            }
            return Profil.get({id : vm.agriculteur.profil.id}).$promise;
        }).then(function(profil) {
            vm.profils.push(profil);
        });
        vm.activiteagricoles = ActiviteAgricole.query();
        vm.specialiteagricoles = SpecialiteAgricole.query();
        vm.commandedetails = CommandeDetails.query();
        vm.commandes = Commande.query();
        vm.productions = Production.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.agriculteur.id !== null) {
                Agriculteur.update(vm.agriculteur, onSaveSuccess, onSaveError);
            } else {
                Agriculteur.save(vm.agriculteur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:agriculteurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
