(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProfilDialogController', ProfilDialogController);

    ProfilDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profil', 'Employee', 'Employer', 'Agriculteur', 'TraderAGB', 'TraderCA', 'Annonce', 'AnnonceHistorique', 'View', 'AnnonceChangement', 'ECommande', 'ECommandeHistorique', 'Transaction'];

    function ProfilDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profil, Employee, Employer, Agriculteur, TraderAGB, TraderCA, Annonce, AnnonceHistorique, View, AnnonceChangement, ECommande, ECommandeHistorique, Transaction) {
        var vm = this;

        vm.profil = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();
        vm.employers = Employer.query();
        vm.agriculteurs = Agriculteur.query();
        vm.traderagbs = TraderAGB.query();
        vm.tradercas = TraderCA.query();
        vm.annonces = Annonce.query();
        vm.annoncehistoriques = AnnonceHistorique.query();
        vm.views = View.query();
        vm.annoncechangements = AnnonceChangement.query();
        vm.ecommandes = ECommande.query();
        vm.ecommandehistoriques = ECommandeHistorique.query();
        vm.transactions = Transaction.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.profil.id !== null) {
                Profil.update(vm.profil, onSaveSuccess, onSaveError);
            } else {
                Profil.save(vm.profil, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:profilUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
