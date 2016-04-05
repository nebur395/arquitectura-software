angular.module('myApp', ['ui.router','ab-base64'])

    .config(function($stateProvider, $urlRouterProvider){
        $stateProvider

            .state('novedades', {
                url: "/novedades",
                templateUrl: "templates/novedades.html",
                controller: "novedadesCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    }
                }
            })
            .state('ajustes', {
                url: "/ajustes",
                templateUrl: "templates/ajustes.html",
                controller: "ajustesCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    }
                }
            })
            .state('initSesion', {
                url: "/initSesion",
                templateUrl: "templates/initSesion.html",
                controller: "initSesionCtrl",
                onEnter: function($state,auth){
                    if(auth.isAuthenticated()){
                        $state.go('novedades');
                    }
                }
            });
            
        
        $urlRouterProvider.otherwise('initSesion');
    });
