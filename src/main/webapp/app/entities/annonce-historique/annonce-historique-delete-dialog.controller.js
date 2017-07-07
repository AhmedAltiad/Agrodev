(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceHistoriqueDeleteController',AnnonceHistoriqueDeleteController);

    AnnonceHistoriqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'AnnonceHistorique'];

    function AnnonceHistoriqueDeleteController($uibModalInstance, entity, AnnonceHistorique) {
        var vm = this;

        vm.annonceHistorique = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AnnonceHistorique.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
