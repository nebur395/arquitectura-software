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

    .factory('usuarioService', function ($http,auth) {

        var usuarioID = 0;
        if ((usuarioID == 0) && (localStorage.usuarioID !== undefined)) {
            usuarioID = localStorage.usuarioID;
        }

        return {
            setUsuario: function(usuario) {
                localStorage.usuarioID = usuario;
                usuarioID = usuario;
            },
            getInfo: function(callback) {
                $http({
                 method: 'POST',
                 url: 'Usuario',
                 data: JSON.stringify({"idUsuario":usuarioID, "idUser":auth.idUser()}),
                 headers: {
                 'Content-Type': 'application/json; charset=UTF-8'
                 }
                 }).success(function(data){ 
					callback(data.usuario,data.comentarios,data.valoraciones);
                 }).error(function(){
                 });
            },
            seguir: function(usuario) {
                $http({
                    method: 'POST',
                    url: 'Seguir',
                    data: JSON.stringify({"idUser":auth.idUser(), "idSeguidor":usuario.id}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(){
                    usuario.tipo = 3;
                }).error(function(){
                });
            },
            dejarSeguir: function(usuario) {
                $http({
                    method: 'POST',
                    url: 'dejarSeguir',
                    data: JSON.stringify({"idUser":auth.idUser(), "idSeguidor":usuario.id}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(){
                    usuario.tipo = 2;
                }).error(function(){
                });
            }
        };
    })
    
    .factory('videojuegoService', function ($http,auth) {

        var videojuegoID = 0;
        if ((videojuegoID == 0) && (localStorage.videojuegoID !== undefined)) {
            videojuegoID = localStorage.videojuegoID;
        }

        return {
            guardarOpinion: function(tipo,opinion,callbackExito,callbackError) {
                $http({
                    method: 'POST',
                    url: 'Opinar',
                    data: JSON.stringify({"idUser":auth.idUser(), "tipo":tipo, "opinion":opinion, "idVideojuego":videojuegoID}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(){
                    callbackExito();
                }).error(function(data){
                    callbackError(data);
                });
            },
            setVideojuego: function(videojuego) {
                localStorage.videojuegoID = videojuego;
                videojuegoID = videojuego;
            },
            getInfo: function(callback) {
                $http({
                    method: 'POST',
                    url: 'Videojuego',
                    data: JSON.stringify({"idVideojuego":videojuegoID}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data.videojuego,data.comentarios,data.valoraciones);
                }).error(function(){
                });
            }
        };
    })

    .factory('buscarService', function ($http) {
        return {
            buscar: function(callback) {
                $http({
                    method: 'POST',
                    url: 'Buscar',
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(data){
                    callback(data);
                }).error(function(){
                });
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
