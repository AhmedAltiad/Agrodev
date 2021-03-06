'use strict';

describe('Controller Tests', function() {

    describe('ActiviteAgricole Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockActiviteAgricole, MockAgriculteur;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockActiviteAgricole = jasmine.createSpy('MockActiviteAgricole');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ActiviteAgricole': MockActiviteAgricole,
                'Agriculteur': MockAgriculteur
            };
            createController = function() {
                $injector.get('$controller')("ActiviteAgricoleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:activiteAgricoleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
