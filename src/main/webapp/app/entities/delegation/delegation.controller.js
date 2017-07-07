(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DelegationController', DelegationController);

    DelegationController.$inject = ['Delegation', 'DelegationSearch'];

    function DelegationController(Delegation, DelegationSearch) {

        var vm = this;

        vm.delegations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Delegation.query(function(result) {
                vm.delegations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DelegationSearch.query({query: vm.searchQuery}, function(result) {
                vm.delegations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
