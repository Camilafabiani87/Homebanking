const app = Vue.createApp({
    data() {
      return {
        url: "/api/clients/current",
        primerNombre: "",
        number: "",
        creationDate: "",
        balance: "",
        monto: "",
        cuentaDestino: "",
        cuentas: [],
        error: "",
        cuotas: [],
        // cuotasHipotecario:[],
        // cuotasPersonal:[],
        // cuotasAutomotriz:[],
        prestamos: [],
        loanId: "",
        opcion: "",
        montoMaximo: "",
        prestamoSeleccionado: "",
        prestamoNombre: "",
        cuotasSelecionada: "",
        cuotaFinal: "",
        tipoPrestamos:"",
        cuota1:"",
        cuota2:"",
        cuota3:"",
        cuota4:"",
        cuota5:"", 
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
  
            // console.log(payment);
          })
          .catch((error) => console.error(error.message));
        axios.get("/api/loans").then((respuesta) => {
          this.prestamos = respuesta.data;
        });
      },
      cuotasSeleccionadas() {
        this.cuotas = this.prestamos
          .filter((prestamo) => prestamo.name == this.prestamoNombre)
          .map((prestamo) => prestamo.payment);
        this.loanId = this.prestamos
          .filter((prestamo) => prestamo.name == this.prestamoNombre)
          .map((prestamo) => prestamo.id);
        this.montoMaximo = this.prestamos
          .filter((prestamo) => prestamo.name == this.prestamoNombre)
          .map((prestamo) => prestamo.maxAmount);
      },
  
      botonPrestamo() {
        Swal.fire({
          background: "#212121",
          color: "white",
          title: "¿Desea confirmar el prestamo?",
          // icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: "#1bb5db",
          cancelButtonColor: "#d33",
          confirmButtonText: "Si, confirmo el prestamo",
          cancelButtonText: "Cancelar",
        }).then((result) => {
          if (result.isConfirmed) {
            //console.log(idCuenta);
            this.realizarPrestamo();
          }
        });
      },
  
      realizarPrestamo() {
        console.log({
          loanId: this.loanId[0],
          amount: this.monto,
          payment: this.cuotasSelecionada,
          numberAccountDestin: this.cuentaDestino,
        });
  
        axios
          .post("/api/loans", {
            loanId: this.loanId[0],
            amount: this.monto,
            payment: this.cuotasSelecionada,
            numberAccountDestin: this.cuentaDestino,
          })
  
          .then(() => {
            window.location.assign("/web/accounts.html")
  
            let timerInterval;
            Swal.fire({
              background: "#212121",
              color: "white",
              title: "Su prestamo sera efectivo en unos segundos",
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
  
    //       .catch((error) => {
    //         this.error = error.response.data;
    //         console.log(error);
    //         if (this.error == "La cuenta de destino esta vacia") {
    //           Swal.fire({
    //             confirmButtonColor: "#1bb5db",
    //             background: "#212121",
    //             color: "white",
    //             // icon: 'warning',
    //             // title: 'Error',
    //             text: "Debe seleccionar una cuenta",
    //           });
    //         }
    //         if (this.error == "Las cuotas estan vacias") {
    //           Swal.fire({
    //             confirmButtonColor: "#1bb5db",
    //             background: "#212121",
    //             color: "white",
    //             // icon: 'warning',
    //             // title: 'Error',
    //             text: "Debe seleccionar en cuántas cuotas desea pagar su préstamo",
    //           });
    //         }
    //         if (this.error == "El monto ingresado es menor a cero") {
    //           Swal.fire({
    //             confirmButtonColor: "#1bb5db",
    //             background: "#212121",
    //             color: "white",
    //             // icon: 'warning',
    //             // title: 'Error',
    //             text: "El monto ingresado debe ser mayor a 0",
    //           });
    //         }
    //         if (
    //           this.error ==
    //           "El monto solicitado  supera el monto máximo permitido del préstamo"
    //         ) {
    //           Swal.fire({
    //             confirmButtonColor: "#1bb5db",
    //             background: "#212121",
    //             color: "white",
    //             // icon: 'warning',
    //             // title: 'Error',
    //             text: "El monto solicitado no puede superar el monto máximo permitido del préstamo",
    //           });
    //         }
    //         if (this.error == "No puede solicitar el mismo prestamo") {
    //           Swal.fire({
    //             confirmButtonColor: "#1bb5db",
    //             background: "#212121",
    //             color: "white",
    //             // icon: 'warning',
    //             // title: 'Error',
    //             text: "Usted ya posee este préstamo",
    //           });
    //         }
    //       });
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
    computed: {
      montoCuotaFinal() {
        this.cuotaFinal = (
          (this.monto * 120) /
          100 /
          this.cuotasSelecionada
        ).toFixed(2);
  
        // console.log(this.cuotaFinal);
      },
    },
  }).mount("#app");
  