(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceChangementDeleteController',AnnonceChangementDeleteController);

    AnnonceChangementDeleteController.$inject = ['$uibModalInstance', 'entity', 'AnnonceChangement'];

    function AnnonceChangementDeleteController($uibModalInstance, entity, AnnonceChangement) {
        var vm = this;

        vm.annonceChangement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AnnonceChangement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
