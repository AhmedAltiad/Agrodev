'use strict';

describe('Controller Tests', function() {

    describe('Production Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProduction, MockAgriculteur, MockVariete;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProduction = jasmine.createSpy('MockProduction');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            MockVariete = jasmine.createSpy('MockVariete');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Production': MockProduction,
                'Agriculteur': MockAgriculteur,
                'Variete': MockVariete
            };
            createController = function() {
                $injector.get('$controller')("ProductionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:productionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
