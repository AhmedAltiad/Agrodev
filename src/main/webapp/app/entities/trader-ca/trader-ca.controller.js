(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderCAController', TraderCAController);

    TraderCAController.$inject = ['TraderCA', 'TraderCASearch'];

    function TraderCAController(TraderCA, TraderCASearch) {

        var vm = this;

        vm.traderCAS = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TraderCA.query(function(result) {
                vm.traderCAS = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TraderCASearch.query({query: vm.searchQuery}, function(result) {
                vm.traderCAS = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
