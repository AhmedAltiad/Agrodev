(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DeskController', DeskController);

    DeskController.$inject = ['Desk', 'DeskSearch'];

    function DeskController(Desk, DeskSearch) {

        var vm = this;

        vm.desks = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Desk.query(function(result) {
                vm.desks = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DeskSearch.query({query: vm.searchQuery}, function(result) {
                vm.desks = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
