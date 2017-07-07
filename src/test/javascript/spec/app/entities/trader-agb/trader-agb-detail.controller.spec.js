'use strict';

describe('Controller Tests', function() {

    describe('TraderAGB Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraderAGB, MockProfil, MockDesk, MockCommande, MockTrade, MockPortefolio;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockProfil = jasmine.createSpy('MockProfil');
            MockDesk = jasmine.createSpy('MockDesk');
            MockCommande = jasmine.createSpy('MockCommande');
            MockTrade = jasmine.createSpy('MockTrade');
            MockPortefolio = jasmine.createSpy('MockPortefolio');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraderAGB': MockTraderAGB,
                'Profil': MockProfil,
                'Desk': MockDesk,
                'Commande': MockCommande,
                'Trade': MockTrade,
                'Portefolio': MockPortefolio
            };
            createController = function() {
                $injector.get('$controller')("TraderAGBDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:traderAGBUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
