'use strict';

describe('Controller Tests', function() {

    describe('Souscategorie Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSouscategorie, MockProduit, MockCategorie;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSouscategorie = jasmine.createSpy('MockSouscategorie');
            MockProduit = jasmine.createSpy('MockProduit');
            MockCategorie = jasmine.createSpy('MockCategorie');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Souscategorie': MockSouscategorie,
                'Produit': MockProduit,
                'Categorie': MockCategorie
            };
            createController = function() {
                $injector.get('$controller')("SouscategorieDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:souscategorieUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
