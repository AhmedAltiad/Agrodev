(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('annonce-changement', {
            parent: 'entity',
            url: '/annonce-changement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.annonceChangement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce-changement/annonce-changements.html',
                    controller: 'AnnonceChangementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonceChangement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('annonce-changement-detail', {
            parent: 'annonce-changement',
            url: '/annonce-changement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.annonceChangement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce-changement/annonce-changement-detail.html',
                    controller: 'AnnonceChangementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonceChangement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AnnonceChangement', function($stateParams, AnnonceChangement) {
                    return AnnonceChangement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'annonce-changement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('annonce-changement-detail.edit', {
            parent: 'annonce-changement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-changement/annonce-changement-dialog.html',
                    controller: 'AnnonceChangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnnonceChangement', function(AnnonceChangement) {
                            return AnnonceChangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce-changement.new', {
            parent: 'annonce-changement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-changement/annonce-changement-dialog.html',
                    controller: 'AnnonceChangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                changement: null,
                                createddate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('annonce-changement', null, { reload: 'annonce-changement' });
                }, function() {
                    $state.go('annonce-changement');
                });
            }]
        })
        .state('annonce-changement.edit', {
            parent: 'annonce-changement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-changement/annonce-changement-dialog.html',
                    controller: 'AnnonceChangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnnonceChangement', function(AnnonceChangement) {
                            return AnnonceChangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce-changement', null, { reload: 'annonce-changement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce-changement.delete', {
            parent: 'annonce-changement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-changement/annonce-changement-delete-dialog.html',
                    controller: 'AnnonceChangementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AnnonceChangement', function(AnnonceChangement) {
                            return AnnonceChangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce-changement', null, { reload: 'annonce-changement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
