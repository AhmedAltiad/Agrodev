(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PortefolioController', PortefolioController);

    PortefolioController.$inject = ['Portefolio', 'PortefolioSearch'];

    function PortefolioController(Portefolio, PortefolioSearch) {

        var vm = this;

        vm.portefolios = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Portefolio.query(function(result) {
                vm.portefolios = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PortefolioSearch.query({query: vm.searchQuery}, function(result) {
                vm.portefolios = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
