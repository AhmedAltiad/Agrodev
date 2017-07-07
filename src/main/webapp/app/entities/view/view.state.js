(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('view', {
            parent: 'entity',
            url: '/view',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.view.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/view/views.html',
                    controller: 'ViewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('view');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('view-detail', {
            parent: 'view',
            url: '/view/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.view.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/view/view-detail.html',
                    controller: 'ViewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('view');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'View', function($stateParams, View) {
                    return View.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'view',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('view-detail.edit', {
            parent: 'view-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/view/view-dialog.html',
                    controller: 'ViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['View', function(View) {
                            return View.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('view.new', {
            parent: 'view',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/view/view-dialog.html',
                    controller: 'ViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                consoleType: null,
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('view', null, { reload: 'view' });
                }, function() {
                    $state.go('view');
                });
            }]
        })
        .state('view.edit', {
            parent: 'view',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/view/view-dialog.html',
                    controller: 'ViewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['View', function(View) {
                            return View.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('view', null, { reload: 'view' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('view.delete', {
            parent: 'view',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/view/view-delete-dialog.html',
                    controller: 'ViewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['View', function(View) {
                            return View.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('view', null, { reload: 'view' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
