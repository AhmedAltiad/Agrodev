(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('palier', {
            parent: 'entity',
            url: '/palier',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.palier.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/palier/paliers.html',
                    controller: 'PalierController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('palier');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('palier-detail', {
            parent: 'palier',
            url: '/palier/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.palier.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/palier/palier-detail.html',
                    controller: 'PalierDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('palier');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Palier', function($stateParams, Palier) {
                    return Palier.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'palier',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('palier-detail.edit', {
            parent: 'palier-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palier/palier-dialog.html',
                    controller: 'PalierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Palier', function(Palier) {
                            return Palier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('palier.new', {
            parent: 'palier',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palier/palier-dialog.html',
                    controller: 'PalierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datedebut: null,
                                datefin: null,
                                nombrecons: null,
                                promotion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('palier', null, { reload: 'palier' });
                }, function() {
                    $state.go('palier');
                });
            }]
        })
        .state('palier.edit', {
            parent: 'palier',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palier/palier-dialog.html',
                    controller: 'PalierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Palier', function(Palier) {
                            return Palier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('palier', null, { reload: 'palier' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('palier.delete', {
            parent: 'palier',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/palier/palier-delete-dialog.html',
                    controller: 'PalierDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Palier', function(Palier) {
                            return Palier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('palier', null, { reload: 'palier' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
