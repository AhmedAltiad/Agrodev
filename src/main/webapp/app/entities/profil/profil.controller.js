(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProfilController', ProfilController);

    ProfilController.$inject = ['Profil', 'ProfilSearch'];

    function ProfilController(Profil, ProfilSearch) {

        var vm = this;

        vm.profils = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Profil.query(function(result) {
                vm.profils = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProfilSearch.query({query: vm.searchQuery}, function(result) {
                vm.profils = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
