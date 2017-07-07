(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('FormationController', FormationController);

    FormationController.$inject = ['Formation', 'FormationSearch'];

    function FormationController(Formation, FormationSearch) {

        var vm = this;

        vm.formations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Formation.query(function(result) {
                vm.formations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            FormationSearch.query({query: vm.searchQuery}, function(result) {
                vm.formations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
