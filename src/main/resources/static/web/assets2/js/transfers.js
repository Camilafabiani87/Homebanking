const app = Vue.createApp({
  data() {
    return {
      url: "/api/clients/current",

      primerNombre: "",
      number: "",
      creationDate: "",
      balance: "",
      monto: null,
      descripcion: "",
      cuentaOrigen: "",
      cuentaDestino: "",
      opcion: "",
      cuentas: [],
      cuentaSeleccionada: {},
      error: "",
    };
  },

  created() {
    this.loadData(this.url);
  },
  mounted() {},
  methods: {
    loadData(url) {
      axios
        .get(url)
        .then((respuesta) => {
          this.clients = respuesta.data;
          this.cuentas = this.clients.accounts;
        })
        .catch((error) => console.error(error.message));
    },
    botonTransferir() {
      Swal.fire({
        background: "#212121",
        color: "white",
        title: "¿Desea confirmar la transferencia?",
        // icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: "#1bb5db",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, confirmo transferencia",
        cancelButtonText: "Cancelar",
      }).then((result) => {
        if (result.isConfirmed) {
          //console.log(idCuenta);
          this.realizarTransf();
        }
      });
    },

    realizarTransf() {
      axios
        .post(
          "/api/transactions",
          "amount=" +
            this.monto +
            "&description=" +
            this.descripcion +
            "&accountOrigin=" +
            this.cuentaOrigen +
            "&accountDestin=" +
            this.cuentaDestino
        )

        .then(() => {
          this.cuentaSeleccionada = this.cuentas.find(
            (cuenta) => cuenta.number == this.cuentaOrigen
          );

          let idCuenta = this.cuentaSeleccionada.id;
          window.location.assign("/web/account.html?id=" + idCuenta);
          let timerInterval;
          Swal.fire({
            background: "#212121",
            color: "white",
            title: "Su transferencia esta siendo realizada",
            html: "",
            timer: 2000,
            timerProgressBar: true,
            didOpen: () => {
              Swal.showLoading();
              const b = Swal.getHtmlContainer().querySelector("b");
              timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft();
              }, 1000);
            },
            willClose: () => {
              clearInterval(timerInterval);
            },
          }).then((result) => {
            /* Read more about handling dismissals below */
            if (result.dismiss === Swal.DismissReason.timer) {
              console.log("");
            }
          });
        })

        .catch((error) => {
          this.error = error.response.data;
          console.log(error);
          if (this.error == "Falta completar la descripcion") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "Falta completar la descripcion",
            });
          }
          if (this.error == "Falta completar el monto") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              // color:'white',
              // icon: 'warning',
              title: "Error",
              text: "Falta completar el monto",
            });
          }
          if (this.error == "Falta elegir una cuenta de origen") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "Falta elegir una cuenta de origen",
            });
          }
          if (this.error == "Falta elegir una cuenta destino") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "Falta elegir una cuenta destino",
            });
          }
          if (this.error == "No se pudo obtener la cuenta") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "No se pudo obtener la cuenta",
            });
          }
          if (this.error == "La cuenta de destino no existe") {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "La cuenta de destino no existe",
            });
          }
          if (
            this.error ==
            "Sus fondos son insuficientes para realizar la transferencia"
          ) {
            Swal.fire({
              confirmButtonColor: "#1bb5db",
              background: "#212121",
              color: "white",
              // icon: 'warning',
              // title: 'Error',
              text: "Sus fondos son insuficientes para realizar la transferencia",
            });
          }
        });
    },
    cerrarSesion() {
      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: "btn btn-success",
          cancelButton: "btn btn-danger",
        },
        buttonsStyling: false,
      });
  
      swalWithBootstrapButtons
        .fire({
          background: "#212121",
          color: "white",
          title: "¿Seguro deseas cerrar sesión?",
          // icon: 'warning',
          showCancelButton: true,
          // confirmButtonColor: '#d14334',
          // cancelButtonColor: '#d33',
          confirmButtonText: "Si",
          cancelButtonText: "No",
  
          reverseButtons: false,
        })
        .then((result) => {
          if (result.isConfirmed) {
            Swal.fire({
              background: "#212121",
              color: "white",
              position: "top-center",
              // icon: 'success',
              title: "Su sesión ha sido finalizada",
              showConfirmButton: false,
              timer: 10500,
            });
            axios
              .post("/api/logout")
              .then((response) => (window.location.href = "/web/index.html"));
          }
        });
    },
  },
  computed: {},
}).mount("#app");
