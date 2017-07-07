(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TradeDialogController', TradeDialogController);

    TradeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trade', 'Currency', 'Variete', 'TraderAGB', 'TraderCA'];

    function TradeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trade, Currency, Variete, TraderAGB, TraderCA) {
        var vm = this;

        vm.trade = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.currencies = Currency.query();
        vm.varietes = Variete.query();
        vm.traderagbs = TraderAGB.query();
        vm.tradercas = TraderCA.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trade.id !== null) {
                Trade.update(vm.trade, onSaveSuccess, onSaveError);
            } else {
                Trade.save(vm.trade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:tradeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfOrder = false;
        vm.datePickerOpenStatus.dateOfDelivery = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
