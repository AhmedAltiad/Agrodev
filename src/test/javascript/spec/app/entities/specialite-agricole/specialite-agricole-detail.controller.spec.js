'use strict';

describe('Controller Tests', function() {

    describe('SpecialiteAgricole Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSpecialiteAgricole, MockAgriculteur;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSpecialiteAgricole = jasmine.createSpy('MockSpecialiteAgricole');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SpecialiteAgricole': MockSpecialiteAgricole,
                'Agriculteur': MockAgriculteur
            };
            createController = function() {
                $injector.get('$controller')("SpecialiteAgricoleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:specialiteAgricoleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
