'use strict';

describe('Controller Tests', function() {

    describe('View Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockView, MockProfil, MockAnnonceHistorique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockView = jasmine.createSpy('MockView');
            MockProfil = jasmine.createSpy('MockProfil');
            MockAnnonceHistorique = jasmine.createSpy('MockAnnonceHistorique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'View': MockView,
                'Profil': MockProfil,
                'AnnonceHistorique': MockAnnonceHistorique
            };
            createController = function() {
                $injector.get('$controller')("ViewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:viewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
