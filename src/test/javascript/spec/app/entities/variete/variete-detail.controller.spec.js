'use strict';

describe('Controller Tests', function() {

    describe('Variete Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVariete, MockProduit, MockAnnonce, MockCommandeDetails, MockTrade, MockProduction, MockPortefolio;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVariete = jasmine.createSpy('MockVariete');
            MockProduit = jasmine.createSpy('MockProduit');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            MockCommandeDetails = jasmine.createSpy('MockCommandeDetails');
            MockTrade = jasmine.createSpy('MockTrade');
            MockProduction = jasmine.createSpy('MockProduction');
            MockPortefolio = jasmine.createSpy('MockPortefolio');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Variete': MockVariete,
                'Produit': MockProduit,
                'Annonce': MockAnnonce,
                'CommandeDetails': MockCommandeDetails,
                'Trade': MockTrade,
                'Production': MockProduction,
                'Portefolio': MockPortefolio
            };
            createController = function() {
                $injector.get('$controller')("VarieteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:varieteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
