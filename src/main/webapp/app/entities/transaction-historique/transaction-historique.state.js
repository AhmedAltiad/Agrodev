(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transaction-historique', {
            parent: 'entity',
            url: '/transaction-historique',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.transactionHistorique.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-historique/transaction-historiques.html',
                    controller: 'TransactionHistoriqueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transactionHistorique');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('transaction-historique-detail', {
            parent: 'transaction-historique',
            url: '/transaction-historique/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.transactionHistorique.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-historique/transaction-historique-detail.html',
                    controller: 'TransactionHistoriqueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transactionHistorique');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TransactionHistorique', function($stateParams, TransactionHistorique) {
                    return TransactionHistorique.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transaction-historique',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transaction-historique-detail.edit', {
            parent: 'transaction-historique-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-historique/transaction-historique-dialog.html',
                    controller: 'TransactionHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionHistorique', function(TransactionHistorique) {
                            return TransactionHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-historique.new', {
            parent: 'transaction-historique',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-historique/transaction-historique-dialog.html',
                    controller: 'TransactionHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                etat: null,
                                prix: null,
                                date: null,
                                numtransaction: null,
                                cmdspayees: null,
                                image: null,
                                dateValidation: null,
                                dateValidationPaiment: null,
                                dateRefuse: null,
                                dateRefuspaiment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transaction-historique', null, { reload: 'transaction-historique' });
                }, function() {
                    $state.go('transaction-historique');
                });
            }]
        })
        .state('transaction-historique.edit', {
            parent: 'transaction-historique',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-historique/transaction-historique-dialog.html',
                    controller: 'TransactionHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionHistorique', function(TransactionHistorique) {
                            return TransactionHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-historique', null, { reload: 'transaction-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-historique.delete', {
            parent: 'transaction-historique',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-historique/transaction-historique-delete-dialog.html',
                    controller: 'TransactionHistoriqueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TransactionHistorique', function(TransactionHistorique) {
                            return TransactionHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-historique', null, { reload: 'transaction-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
