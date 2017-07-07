(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GouvernoratDeleteController',GouvernoratDeleteController);

    GouvernoratDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gouvernorat'];

    function GouvernoratDeleteController($uibModalInstance, entity, Gouvernorat) {
        var vm = this;

        vm.gouvernorat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gouvernorat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
