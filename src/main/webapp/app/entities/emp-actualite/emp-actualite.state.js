(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emp-actualite', {
            parent: 'entity',
            url: '/emp-actualite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empActualite.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-actualite/emp-actualites.html',
                    controller: 'EmpActualiteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empActualite');
                    $translatePartialLoader.addPart('empactualitetype');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('emp-actualite-detail', {
            parent: 'emp-actualite',
            url: '/emp-actualite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empActualite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-actualite/emp-actualite-detail.html',
                    controller: 'EmpActualiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empActualite');
                    $translatePartialLoader.addPart('empactualitetype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmpActualite', function($stateParams, EmpActualite) {
                    return EmpActualite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emp-actualite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emp-actualite-detail.edit', {
            parent: 'emp-actualite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-actualite/emp-actualite-dialog.html',
                    controller: 'EmpActualiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpActualite', function(EmpActualite) {
                            return EmpActualite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-actualite.new', {
            parent: 'emp-actualite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-actualite/emp-actualite-dialog.html',
                    controller: 'EmpActualiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                initDate: null,
                                periode: null,
                                secteur: null,
                                title: null,
                                shortDisc: null,
                                longDisc: null,
                                image: null,
                                adresse: null,
                                empacttype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emp-actualite', null, { reload: 'emp-actualite' });
                }, function() {
                    $state.go('emp-actualite');
                });
            }]
        })
        .state('emp-actualite.edit', {
            parent: 'emp-actualite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-actualite/emp-actualite-dialog.html',
                    controller: 'EmpActualiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpActualite', function(EmpActualite) {
                            return EmpActualite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-actualite', null, { reload: 'emp-actualite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-actualite.delete', {
            parent: 'emp-actualite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-actualite/emp-actualite-delete-dialog.html',
                    controller: 'EmpActualiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmpActualite', function(EmpActualite) {
                            return EmpActualite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-actualite', null, { reload: 'emp-actualite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
