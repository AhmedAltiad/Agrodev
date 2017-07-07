'use strict';

describe('Controller Tests', function() {

    describe('Commande Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCommande, MockCommandeDetails, MockCurrency, MockTraderAGB, MockAgriculteur;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCommande = jasmine.createSpy('MockCommande');
            MockCommandeDetails = jasmine.createSpy('MockCommandeDetails');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Commande': MockCommande,
                'CommandeDetails': MockCommandeDetails,
                'Currency': MockCurrency,
                'TraderAGB': MockTraderAGB,
                'Agriculteur': MockAgriculteur
            };
            createController = function() {
                $injector.get('$controller')("CommandeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:commandeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
