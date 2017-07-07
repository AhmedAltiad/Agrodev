(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CvSpecialitiesController', CvSpecialitiesController);

    CvSpecialitiesController.$inject = ['CvSpecialities', 'CvSpecialitiesSearch'];

    function CvSpecialitiesController(CvSpecialities, CvSpecialitiesSearch) {

        var vm = this;

        vm.cvSpecialities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CvSpecialities.query(function(result) {
                vm.cvSpecialities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CvSpecialitiesSearch.query({query: vm.searchQuery}, function(result) {
                vm.cvSpecialities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
