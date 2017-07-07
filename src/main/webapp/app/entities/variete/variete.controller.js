(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('VarieteController', VarieteController);

    VarieteController.$inject = ['Variete', 'VarieteSearch'];

    function VarieteController(Variete, VarieteSearch) {

        var vm = this;

        vm.varietes = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Variete.query(function(result) {
                vm.varietes = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            VarieteSearch.query({query: vm.searchQuery}, function(result) {
                vm.varietes = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
