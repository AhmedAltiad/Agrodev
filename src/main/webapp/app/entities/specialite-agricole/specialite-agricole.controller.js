(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SpecialiteAgricoleController', SpecialiteAgricoleController);

    SpecialiteAgricoleController.$inject = ['SpecialiteAgricole', 'SpecialiteAgricoleSearch'];

    function SpecialiteAgricoleController(SpecialiteAgricole, SpecialiteAgricoleSearch) {

        var vm = this;

        vm.specialiteAgricoles = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SpecialiteAgricole.query(function(result) {
                vm.specialiteAgricoles = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SpecialiteAgricoleSearch.query({query: vm.searchQuery}, function(result) {
                vm.specialiteAgricoles = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
