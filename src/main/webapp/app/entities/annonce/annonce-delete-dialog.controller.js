(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('AnnonceDeleteController',AnnonceDeleteController);

    AnnonceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Annonce'];

    function AnnonceDeleteController($uibModalInstance, entity, Annonce) {
        var vm = this;

        vm.annonce = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Annonce.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
