(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('annonce', {
            parent: 'entity',
            url: '/annonce',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourseApp.annonce.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce/annonces.html',
                    controller: 'AnnonceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonce');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('annonce-detail', {
            parent: 'annonce',
            url: '/annonce/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourseApp.annonce.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce/annonce-detail.html',
                    controller: 'AnnonceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonce');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Annonce', function($stateParams, Annonce) {
                    return Annonce.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'annonce',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('annonce-detail.edit', {
            parent: 'annonce-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce/annonce-dialog.html',
                    controller: 'AnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Annonce', function(Annonce) {
                            return Annonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce.new', {
            parent: 'annonce',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce/annonce-dialog.html',
                    controller: 'AnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sujet: null,
                                annoncebody: null,
                                image: null,
                                datedebut: null,
                                datefin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('annonce', null, { reload: 'annonce' });
                }, function() {
                    $state.go('annonce');
                });
            }]
        })
        .state('annonce.edit', {
            parent: 'annonce',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce/annonce-dialog.html',
                    controller: 'AnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Annonce', function(Annonce) {
                            return Annonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce', null, { reload: 'annonce' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce.delete', {
            parent: 'annonce',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce/annonce-delete-dialog.html',
                    controller: 'AnnonceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Annonce', function(Annonce) {
                            return Annonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce', null, { reload: 'annonce' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
