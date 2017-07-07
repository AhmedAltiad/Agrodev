(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderAGBController', TraderAGBController);

    TraderAGBController.$inject = ['TraderAGB', 'TraderAGBSearch'];

    function TraderAGBController(TraderAGB, TraderAGBSearch) {

        var vm = this;

        vm.traderAGBS = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TraderAGB.query(function(result) {
                vm.traderAGBS = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TraderAGBSearch.query({query: vm.searchQuery}, function(result) {
                vm.traderAGBS = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
