(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TradeController', TradeController);

    TradeController.$inject = ['Trade', 'TradeSearch'];

    function TradeController(Trade, TradeSearch) {

        var vm = this;

        vm.trades = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Trade.query(function(result) {
                vm.trades = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TradeSearch.query({query: vm.searchQuery}, function(result) {
                vm.trades = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
