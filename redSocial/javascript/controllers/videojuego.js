angular.module('myApp')

    .controller('videojuegoCtrl', ['$scope','$state','usuarioService','videojuegoService',function($scope,$state,usuarioService,videojuegoService){
        $scope.mostrar = ["Comentarios","Valoraciones"];
        $scope.opinion = "Comentarios";
        $scope.listaComentarios;
        $scope.listaValoraciones;
        $scope.videojuego;
        $scope.comentario = "";

        $scope.exito = false;
        $scope.error = false;
        $scope.errorMsg = "";


        $scope.hideError = function () {
            $scope.errorMsg = "";
            $scope.error = false;
        };

        $scope.hideExito = function () {
            $scope.exito = false;
        };

        var showError = function (error) {
            $scope.errorMsg = error;
            $scope.error = true;
        };

        var showExito = function () {
            $scope.exito = true;
        };
        
        $scope.cogerInfo = function(){
            videojuegoService.getInfo(function(videojuego,comentarios,valoraciones) {
                $scope.videojuego = videojuego;
                $scope.listaComentarios = comentarios;
                $scope.listaValoraciones = valoraciones;
            });
        };
        $scope.cogerInfo();

        $scope.entrarUsuario = function (id) {
            usuarioService.setUsuario(id);
            $state.go('usuario');
        };

        $scope.newWardrobe = function () {
            $scope.hideExito();
            $scope.hideError();
            $scope.openWardrobeModal(wardrobe);
        };

        $scope.guardarComentario = function () {
            $("#comentarioModal").modal("hide");
            $scope.activo = true;
            $("#comentarioModal").on('hidden.bs.modal', function () {
                if ($scope.activo) {
                    $scope.activo = false;
                    videojuegoService.guardarOpinion(true,$scope.comentario,showExito,showError);
                }
            });
        };

        $scope.cerrarModal = function () {
            $scope.comentario = "";
            $("#comentarioModal").modal("hide");
        };

        

        $scope.valorar = function (num) {
            $("#valorarModal").modal("hide");
            $scope.activo = true;
            $("#valorarModal").on('hidden.bs.modal', function () {
                if ($scope.activo) {
                    $scope.activo = false;
                    videojuegoService.guardarOpinion(false,num,showExito,showError);
                }
            });
        };

    }]);
