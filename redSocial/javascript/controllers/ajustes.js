angular.module('myApp')

    .controller('ajustesCtrl', ['$scope','$state','auth','ajustesService','usuarioService',function($scope,$state,auth,ajustesService,usuarioService){
        
        $scope.nombreUsuarioPrimario = auth.identity();
        $scope.nombreApellidosPrimario = auth.nombreApellidos();
        $scope.seguidores;
        
        $scope.nombreUsuario = $scope.nombreUsuarioPrimario;
        $scope.nombreApellidos = $scope.nombreApellidosPrimario;
        $scope.oldPassword = "";
        $scope.password = "";
        $scope.rePassword = "";
        
        $scope.exitoAjustes = false;
        $scope.errorAjustes = false;
        $scope.errorMsgAjustes = "";

        
        $scope.hideError = function () {
            $scope.errorMsgAjustes = "";
            $scope.errorAjustes = false;
        };
        
        $scope.hideExito = function () {
            $scope.exitoAjustes = false;
        };

        var showError = function (error) {
            $scope.errorMsgAjustes = error;
            $scope.errorAjustes = true;
        };

        var showExito = function () {
            $scope.exitoAjustes = true;
        };
        
        $scope.guardarCambios = function () {
            if ($scope.password !== $scope.rePassword) {
				$scope.errorAjustes = true;
                $scope.errorMsgAjustes = 'Las contraseñas no coinciden';
            } else {
                var userObject = {
					id: auth.idUser(),
                    nombreUsuario: $scope.nombreUsuario,
                    nombreApellidos: $scope.nombreApellidos,
                    oldPass: $scope.oldPassword,
                    pass: $scope.password,
                    rePass: $scope.rePassword
                };
                ajustesService.cambioAjustes(userObject,showExito,showError);
            }
        };

        $scope.limpiarCambios = function () {
            $scope.nombreUsuario = $scope.nombreUsuarioPrimario;
            $scope.nombreApellidos = $scope.nombreApellidosPrimario;
            $scope.oldPassword = "";
            $scope.password = "";
            $scope.rePassword = "";
            $scope.hideError();
            $scope.hideExito();
        };

        $scope.cogerSeguidores = function(){
            ajustesService.seguidores( auth.idUser(), function(seguidores) {
                $scope.seguidores = seguidores;
            });
        };
        $scope.cogerSeguidores();

        $scope.dejarSeguir = function (idSeguidor) {
            ajustesService.dejarSeguir(auth.idUser(), idSeguidor,showExito,borrarSeguidor);
        };

        var borrarSeguidor = function (idSeguidor) {
            $scope.idDejarSeguir = idSeguidor;
            var aux = $scope.seguidores.findIndex($scope.checkIdSeguidor);
            if (aux != ($scope.seguidores.length -1)) {
                $scope.seguidores[aux] = $scope.seguidores.pop();
            } else {
                $scope.seguidores.pop();
            }
        };

        $scope.checkIdSeguidor = function (seguidor) {
            return seguidor.id == $scope.idDejarSeguir;
        };

        $scope.entrarUsuario = function (id) {
            usuarioService.setUsuario(id);
            $state.go('usuario');
        };

    }]);
