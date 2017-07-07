(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trader-ca', {
            parent: 'entity',
            url: '/trader-ca',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.traderCA.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trader-ca/trader-cas.html',
                    controller: 'TraderCAController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traderCA');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trader-ca-detail', {
            parent: 'trader-ca',
            url: '/trader-ca/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.traderCA.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trader-ca/trader-ca-detail.html',
                    controller: 'TraderCADetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traderCA');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraderCA', function($stateParams, TraderCA) {
                    return TraderCA.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trader-ca',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trader-ca-detail.edit', {
            parent: 'trader-ca-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-ca/trader-ca-dialog.html',
                    controller: 'TraderCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraderCA', function(TraderCA) {
                            return TraderCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trader-ca.new', {
            parent: 'trader-ca',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-ca/trader-ca-dialog.html',
                    controller: 'TraderCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                adresse: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trader-ca', null, { reload: 'trader-ca' });
                }, function() {
                    $state.go('trader-ca');
                });
            }]
        })
        .state('trader-ca.edit', {
            parent: 'trader-ca',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-ca/trader-ca-dialog.html',
                    controller: 'TraderCADialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraderCA', function(TraderCA) {
                            return TraderCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trader-ca', null, { reload: 'trader-ca' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trader-ca.delete', {
            parent: 'trader-ca',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-ca/trader-ca-delete-dialog.html',
                    controller: 'TraderCADeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraderCA', function(TraderCA) {
                            return TraderCA.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trader-ca', null, { reload: 'trader-ca' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
