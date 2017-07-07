(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trader-agb', {
            parent: 'entity',
            url: '/trader-agb',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.traderAGB.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trader-agb/trader-agbs.html',
                    controller: 'TraderAGBController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traderAGB');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trader-agb-detail', {
            parent: 'trader-agb',
            url: '/trader-agb/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.traderAGB.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trader-agb/trader-agb-detail.html',
                    controller: 'TraderAGBDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traderAGB');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraderAGB', function($stateParams, TraderAGB) {
                    return TraderAGB.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trader-agb',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trader-agb-detail.edit', {
            parent: 'trader-agb-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-agb/trader-agb-dialog.html',
                    controller: 'TraderAGBDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraderAGB', function(TraderAGB) {
                            return TraderAGB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trader-agb.new', {
            parent: 'trader-agb',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-agb/trader-agb-dialog.html',
                    controller: 'TraderAGBDialogController',
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
                    $state.go('trader-agb', null, { reload: 'trader-agb' });
                }, function() {
                    $state.go('trader-agb');
                });
            }]
        })
        .state('trader-agb.edit', {
            parent: 'trader-agb',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-agb/trader-agb-dialog.html',
                    controller: 'TraderAGBDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraderAGB', function(TraderAGB) {
                            return TraderAGB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trader-agb', null, { reload: 'trader-agb' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trader-agb.delete', {
            parent: 'trader-agb',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trader-agb/trader-agb-delete-dialog.html',
                    controller: 'TraderAGBDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraderAGB', function(TraderAGB) {
                            return TraderAGB.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trader-agb', null, { reload: 'trader-agb' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
