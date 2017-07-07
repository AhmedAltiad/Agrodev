(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ActiviteAgricoleController', ActiviteAgricoleController);

    ActiviteAgricoleController.$inject = ['ActiviteAgricole', 'ActiviteAgricoleSearch'];

    function ActiviteAgricoleController(ActiviteAgricole, ActiviteAgricoleSearch) {

        var vm = this;

        vm.activiteAgricoles = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ActiviteAgricole.query(function(result) {
                vm.activiteAgricoles = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ActiviteAgricoleSearch.query({query: vm.searchQuery}, function(result) {
                vm.activiteAgricoles = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
