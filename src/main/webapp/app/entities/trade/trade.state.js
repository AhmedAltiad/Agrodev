(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trade', {
            parent: 'entity',
            url: '/trade',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.trade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trade/trades.html',
                    controller: 'TradeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trade');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trade-detail', {
            parent: 'trade',
            url: '/trade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.trade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trade/trade-detail.html',
                    controller: 'TradeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trade');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Trade', function($stateParams, Trade) {
                    return Trade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trade-detail.edit', {
            parent: 'trade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trade/trade-dialog.html',
                    controller: 'TradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trade', function(Trade) {
                            return Trade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trade.new', {
            parent: 'trade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trade/trade-dialog.html',
                    controller: 'TradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateOfOrder: null,
                                dateOfDelivery: null,
                                prixUnitaire: null,
                                position: null,
                                quantity: null,
                                montant: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trade', null, { reload: 'trade' });
                }, function() {
                    $state.go('trade');
                });
            }]
        })
        .state('trade.edit', {
            parent: 'trade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trade/trade-dialog.html',
                    controller: 'TradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trade', function(Trade) {
                            return Trade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trade', null, { reload: 'trade' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trade.delete', {
            parent: 'trade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trade/trade-delete-dialog.html',
                    controller: 'TradeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trade', function(Trade) {
                            return Trade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trade', null, { reload: 'trade' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
