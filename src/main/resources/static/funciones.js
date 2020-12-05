
function eliminar(id, route, redirect) {
    swal({
        title: "Esta seguro de eliminar el registro?",
        text: "Una vez eliminado no se prodra restablecer!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((OK) => {
        if (OK) {
            $.ajax({
                url: route + id,
                success: function () {
                    swal("Poof! Registro eliminado!", {
                        icon: "success",
                    }).then(() => {
                        location.href = redirect;
                    });
                },
                error: function () {
                    swal("Ocurrio un error, No se puede eliminar el registro", {
                        icon: "warning",
                    })
                }
            });

        }
    });
}



