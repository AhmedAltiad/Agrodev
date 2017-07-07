'use strict';

describe('Controller Tests', function() {

    describe('CommandeDetails Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCommandeDetails, MockVariete, MockAgriculteur, MockCommande;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCommandeDetails = jasmine.createSpy('MockCommandeDetails');
            MockVariete = jasmine.createSpy('MockVariete');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            MockCommande = jasmine.createSpy('MockCommande');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CommandeDetails': MockCommandeDetails,
                'Variete': MockVariete,
                'Agriculteur': MockAgriculteur,
                'Commande': MockCommande
            };
            createController = function() {
                $injector.get('$controller')("CommandeDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:commandeDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
