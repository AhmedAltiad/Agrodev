'use strict';

describe('Controller Tests', function() {

    describe('Produit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProduit, MockVariete, MockLocalite, MockPalier, MockSouscategorie;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProduit = jasmine.createSpy('MockProduit');
            MockVariete = jasmine.createSpy('MockVariete');
            MockLocalite = jasmine.createSpy('MockLocalite');
            MockPalier = jasmine.createSpy('MockPalier');
            MockSouscategorie = jasmine.createSpy('MockSouscategorie');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Produit': MockProduit,
                'Variete': MockVariete,
                'Localite': MockLocalite,
                'Palier': MockPalier,
                'Souscategorie': MockSouscategorie
            };
            createController = function() {
                $injector.get('$controller')("ProduitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:produitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
