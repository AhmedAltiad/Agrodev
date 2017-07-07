(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PortefolioDialogController', PortefolioDialogController);

    PortefolioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Portefolio', 'TraderAGB', 'Variete'];

    function PortefolioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Portefolio, TraderAGB, Variete) {
        var vm = this;

        vm.portefolio = entity;
        vm.clear = clear;
        vm.save = save;
        vm.traderagbs = TraderAGB.query();
        vm.varietes = Variete.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.portefolio.id !== null) {
                Portefolio.update(vm.portefolio, onSaveSuccess, onSaveError);
            } else {
                Portefolio.save(vm.portefolio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:portefolioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
