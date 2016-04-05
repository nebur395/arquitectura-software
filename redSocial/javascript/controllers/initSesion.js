angular.module('myApp')

    .controller('initSesionCtrl', [ '$scope', '$http', '$state', 'auth', function($scope,$http,$state,auth){
        $scope.usarioIniciar = "";
        $scope.pwdIniciar = "";
        $scope.pwd1Registrar = "";
        $scope.pwd2Registrar = "";
        $scope.usuarioRegistrar = "";
        $scope.hiddenErrorIniciar = true;
        $scope.hiddenErrorRegistrar = true;
        $scope.errorRegistrar = "";

        $scope.hideErrorIniciar = function () {
            $scope.hiddenErrorIniciar = true;
        };

        var showErrorIniciar = function () {
            $scope.hiddenErrorIniciar = false;
        };

        $scope.hideErrorRegistrar = function () {
            $scope.hiddenErrorRegistrar = true;
            $scope.errorRegistrar = "";
        };

        var showErrorRegistrar = function (error) {
            debugger;
            $scope.errorRegistrar = error;
            $scope.hiddenErrorRegistrar = false;
        };

        $scope.iniciarSesion = function(){

            var user = $scope.usarioIniciar;
            var password = $scope.pwdIniciar;
            // Estandard 'authorization basic'
           auth.enviarSesion(user,password,showErrorIniciar);

        };

        $scope.registrarse = function(){
            
            var user = $scope.usarioRegistrar;
            var password1 = $scope.pwd1Registrar;
            var password2 = $scope.pwd2Registrar;
            if ( password1 !== password2 ) {
                $scope.hiddenErrorRegistrar = false;
                $scope.errorRegistrar = 'Las contraseñas no coinciden.';
            }else {
                var userObject = {
                    name: user,
                    pass1: password1,
                    pass2: password2
                };
                auth.enviarRegistro(userObject,showErrorRegistrar);
            }
        }
    }]);
