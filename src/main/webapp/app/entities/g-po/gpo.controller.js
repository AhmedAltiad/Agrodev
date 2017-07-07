(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GPOController', GPOController);

    GPOController.$inject = ['GPO', 'GPOSearch'];

    function GPOController(GPO, GPOSearch) {

        var vm = this;

        vm.gPOS = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            GPO.query(function(result) {
                vm.gPOS = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            GPOSearch.query({query: vm.searchQuery}, function(result) {
                vm.gPOS = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
