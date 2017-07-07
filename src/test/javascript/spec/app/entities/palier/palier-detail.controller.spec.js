'use strict';

describe('Controller Tests', function() {

    describe('Palier Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPalier, MockProduit, MockAnnonce;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPalier = jasmine.createSpy('MockPalier');
            MockProduit = jasmine.createSpy('MockProduit');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Palier': MockPalier,
                'Produit': MockProduit,
                'Annonce': MockAnnonce
            };
            createController = function() {
                $injector.get('$controller')("PalierDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:palierUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
