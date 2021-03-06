(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('desk', {
            parent: 'entity',
            url: '/desk',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.desk.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desk/desks.html',
                    controller: 'DeskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('desk');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('desk-detail', {
            parent: 'desk',
            url: '/desk/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.desk.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desk/desk-detail.html',
                    controller: 'DeskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('desk');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Desk', function($stateParams, Desk) {
                    return Desk.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'desk',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('desk-detail.edit', {
            parent: 'desk-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desk/desk-dialog.html',
                    controller: 'DeskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desk', function(Desk) {
                            return Desk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desk.new', {
            parent: 'desk',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desk/desk-dialog.html',
                    controller: 'DeskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                adresse: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('desk', null, { reload: 'desk' });
                }, function() {
                    $state.go('desk');
                });
            }]
        })
        .state('desk.edit', {
            parent: 'desk',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desk/desk-dialog.html',
                    controller: 'DeskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desk', function(Desk) {
                            return Desk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desk', null, { reload: 'desk' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desk.delete', {
            parent: 'desk',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desk/desk-delete-dialog.html',
                    controller: 'DeskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Desk', function(Desk) {
                            return Desk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desk', null, { reload: 'desk' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
