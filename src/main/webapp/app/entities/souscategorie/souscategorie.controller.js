(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SouscategorieController', SouscategorieController);

    SouscategorieController.$inject = ['Souscategorie', 'SouscategorieSearch'];

    function SouscategorieController(Souscategorie, SouscategorieSearch) {

        var vm = this;

        vm.souscategories = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Souscategorie.query(function(result) {
                vm.souscategories = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SouscategorieSearch.query({query: vm.searchQuery}, function(result) {
                vm.souscategories = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
