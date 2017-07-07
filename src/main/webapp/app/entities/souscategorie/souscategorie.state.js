(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('souscategorie', {
            parent: 'entity',
            url: '/souscategorie',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.souscategorie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/souscategorie/souscategories.html',
                    controller: 'SouscategorieController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('souscategorie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('souscategorie-detail', {
            parent: 'souscategorie',
            url: '/souscategorie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.souscategorie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/souscategorie/souscategorie-detail.html',
                    controller: 'SouscategorieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('souscategorie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Souscategorie', function($stateParams, Souscategorie) {
                    return Souscategorie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'souscategorie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('souscategorie-detail.edit', {
            parent: 'souscategorie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/souscategorie/souscategorie-dialog.html',
                    controller: 'SouscategorieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Souscategorie', function(Souscategorie) {
                            return Souscategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('souscategorie.new', {
            parent: 'souscategorie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/souscategorie/souscategorie-dialog.html',
                    controller: 'SouscategorieDialogController',
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
                    $state.go('souscategorie', null, { reload: 'souscategorie' });
                }, function() {
                    $state.go('souscategorie');
                });
            }]
        })
        .state('souscategorie.edit', {
            parent: 'souscategorie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/souscategorie/souscategorie-dialog.html',
                    controller: 'SouscategorieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Souscategorie', function(Souscategorie) {
                            return Souscategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('souscategorie', null, { reload: 'souscategorie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('souscategorie.delete', {
            parent: 'souscategorie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/souscategorie/souscategorie-delete-dialog.html',
                    controller: 'SouscategorieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Souscategorie', function(Souscategorie) {
                            return Souscategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('souscategorie', null, { reload: 'souscategorie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
