angular.module('myApp')

    .controller('videojuegoCtrl', ['$scope','$state','usuarioService','videojuegoService','auth',function($scope,$state,usuarioService,videojuegoService,auth){
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
                    videojuegoService.guardarOpinion(true,$scope.comentario,
                        showExito,showError, function (fecha) {
                            var comentario = {
                                idUsuario: auth.idUser(),
                                nombreUsuario: auth.identity(),
                                fecha: fecha,
                                nombreApellidos: auth.nombreApellidos(),
                                contenido: $scope.comentario
                            };
                            $scope.listaComentarios.push(comentario);
                        });
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
                    videojuegoService.guardarOpinion(false,num,showExito,showError,
                        function (fecha, estrellas) {
                            $scope.videojuego.valoracion = estrellas;
                            var numEstrella = "";
                            switch (num) {
                                case 1:
                                    numEstrella = "unaEstrella";
                                    break;
                                case 2:
                                    numEstrella = "dosEstrella";
                                    break;
                                case 3:
                                    numEstrella = "tresEstrella";
                                    break;
                                case 4:
                                    numEstrella = "cuatroEstrella";
                                    break;
                                default:
                                    numEstrella = "cincoEstrella";
                            }
                            var valoracion = {
                                idUsuario: auth.idUser(),
                                nombreUsuario: auth.identity(),
                                fecha: fecha,
                                nombreApellidos: auth.nombreApellidos(),
                                valoracion: numEstrella
                            };

                            $scope.listaValoraciones.push(valoracion);
                        });
                }
            });
        };

    }]);
