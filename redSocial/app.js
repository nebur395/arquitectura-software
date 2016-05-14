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
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
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
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
                    }
                }
            })

            .state('videojuego', {
                url: "/videojuego",
                templateUrl: "templates/videojuego.html",
                controller: "videojuegoCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
                    }
                }
            })

            .state('buscar', {
                url: "/buscar",
                templateUrl: "templates/buscar.html",
                controller: "buscarCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
                    }
                }
            })

            .state('buscarUsuario', {
                url: "/buscarUsuario",
                templateUrl: "templates/buscarUsuario.html",
                controller: "buscarUsuarioCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
                    }
                }
            })

            .state('admin', {
                url: "/admin",
                templateUrl: "templates/admin.html",
                controller: "adminCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    } else if (!auth.isAdmin()) {
                        $state.go('novedades');
                    }
                }
            })

            .state('usuario', {
                url: "/usuario",
                templateUrl: "templates/usuario.html",
                controller: "usuarioCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('initSesion');
                    } else if (auth.isAdmin()) {
                        $state.go('admin');
                    }
                }
            })
            
            .state('initSesion', {
                url: "/initSesion",
                templateUrl: "templates/initSesion.html",
                controller: "initSesionCtrl",
                onEnter: function($state,auth){
                    if(auth.isAuthenticated()){
                        if (auth.isAdmin()) {
                            $state.go('admin');
                        } else {
                            $state.go('novedades');
                        }
                    }
                }
            });
        
        $urlRouterProvider.otherwise('initSesion');
    });
