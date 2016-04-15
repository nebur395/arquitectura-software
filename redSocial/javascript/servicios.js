angular.module('myApp')


    .factory('auth', function ($http,$state,base64) {

        var _identity = undefined,
            _authenticated = false;

        return {
            isAuthenticated: function () {
                if (_authenticated) {
                    return _authenticated;
                } else {
                    var tmp = angular.fromJson(localStorage.userIdentity);
                    if (tmp !== undefined) {
                        this.authenticate(tmp);
                        return _authenticated;
                    }else{
                        return false;
                    }
                }
            },
            authenticate: function (identity) {
                _identity = identity;
                _authenticated = identity != null && identity != undefined;
                localStorage.userIdentity = angular.toJson(_identity);
            },
            identity: function () {
                return _identity.nombreUsuario;
            },
            nombreApellidos: function () {
                return _identity.nombreApellidos;
            },
            idUser: function () {
                return _identity.id;
            },
            enviarSesion: function(user,password,callback) {
				var servicios = this;
                $http({
                    method: 'GET',
                    url: 'Login',
                    headers: {
                        'Authorization': 'Basic ' + 
                        base64.encode(user + ":" +password)
                    }
                }).success(function(data){
                    servicios.authenticate(data);
                    $state.go('novedades');

                }).error(function(){
                    callback();
                });
            },

            enviarRegistro: function(userObject,callback) {
				var servicios = this;
                $http({
                    method: 'POST',
                    url: 'Registro',
                    data: JSON.stringify(userObject),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    servicios.authenticate(data);
                    $state.go('novedades');

                }).error(function(data){
                    callback(data);
                });
            }

        };
    })

    .factory('novedadesService', function ($http) {
        return {
            novedades: function(idUser,callback) {
                $http({
                    method: 'POST',
                    url: 'Novedades',
                    data: JSON.stringify({"idUser":idUser}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data.comentarios,data.valoraciones);

                }).error(function(){
                    
                });
            }
        };
    })

    .factory('videojuegoService', function () {

        var videojuegoID = 0;

        return {
            setVideojuego: function(videojuego) {
                videojuegoID = videojuego;
            },
            getInfo: function(callback) {
                var videojuegos =
                    {
                        id: 1,
                        nombre: "God of war III",
                        descripcion: "zaragoza",
                        lanzamiento: "18-03-2010",
                        desarrolladora: "SCE Studios Santa Mónica",
                        distribuidora: "Sony Computer Entertainment",
                        genero: "Acción-aventura",
                        plataforma: "PlayStation 3, PlayStation 4",
                        valoracion: "cuatroEstrella",
                    };
                callback(videojuegos);
                /*$http({
                    method: 'POST',
                    url: 'Videojuego',
                    data: JSON.stringify({"idVideojuego":videojuegoID}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data.videojuego,data.comentarios,data.valoraciones);
                }).error(function(){
                });*/
            }
        };
    })

    .factory('buscarService', function ($http) {
        return {
            buscar: function(callback) {
                /*$http({
                    method: 'POST',
                    url: 'Buscar',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data);

                }).error(function(){
                });*/
                var videojuegos =  [
                    {
                        id: 1,
                        nombre: "God of war III",
                        descripcion: "zaragoza",
                        lanzamiento: "18-03-2010",
                        desarrolladora: "SCE Studios Santa Mónica",
                        distribuidora: "Sony Computer Entertainment",
                        genero: "Acción-aventura",
                        plataforma: "PlayStation 3, PlayStation 4",
                        valoracion: "cuatroEstrella",
                    },
                    {
                        id: 2,
                        nombre: "The last of us",
                        descripcion: "zaragoza",
                        lanzamiento: "14-06-2013",
                        desarrolladora: "Naughty Dog",
                        distribuidora: "Sony Computer Entertainment",
                        genero: "Survival horror, Acción-aventura",
                        plataforma: "PlayStation 3, PlayStation 4",
                        valoracion: "cincoEstrella",
                    },
                    {
                        id: 3,
                        nombre: "Guild Wars 2",
                        descripcion: "zaragoza",
                        lanzamiento: "28-08-2012",
                        desarrolladora: "ArenaNet",
                        distribuidora: "NCosft",
                        genero: "MMORPG",
                        plataforma: "PC",
                        valoracion: "cincoEstrella",
                    }];
                callback(videojuegos);
            }
        };
    })

    .factory('ajustesService', function ($http,auth) {
        return {
            cambioAjustes: function(user,callbackExito,callbackError) {
                $http({
                    method: 'POST',
                    url: 'Ajustes',
                    data: JSON.stringify(user),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    auth.authenticate(data);
                    callbackExito();
                }).error(function(data){
                    callbackError(data);
                });
            },
            seguidores: function(idUser,callback) {
                $http({
                    method: 'POST',
                    url: 'Seguidores',
                    data: JSON.stringify({"idUser":idUser}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data.seguidores);

                }).error(function(){
                });
            },
            dejarSeguir: function(idUser,idSeguidor,callbackMensaje,callbackBorrarId) {
                $http({
                    method: 'POST',
                    url: 'dejarSeguir',
                    data: JSON.stringify({"idUser":idUser, "idSeguidor":idSeguidor}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(){
                    callbackMensaje();
                    callbackBorrarId(idSeguidor);
                }).error(function(){

                });
            }
        };
    });
