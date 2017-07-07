(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TransactionController', TransactionController);

    TransactionController.$inject = ['Transaction', 'TransactionSearch'];

    function TransactionController(Transaction, TransactionSearch) {

        var vm = this;

        vm.transactions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Transaction.query(function(result) {
                vm.transactions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TransactionSearch.query({query: vm.searchQuery}, function(result) {
                vm.transactions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
