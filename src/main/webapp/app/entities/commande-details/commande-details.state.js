(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('commande-details', {
            parent: 'entity',
            url: '/commande-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.commandeDetails.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commande-details/commande-details.html',
                    controller: 'CommandeDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commandeDetails');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('commande-details-detail', {
            parent: 'commande-details',
            url: '/commande-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.commandeDetails.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commande-details/commande-details-detail.html',
                    controller: 'CommandeDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commandeDetails');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CommandeDetails', function($stateParams, CommandeDetails) {
                    return CommandeDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'commande-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('commande-details-detail.edit', {
            parent: 'commande-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commande-details/commande-details-dialog.html',
                    controller: 'CommandeDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommandeDetails', function(CommandeDetails) {
                            return CommandeDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commande-details.new', {
            parent: 'commande-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commande-details/commande-details-dialog.html',
                    controller: 'CommandeDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('commande-details', null, { reload: 'commande-details' });
                }, function() {
                    $state.go('commande-details');
                });
            }]
        })
        .state('commande-details.edit', {
            parent: 'commande-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commande-details/commande-details-dialog.html',
                    controller: 'CommandeDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommandeDetails', function(CommandeDetails) {
                            return CommandeDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commande-details', null, { reload: 'commande-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commande-details.delete', {
            parent: 'commande-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commande-details/commande-details-delete-dialog.html',
                    controller: 'CommandeDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommandeDetails', function(CommandeDetails) {
                            return CommandeDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commande-details', null, { reload: 'commande-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
