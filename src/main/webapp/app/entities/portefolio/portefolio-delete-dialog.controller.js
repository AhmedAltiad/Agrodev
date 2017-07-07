(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PortefolioDeleteController',PortefolioDeleteController);

    PortefolioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Portefolio'];

    function PortefolioDeleteController($uibModalInstance, entity, Portefolio) {
        var vm = this;

        vm.portefolio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Portefolio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
