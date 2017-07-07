'use strict';

describe('Controller Tests', function() {

    describe('AnnonceHistorique Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAnnonceHistorique, MockAnnonceChangement, MockView, MockProfil, MockECommandeHistorique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAnnonceHistorique = jasmine.createSpy('MockAnnonceHistorique');
            MockAnnonceChangement = jasmine.createSpy('MockAnnonceChangement');
            MockView = jasmine.createSpy('MockView');
            MockProfil = jasmine.createSpy('MockProfil');
            MockECommandeHistorique = jasmine.createSpy('MockECommandeHistorique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AnnonceHistorique': MockAnnonceHistorique,
                'AnnonceChangement': MockAnnonceChangement,
                'View': MockView,
                'Profil': MockProfil,
                'ECommandeHistorique': MockECommandeHistorique
            };
            createController = function() {
                $injector.get('$controller')("AnnonceHistoriqueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:annonceHistoriqueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
