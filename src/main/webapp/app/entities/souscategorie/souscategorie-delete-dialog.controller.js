(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SouscategorieDeleteController',SouscategorieDeleteController);

    SouscategorieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Souscategorie'];

    function SouscategorieDeleteController($uibModalInstance, entity, Souscategorie) {
        var vm = this;

        vm.souscategorie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Souscategorie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
