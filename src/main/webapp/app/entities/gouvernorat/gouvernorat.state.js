(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gouvernorat', {
            parent: 'entity',
            url: '/gouvernorat',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.gouvernorat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gouvernorat/gouvernorats.html',
                    controller: 'GouvernoratController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gouvernorat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gouvernorat-detail', {
            parent: 'gouvernorat',
            url: '/gouvernorat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.gouvernorat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gouvernorat/gouvernorat-detail.html',
                    controller: 'GouvernoratDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gouvernorat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gouvernorat', function($stateParams, Gouvernorat) {
                    return Gouvernorat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gouvernorat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gouvernorat-detail.edit', {
            parent: 'gouvernorat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gouvernorat/gouvernorat-dialog.html',
                    controller: 'GouvernoratDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gouvernorat', function(Gouvernorat) {
                            return Gouvernorat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gouvernorat.new', {
            parent: 'gouvernorat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gouvernorat/gouvernorat-dialog.html',
                    controller: 'GouvernoratDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gouvernorat', null, { reload: 'gouvernorat' });
                }, function() {
                    $state.go('gouvernorat');
                });
            }]
        })
        .state('gouvernorat.edit', {
            parent: 'gouvernorat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gouvernorat/gouvernorat-dialog.html',
                    controller: 'GouvernoratDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gouvernorat', function(Gouvernorat) {
                            return Gouvernorat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gouvernorat', null, { reload: 'gouvernorat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gouvernorat.delete', {
            parent: 'gouvernorat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gouvernorat/gouvernorat-delete-dialog.html',
                    controller: 'GouvernoratDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gouvernorat', function(Gouvernorat) {
                            return Gouvernorat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gouvernorat', null, { reload: 'gouvernorat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
