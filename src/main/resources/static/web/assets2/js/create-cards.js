const app = Vue.createApp({
  data() {
    return {
      url: "/api/clients/current",
      titularTarjeta: "",
      tipoTarjeta: "",
      colorTarjeta: "",
      primerNombre: "",
      number: "",
      creationDate: "",
      balance: "",
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
        })
        .catch((error) => console.error(error.message));
    },

    crearTarjeta() {
      Swal.fire({
        background: "#212121",
        color: "white",
        title: "¿Desea crear una nueva tarjeta?",
        // icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: "#1bb5db",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, confirmo ",
        cancelButtonText: "Cancelar",
      })
        .then((result) => {
          if (result.isConfirmed) {
            axios.post(
              "/api/clients/current/cards",
              "cardType=" + this.tipoTarjeta + "&colorType=" + this.colorTarjeta

            ) .then(()=>window.location.assign("/web/cards.html"))
            .catch((error) => {
                this.error = error.response.data;
                console.log(error);
             
                if (this.tipoTarjeta == "") {
                  Swal.fire({
                    confirmButtonColor: "#1bb5db",
                    background: "#212121",
                    color: "white",
                    icon: "error",
                    // title: "Error",
                    text: "Falta completar el tipo de tarjeta",
                  });
                }
                if (this.colorTarjeta == "") {
                  Swal.fire({
                    confirmButtonColor: "#1bb5db",
                    background: "#212121",
                    color: "white",
                    icon: "error",
                    // title: "Error",
                    text: "Debes elegir un color de tarjeta",
                  });
                }
                if (this.error == "No puede tener mas de 3 tarjetas asociadas") {
                  Swal.fire({
                    confirmButtonColor: "#1bb5db",
                    background: "#212121",
                    color: "white",
                    icon: "error",
                    // title: "Error",
                    text: "No puede tener mas de 3 tarjetas asociadas del mismo tipo",
                  });
                }
            });
          }
        })
        
        


       
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
