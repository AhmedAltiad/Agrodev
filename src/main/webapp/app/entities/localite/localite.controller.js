(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('LocaliteController', LocaliteController);

    LocaliteController.$inject = ['Localite', 'LocaliteSearch'];

    function LocaliteController(Localite, LocaliteSearch) {

        var vm = this;

        vm.localites = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Localite.query(function(result) {
                vm.localites = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LocaliteSearch.query({query: vm.searchQuery}, function(result) {
                vm.localites = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
