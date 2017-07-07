(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TransactionHistoriqueController', TransactionHistoriqueController);

    TransactionHistoriqueController.$inject = ['TransactionHistorique', 'TransactionHistoriqueSearch'];

    function TransactionHistoriqueController(TransactionHistorique, TransactionHistoriqueSearch) {

        var vm = this;

        vm.transactionHistoriques = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TransactionHistorique.query(function(result) {
                vm.transactionHistoriques = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TransactionHistoriqueSearch.query({query: vm.searchQuery}, function(result) {
                vm.transactionHistoriques = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
