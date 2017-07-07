'use strict';

describe('Controller Tests', function() {

    describe('AnnonceChangement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAnnonceChangement, MockProfil, MockAnnonceHistorique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAnnonceChangement = jasmine.createSpy('MockAnnonceChangement');
            MockProfil = jasmine.createSpy('MockProfil');
            MockAnnonceHistorique = jasmine.createSpy('MockAnnonceHistorique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AnnonceChangement': MockAnnonceChangement,
                'Profil': MockProfil,
                'AnnonceHistorique': MockAnnonceHistorique
            };
            createController = function() {
                $injector.get('$controller')("AnnonceChangementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:annonceChangementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
