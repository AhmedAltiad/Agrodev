'use strict';

describe('Controller Tests', function() {

    describe('Annonce Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAnnonce, MockVariete, MockProfil, MockLocalite, MockPalier, MockECommande;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            MockVariete = jasmine.createSpy('MockVariete');
            MockProfil = jasmine.createSpy('MockProfil');
            MockLocalite = jasmine.createSpy('MockLocalite');
            MockPalier = jasmine.createSpy('MockPalier');
            MockECommande = jasmine.createSpy('MockECommande');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Annonce': MockAnnonce,
                'Variete': MockVariete,
                'Profil': MockProfil,
                'Localite': MockLocalite,
                'Palier': MockPalier,
                'ECommande': MockECommande
            };
            createController = function() {
                $injector.get('$controller')("AnnonceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:annonceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
