(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GouvernoratController', GouvernoratController);

    GouvernoratController.$inject = ['Gouvernorat', 'GouvernoratSearch'];

    function GouvernoratController(Gouvernorat, GouvernoratSearch) {

        var vm = this;

        vm.gouvernorats = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Gouvernorat.query(function(result) {
                vm.gouvernorats = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            GouvernoratSearch.query({query: vm.searchQuery}, function(result) {
                vm.gouvernorats = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
