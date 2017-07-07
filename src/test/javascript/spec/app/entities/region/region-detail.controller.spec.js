'use strict';

describe('Controller Tests', function() {

    describe('Region Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRegion, MockPays, MockGouvernorat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRegion = jasmine.createSpy('MockRegion');
            MockPays = jasmine.createSpy('MockPays');
            MockGouvernorat = jasmine.createSpy('MockGouvernorat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Region': MockRegion,
                'Pays': MockPays,
                'Gouvernorat': MockGouvernorat
            };
            createController = function() {
                $injector.get('$controller')("RegionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:regionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
