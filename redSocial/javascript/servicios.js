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
            dejarSeguir: function(idUser,idSeguidor) {
                $http({
                    method: 'POST',
                    url: 'dejarSeguir',
                    data: JSON.stringify({"idUser":idUser, "idSeguidor":idSeguidor}),
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function(){
                    
                }).error(function(){

                });
            }
        };
    });
